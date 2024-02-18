package commandsHendler.commands;

import commandsHendler.Command;
import handler.Run;
import handler.terminalHandler;

import java.io.File;
import java.io.FileNotFoundException;

public class Execute extends Command {
    public Execute() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        File currentFile = new File(args);
        try {
            terminalHandler.setFile(currentFile);
        } catch (FileNotFoundException e) {
            System.out.print("!!!Файл не найден. ");
            return false;
        }
        Run run = new Run();
        while (terminalHandler.getFromFile()) {
            run.userCommandFetch();
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "выполняет скрипт из файла, в качестве аргумента указывается имя файла со скриптом";
    }

    @Override
    public String getName() {
        return "execute_script <file_name>";
    }
}
