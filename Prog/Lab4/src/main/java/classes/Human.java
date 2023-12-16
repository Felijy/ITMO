package classes;

import exceptions.NullThingException;
import interfaces.*;
import other.*;

import java.util.Objects;

public class Human extends Entity implements IClimb, IAsk, IFall, IDo, IUse, IHelp {
    public Human(String name) {
        super(name);
    }

    private Thing currentThing = null;
    public void addThing(Thing smth) {
        currentThing = smth;
    }

    public void removeThing() {
        currentThing = null;
    }

    public Thing getThing() {
        return currentThing;
    }

    public void printThing() {
        if (currentThing == null) {
            System.out.println(getName() + " ничего не имеет");
        } else {
            System.out.println(getName() + " имеет " + currentThing.getName());
        }
    }

    public void asking(Moves move, Thing smth) {
        // локальный класс
        class Phrase {
            private String finalPhrase;
            private Moves currentMove;
            private String currentThingName = smth.getName();
            private String humanName = getName();
            public Phrase() {
                this.currentMove = move;
            }

            public String getFinalPhrase() {
                return this.finalPhrase;
            }

            public void makeFinalPhrase() {
                this.finalPhrase = humanName + " велел ";
                if (currentThingName == null) {
                    switch (currentMove) {
                        case SIT -> this.finalPhrase += "сесть";
                        case TIE -> this.finalPhrase += "завязать";
                        case FALL -> this.finalPhrase += "упасть";
                        case FILL -> this.finalPhrase += "заполнить";
                        case REMOVE -> this.finalPhrase += "убрать";
                        case CLIMB -> this.finalPhrase += "залезть";
                        case SHOUT -> this.finalPhrase += "закричать";
                    }
                } else {
                    switch (currentMove) {
                        case SIT -> this.finalPhrase += "сесть на " + currentThingName;
                        case TIE -> this.finalPhrase += "завязать " + currentThingName;
                        case FALL -> this.finalPhrase += "упасть с " + currentThingName;
                        case FILL -> this.finalPhrase += "заполнить " + currentThingName;
                        case REMOVE -> this.finalPhrase += "убрать " + currentThingName;
                        case CLIMB -> this.finalPhrase += " залезть в " + currentThingName;
                        case SHOUT -> this.finalPhrase += "закричать на " + currentThingName;
                    }
                }
            }
        }
        Phrase phrase = new Phrase();
        phrase.makeFinalPhrase();
        System.out.println(phrase.getFinalPhrase());
    }

    public void climbing(Thing smth) throws NullThingException {
        if (smth == null) {
            throw new NullThingException("climb without Thing!");
        } else {
            System.out.println(getName() + " залез в " + smth.getName());
        }

    }

    @Override
    public void climbingWithoutThing() {
        System.out.println(getName() + " залез");
    }

    public void fallingFrom(Thing smth, boolean complete) {
        if (complete) {
            if (smth == null) {
                System.out.println(getName() + " упал");
            } else {
                System.out.println(getName() + " упал с " + smth.getName());
            }
        } else {
            if (smth == null) {
                System.out.println(getName() + " не упал");
            } else {
                System.out.println(getName() + " не упал с " + smth.getName());
            }
        }
    }

    public void fallingTo(Human smb, boolean complete) {
        if (complete) {
            if (smb == null) {
                System.out.println(getName() + " упал");
            } else {
                System.out.println(getName() + " упал на " + smb.getName());
            }
        } else {
            if (smb == null) {
                System.out.println(getName() + " не упал");
            } else {
                System.out.println(getName() + " не упал на " + smb.getName());
            }
        }
    }

    public void doing(Moves move, Thing smth) throws NullThingException{
        if (smth == null) {
            throw new NullThingException("doing without thing");
        } else {
            System.out.print(getName());
            switch (move) {
                case SIT -> System.out.println(" сел на " + smth.getName());
                case TIE -> System.out.println(" завязал " + smth.getName());
                case FALL -> System.out.println(" упал с " + smth.getName());
                case FILL -> System.out.println(" заполнил " + smth.getName());
                case REMOVE -> System.out.println(" убрал " + smth.getName());
                case CLIMB -> System.out.println(" залез в " + smth.getName());
                case SHOUT -> System.out.println(" закричал на " + smth.getName());
            }
        }
    }

    @Override
    public void doingWithoutThing(Moves move) {
        System.out.print(getName());
        switch (move) {
            case SIT -> System.out.println(" cел");
            case TIE -> System.out.println(" завязал");
            case FALL -> System.out.println(" упал");
            case FILL -> System.out.println(" заполнил");
            case REMOVE -> System.out.println(" убрал");
            case CLIMB -> System.out.println(" залез");
            case SHOUT -> System.out.println(" закричал");
        }
    }

    private String currentStatus = null;

    public void addStatus(String status) {
        currentStatus = status;
    }

    public void removeStatus() {
        currentStatus = null;
    }

    public void printStatus() {
        if (currentStatus == null) {
            System.out.println(getName() + " не имеет статуса");
        } else {
            System.out.println(getName() + " " + currentStatus);
        }
    }

    public String getStatus() {
        return null;
    }

    @Override
    public void helping(Moves move, Human person) {
        System.out.print(getName());
        switch (move) {
            case SIT -> System.out.println(" посадил " + person.getName());
            case TIE -> System.out.println(" завязал " + person.getName());
            case FALL -> System.out.println(" упал с " + person.getName());
            case FILL -> System.out.println(" заполнил " + person.getName());
            case REMOVE -> System.out.println(" убрал " + person.getName());
            case CLIMB -> System.out.println(" залез c " + person.getName());
            case SHOUT -> System.out.println(" закричал c " + person.getName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Human human = (Human) o;
        return Objects.equals(currentThing, human.currentThing) && Objects.equals(currentStatus, human.currentStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), currentThing, currentStatus);
    }

    @Override
    public String toString() {
        return "Human{" +
                "currentThing=" + currentThing +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }
}