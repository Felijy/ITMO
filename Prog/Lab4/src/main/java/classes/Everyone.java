package classes;

import interfaces.Help;
import other.Moves;

public class Everyone extends Human implements Help {
    public Everyone(String name) {
        super(name);
    }

    @Override
    public void doing(Moves move, Thing smth) {
        System.out.print(getName());
        if (smth == null) {
            switch (move) {
                case SIT -> System.out.println(" cели");
                case TIE -> System.out.println(" завязали");
                case FALL -> System.out.println(" упали");
                case FILL -> System.out.println(" заполнили");
                case REMOVE -> System.out.println(" убрали");
                case CLIMB -> System.out.println(" залезли");
            }
        } else {
            switch (move) {
                case SIT -> System.out.println(" сели на " + smth.getName());
                case TIE -> System.out.println(" завязали " + smth.getName());
                case FALL -> System.out.println(" упали с " + smth.getName());
                case FILL -> System.out.println(" заполнили " + smth.getName());
                case REMOVE -> System.out.println(" убрали " + smth.getName());
                case CLIMB -> System.out.println(" залезли в " + smth.getName());
            }
        }
    }


    public void helping(Moves move, Human person) {
        System.out.print(getName());
        switch (move) {
            case SIT -> System.out.println(" посадили " + person.getName());
            case TIE -> System.out.println(" завязали " + person.getName());
            case FALL -> System.out.println(" упали с " + person.getName());
            case FILL -> System.out.println(" заполнили " + person.getName());
            case REMOVE -> System.out.println(" убрали " + person.getName());
            case CLIMB -> System.out.println(" залезли c " + person.getName());
        }
    }
}
