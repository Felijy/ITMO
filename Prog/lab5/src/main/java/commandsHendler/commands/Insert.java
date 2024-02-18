package commandsHendler.commands;

import commandsHendler.AddValue;
import data.*;
import handler.IdHandler;
import handler.mapHandler;
import handler.terminalHandler;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

public class Insert extends AddValue {

    private String key;
    private Ticket ticket;
    private Venue venue;
    private Coordinates coordinates;

    public Insert() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        this.key = args;
        if (!mapHandler.checkKey(key)) {
            return false;
        }
        terminalHandler.print("Начато добавление нового значения с ключом " + this.key + "\n");
        ticket = new Ticket();
        venue = new Venue();
        coordinates = new Coordinates();
        ticket.setName(startAdding("название билета (не пустое)", false, null, null, false));
        ticket.setPrice(Float.parseFloat(startAdding("цену (больше нуля)", false, 0, null, false)));
        try {
            ticket.setDiscount(Integer.parseInt(startAdding("скидку (больше нуля, меньше 100)", false, 0, 100, false)));
        } catch (NumberFormatException e){
            return false;
        }
        ticket.setComment(startAdding("комментарий", true, null, null, true));
        String currentType;
        do {
            currentType = startAdding("тип билета (USUAL, BUDGETARY, CHEAP)", true, null, null, false);
            if (currentType == null) break;
            if ((!checkTicketTypeEnum(currentType))) terminalHandler.printlnA("!!!Ошибка. Попробуйте еще раз.");
        } while (!checkTicketTypeEnum(currentType));
        if (currentType != null) ticket.setType(TicketType.valueOf(currentType));
        else ticket.setType(null);
        coordinates.setX(Float.parseFloat(startAdding("координату по оси X, меньше 766", false, null, 766, false)));
        coordinates.setY(Float.parseFloat(startAdding("координату по оси Y", false, null, null, false)));
        ticket.setCoordinates(coordinates);
        terminalHandler.print("Добавить место? 'yes' чтобы добавить, любой другой символ, чтобы нет: ");
        String answer = terminalHandler.readLine();
        if (Objects.equals(answer, "yes")) {
            venue.setName(startAdding("название места (не пустое)", false, null, null, false));
            try {
                venue.setCapacity(Long.parseLong(startAdding("вместимость, больше нуля", false, 0, null, false)));
            } catch (NumberFormatException e) {
                return false;
            }
            do {
                currentType = startAdding("тип места (BAR, LOFT, OPEN_AREA, THEATRE, STADIUM)", false, null, null, false);
                if (!checkVenueTypeEnum(currentType)) terminalHandler.printlnA("!!!Ошибка. Попробуйте еще раз.");
            } while (!checkVenueTypeEnum(currentType));
            venue.setType(VenueType.valueOf(currentType));
            venue.setNewId();
            ticket.setVenue(venue);
        } else ticket.setVenue(null);
        ticket.setNewId();
        ticket.setCreationDate(LocalDate.now());
        mapHandler.makeNewTicket(key, ticket);
        return true;
    }


    //можно сделать выплевывание исключения для выхода из цикла заполнения
    private String startAdding (String name, boolean canBeNull, Integer moreThen,
                                      Integer lessThen, boolean canBeEmptyString) {
        boolean isEnd;
        String currentInput;
        do {
            terminalHandler.print("Введите " + name + ": ");
            currentInput = terminalHandler.readLine();
            isEnd = validateNew(currentInput, canBeNull, moreThen, lessThen, canBeEmptyString);
            if (!isEnd) terminalHandler.printlnA("!!!Ошибка ввода. Попробуйте еще раз");
        } while (!isEnd);
        return currentInput;
    }


    private boolean checkTicketTypeEnum(String arg) {
        try {
            TicketType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private boolean checkVenueTypeEnum(String arg) {
        try {
            VenueType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Добавить в коллекцию новый элемент, в качестве аргумента указывается ключ нового элемента";
    }

    @Override
    public String getName() {
        return "insert <key> {element}";
    }

}
