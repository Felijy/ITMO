package classes;

import exceptions.NullThingException;
import interfaces.IDo;
import interfaces.IUse;
import other.Entity;
import other.Moves;

import java.util.Objects;


public class Thing extends Entity implements IDo, IUse {
    public CurrentStatus status;

    public Thing(String name) {
        super(name);
        status = new CurrentStatus();
    }


    public void doing (Moves move, Thing smth) throws NullThingException {
        if (smth == null) {
            throw new NullThingException("Doing without Thing");
        } else {
            System.out.print(getName());
            switch (move) {
                case SIT -> System.out.println(" село на " + smth.getName());
                case TIE -> System.out.println(" завязало " + smth.getName());
                case FALL -> System.out.println(" упало с " + smth.getName());
                case FILL -> System.out.println(" заполнило " + smth.getName());
                case REMOVE -> System.out.println(" убрало " + smth.getName());
                case CLIMB -> System.out.println(" залезло в " + smth.getName());
                case SHOUT -> System.out.println("закричало на " + smth.getName());
            }
        }
    }

    public void doingWithoutThing(Moves move) {
        System.out.print(getName());
        switch (move) {
            case SIT -> System.out.println(" село");
            case TIE -> System.out.println(" завязало");
            case FALL -> System.out.println(" упало");
            case FILL -> System.out.println(" заполнило");
            case REMOVE -> System.out.println(" убрало");
            case CLIMB -> System.out.println(" залезло");
            case SHOUT -> System.out.println(" закричало");
        }
    }

    private Thing content = null;

    public void addThing(Thing smth) {
        content = smth;
    }

    public void removeThing() {
        content = null;
    }

    public Thing getThing() {
        return content;
    }

    public void printThing() {
        if (content == null) {
            System.out.println(getName() + " ничего не содержит");
        } else {
            System.out.println(getName() + " содержит " + content.getName());
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
        Thing thing = (Thing) o;
        return Objects.equals(content, thing.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), content);
    }

    @Override
    public String toString() {
        return "classes.Thing{" +
                "content=" + content +
                '}';
    }
}
