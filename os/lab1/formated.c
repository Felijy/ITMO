#include <errno.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/* ---------- Constants ---------- */
enum { LINE_BUFFER_SIZE = 1024, MAX_TOKENS = 256 };
static const int EXIT_IO_ERROR = 126;
static const int EXIT_CMD_NOT_FOUND = 127;

/* 0644 without magic number literal */
static const mode_t OUT_FILE_PERMS = S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH;

struct Command {
  char *argv[MAX_TOKENS];
  int argc;
  const char *in_file;
  const char *out_file;
};

static void strip_trailing_newline(char *line) {
  size_t len = strcspn(line, "\n");
  line[len] = '\0';
}

static int tokenize(char *line, char *tokens[], int *num_tokens) {
  int count = 0;
  char *saveptr = NULL;
  char *token = strtok_r(line, " \t", &saveptr);
  while (token != NULL) {
    if (count >= MAX_TOKENS) {
      return -1;
    }
    tokens[count++] = token;
    token = strtok_r(NULL, " \t", &saveptr);
  }
  *num_tokens = count;
  return 0;
}

static int parse_command(char *tokens[], int num_tokens, struct Command *cmd) {
  cmd->argc = 0;
  cmd->in_file = NULL;
  cmd->out_file = NULL;

  for (int i = 0; i < num_tokens; ++i) {
    char *tok = tokens[i];

    if (tok[0] == '<' || tok[0] == '>') {
      int is_input = (tok[0] == '<');

      if (tok[1] == '<' || tok[1] == '>') {
        return 1;
      }

      const char *fname = NULL;
      if (tok[1] != '\0') {
        fname = tok + 1;
      } else {
        if (i + 1 >= num_tokens) {
          return 1;
        }
        if (tokens[i + 1][0] == '<' || tokens[i + 1][0] == '>') {
          return 1;
        }
        fname = tokens[++i];
      }

      if (is_input) {
        if (cmd->in_file != NULL) {
          return 1;
        }
        cmd->in_file = fname;
      } else {
        if (cmd->out_file != NULL) {
          return 1;
        }
        cmd->out_file = fname;
      }
    } else {
      if (cmd->argc >= MAX_TOKENS - 1) {
        return 1;
      }
      cmd->argv[cmd->argc++] = tok;
    }
  }

  cmd->argv[cmd->argc] = NULL;
  if (cmd->argc == 0) {
    return 1;
  }
  return 0;
}

static int dup_or_report(int oldfd, int newfd) {
  if (dup2(oldfd, newfd) < 0) {
    puts("I/O error");
    return -1;
  }
  return 0;
}

static void exec_with_redirs(const struct Command *cmd) {
  if (cmd->in_file != NULL) {
    int in_fd = open(cmd->in_file, O_RDONLY);
    if (in_fd < 0) {
      puts("I/O error");
      _exit(EXIT_IO_ERROR);
    }
    if (dup_or_report(in_fd, STDIN_FILENO) < 0) {
      _exit(EXIT_IO_ERROR);
    }
    close(in_fd);
  }

  if (cmd->out_file != NULL) {
    int out_fd = open(cmd->out_file, O_WRONLY | O_CREAT | O_TRUNC, OUT_FILE_PERMS);
    if (out_fd < 0) {
      puts("I/O error");
      _exit(EXIT_IO_ERROR);
    }
    if (dup_or_report(out_fd, STDOUT_FILENO) < 0) {
      _exit(EXIT_IO_ERROR);
    }
    close(out_fd);
  }

  execvp(cmd->argv[0], cmd->argv);

  if (errno == ENOENT) {
    puts("Command not found");
  } else {
    puts("I/O error");
  }
  _exit(EXIT_CMD_NOT_FOUND);
}

int main(void) {
  char line[LINE_BUFFER_SIZE];
  char *tokens[MAX_TOKENS];

  while (fgets(line, sizeof(line), stdin)) {
    if (line[0] == '\n') {
      continue;
    }
    strip_trailing_newline(line);

    int num_tokens = 0;
    if (tokenize(line, tokens, &num_tokens) != 0) {
      puts("Syntax error");
      continue;
    }
    if (num_tokens == 0) {
      continue;
    }

    struct Command cmd;
    if (parse_command(tokens, num_tokens, &cmd) != 0) {
      puts("Syntax error");
      continue;
    }

    pid_t pid = fork();
    if (pid == 0) {
      exec_with_redirs(&cmd);
    } else if (pid > 0) {
      int status = 0;
      (void)waitpid(pid, &status, 0);
    } else {
      puts("I/O error");
    }
  }

  return 0;
}
