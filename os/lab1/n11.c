#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <fcntl.h>
#include <sched.h>
#include <sys/syscall.h>
#include <signal.h>
#include <stdint.h> 


#ifndef HAVE_STRUCT_CLONE_ARGS
struct clone_args {
    uint64_t flags;       uint64_t pidfd;        uint64_t child_tid;
    uint64_t parent_tid;  uint64_t exit_signal;  uint64_t stack;
    uint64_t stack_size;  uint64_t tls;          uint64_t set_tid;
    uint64_t set_tid_size;uint64_t cgroup;
};
#endif

#define MAX_CMD_LEN 1024
#define MAX_ARGS 64
#define TOKEN_DELIMITERS " \t\r\n\a"

int execute_command(char **args);
void shell_loop();

int shell_cd(char **args) {
    if (args[1] == NULL) {
        const char* home = getenv("HOME");
        if (home == NULL) { fprintf(stderr, "shell: не удалось определить домашнюю директорию\n"); return 1; }
        if (chdir(home) != 0) { perror("shell"); }
    } else {
        if (chdir(args[1]) != 0) { perror("shell"); }
    }
    return 1;
}

char** parse_line(char* line) {
    int bufsize = MAX_ARGS; int position = 0;
    char **tokens = malloc(bufsize * sizeof(char*));
    char *token;
    if (!tokens) { fprintf(stderr, "shell: ошибка выделения памяти\n"); exit(EXIT_FAILURE); }
    token = strtok(line, TOKEN_DELIMITERS);
    while (token != NULL) {
        tokens[position++] = token;
        token = strtok(NULL, TOKEN_DELIMITERS);
    }
    tokens[position] = NULL;
    return tokens;
}

int launch_program(char **args) {
    pid_t pid; int status; clock_t start, end; double cpu_time_used;
    int background = 0; char *input_file = NULL, *output_file = NULL; int append_mode = 0;

    int i = 0;
    while(args[i] != NULL) {
        if (strcmp(args[i], "<") == 0) { args[i] = NULL; input_file = args[i+1]; }
        else if (strcmp(args[i], ">") == 0) { args[i] = NULL; output_file = args[i+1]; append_mode = 0; }
        else if (strcmp(args[i], ">>") == 0) { args[i] = NULL; output_file = args[i+1]; append_mode = 1; }
        else if (strcmp(args[i], "&") == 0) { background = 1; args[i] = NULL; }
        i++;
    }

    struct clone_args cl_args;
    memset(&cl_args, 0, sizeof(cl_args));
    cl_args.exit_signal = SIGCHLD;
    pid = syscall(__NR_clone3, &cl_args, sizeof(cl_args));

    if (pid == 0) {
        if (input_file) {
            int fd_in = open(input_file, O_RDONLY);
            if (fd_in == -1) { perror("shell"); exit(EXIT_FAILURE); }
            dup2(fd_in, STDIN_FILENO); close(fd_in);
        }
        if (output_file) {
            int flags = O_WRONLY | O_CREAT | (append_mode ? O_APPEND : O_TRUNC);
            int fd_out = open(output_file, flags, 0644);
            if (fd_out == -1) { perror("shell"); exit(EXIT_FAILURE); }
            dup2(fd_out, STDOUT_FILENO); close(fd_out);
        }
        if (execvp(args[0], args) == -1) { perror("shell"); }
        exit(EXIT_FAILURE);
    } else if (pid < 0) {
        perror("shell: error clone3");
    } else {
        if (!background) {
            start = clock();
            do { waitpid(pid, &status, WUNTRACED); } while (!WIFEXITED(status) && !WIFSIGNALED(status));
            end = clock();
            cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
            printf("Время выполнения: %f секунд\n", cpu_time_used);
        } else {
            printf("Запущен фоновый процесс ('%s') с PID: %d\n", args[0], pid);
            return 1;
        }
    }
    return WEXITSTATUS(status);
}

int execute_command(char **args) {
    if (args[0] == NULL) return 1;
    if (strcmp(args[0], "cd") == 0) return shell_cd(args);
    if (strcmp(args[0], "exit") == 0) return 0;
    return launch_program(args);
}

void shell_loop() {
    char *line = NULL; size_t bufsize = 0;
    while (1) {
        printf("> ");
        if (getline(&line, &bufsize, stdin) == -1) { printf("\n"); break; }
        if (strcmp(line, "\n") == 0) continue;
        if (strncmp(line, "exit", 4) == 0) break;
        
        char* line_copy = strdup(line);
        execute_command(parse_line(line_copy));
        free(line_copy);
    }
    free(line);
}

int main(int argc, char **argv) {
    shell_loop();
    return EXIT_SUCCESS;
}