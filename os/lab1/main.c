#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>
#include <fcntl.h>
#include <errno.h>

int main(void) {
    char line[1024], *tokv[256], *argv[256];

    while (fgets(line, sizeof(line), stdin)) {
        if (line[0] == '\n') continue;
        line[strcspn(line, "\n")] = '\0';

        int ntok = 0;
        for (char *t = strtok(line, " \t"); t; t = strtok(NULL, " \t"))
            tokv[ntok++] = t;
        if (ntok == 0) continue;

        char *in_file = NULL, *out_file = NULL;
        int argc = 0, syntax_err = 0;

        for (int i = 0; i < ntok && !syntax_err; ++i) {
            char *t = tokv[i];

            if (t[0] == '<' || t[0] == '>') {
                int is_in = (t[0] == '<');

                if (t[1] == '<' || t[1] == '>') { syntax_err = 1; break; }

                char *fname = (t[1] != '\0') ? t + 1 : NULL;
                if (!fname) {
                    if (i + 1 >= ntok) { syntax_err = 1; break; }
                    if (tokv[i + 1][0] == '<' || tokv[i + 1][0] == '>') { syntax_err = 1; break; }
                    fname = tokv[++i];
                }

                if (is_in) {
                    if (in_file) { syntax_err = 1; break; }
                    in_file = fname;
                } else {
                    if (out_file) { syntax_err = 1; break; }
                    out_file = fname;
                }
            } else {
                argv[argc++] = t;
            }
        }

        if (syntax_err || argc == 0) { puts("Syntax error"); continue; }
        argv[argc] = NULL;

        pid_t pid = fork();
        if (pid == 0) {
            if (in_file) {
                int fd = open(in_file, O_RDONLY);
                if (fd < 0) { puts("I/O error"); _exit(126); }
                if (dup2(fd, 0) < 0) { puts("I/O error"); _exit(126); }
                close(fd);
            }
            if (out_file) {
                int fd = open(out_file, O_WRONLY | O_CREAT | O_TRUNC, 0644);
                if (fd < 0) { puts("I/O error"); _exit(126); }
                if (dup2(fd, 1) < 0) { puts("I/O error"); _exit(126); }
                close(fd);
            }
            execvp(argv[0], argv);
            if (errno == ENOENT) puts("Command not found");
            else                 puts("I/O error");
            _exit(127);
        }
        int st; (void)waitpid(pid, &st, 0);

    }
    return 0;
}
