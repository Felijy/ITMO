package classes;

import exceptions.NullThingException;
import interfaces.*;
import other.Entity;
import other.Moves;

import java.util.Objects;

public class Human extends Entity implements Climb, Ask, Fall, Do, Help {
    public CurrentThing content;
    public CurrentStatus status;

    public Human(String name) {
        super(name);
        content = new CurrentThing("Вещь " + getName() + "а ");
        status = new CurrentStatus();
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
        }
    }

    // статический класс, вещь вполне может существовать и без человека
    public static class CurrentThing {
        private Thing currentThing;
        private String name;
        public CurrentThing(String name) {
            this.currentThing = null;
            this.name = name;
        }

        public void addThing(Thing smth) {
            this.currentThing = smth;
        }

        public void removeThing() {
            this.currentThing = null;
        }

        public Thing getThing() {
            return this.currentThing;
        }

        public void printThing() {
            if (this.currentThing == null) {
                System.out.println(this.name + " ничего не содержит");
            } else {
                System.out.println(this.name + " содержит " + currentThing.getName());
            }
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
            throw new NullThingException("Climbing to NULL!");
        } else {
            System.out.println(getName() + " залез в " + smth.getName());
        }

    }

    public void climbingWithoutThing() {
        System.out.println(getName() + " залез");
    }

    public void fallingFrom(Thing smth, boolean complete) throws NullThingException {
        if (complete) {
            if (smth == null) {
                throw new NullThingException("Falling from NULL!");
            } else {
                System.out.println(getName() + " упал с " + smth.getName());
            }
        } else {
            if (smth == null) {
                throw new NullThingException("Falling from NULL!");
            } else {
                System.out.println(getName() + " не упал с " + smth.getName());
            }
        }
    }

    public void fallingTo(Human smb, boolean complete) throws NullThingException {
        if (complete) {
            if (smb == null) {
                throw new NullThingException("Falling to NULL!");
            } else {
                System.out.println(getName() + " упал на " + smb.getName());
            }
        } else {
            if (smb == null) {
                throw new NullThingException("Falling to NULL!");
            } else {
                System.out.println(getName() + " не упал на " + smb.getName());
            }
        }
    }

    public void doing(Moves move, Thing smth) throws NullThingException {
        if (smth == null) {
            throw new NullThingException("Human должен выполнить действие без предмета!");
        } else {
            System.out.print(getName());
            switch (move) {
                case SIT -> System.out.println(" сел на " + smth.getName());
                case TIE -> System.out.println(" завязал " + smth.getName());
                case FALL -> System.out.println(" упал с " + smth.getName());
                case FILL -> System.out.println(" заполнил " + smth.getName());
                case REMOVE -> System.out.println(" убрал " + smth.getName());
                case CLIMB -> System.out.println(" залез в " + smth.getName());
                case SHOUT -> System.out.println("закричал на " + smth.getName());
            }
        }
    }

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

    // нестатическй класс, статус человека не может существовать без человека
    public class CurrentStatus {
        private String currentStatus;
        CurrentStatus() {
            this.currentStatus = null;
        }

        public void addStatus(String status) {
            this.currentStatus = status;
        }

        public void removeStatus() {
            this.currentStatus = null;
        }

        public String getStatus() {
            return this.currentStatus;
        }

        public void printStatus() {
            if (currentStatus == null) {
                System.out.println(getName() + " не имеет статуса");
            } else {
                System.out.println(getName() + " " + currentStatus);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Human human = (Human) o;
        return Objects.equals(content.getThing(), human.content.getThing()) && Objects.equals(status.getStatus(), human.status.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content.getThing(), status.getStatus());
    }

    @Override
    public String toString() {
        return "classes.Human{" +
                "currentThing=" + content.getThing() +
                ", currentStatus='" + status.getStatus() + '\'' +
                '}';
    }
}
