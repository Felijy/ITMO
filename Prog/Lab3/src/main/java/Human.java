import java.util.Objects;

public class Human extends Entity implements Climb, Ask, Fall, Do, Using, Status {
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
        System.out.print(getName() + " велел ");
        if (smth.getName() == null) {
            switch (move) {
                case SIT -> System.out.println("сесть");
                case TIE -> System.out.println("завязать");
                case FALL -> System.out.println("упасть");
                case FILL -> System.out.println("заполнить");
                case REMOVE -> System.out.println("убрать");
                case CLIMB -> System.out.println(" залезть");
            }
        } else {
            switch (move) {
                case SIT -> System.out.println("сесть на " + smth.getName());
                case TIE -> System.out.println("завязать " + smth.getName());
                case FALL -> System.out.println("упасть с " + smth.getName());
                case FILL -> System.out.println("заполнить " + smth.getName());
                case REMOVE -> System.out.println("убрать " + smth.getName());
                case CLIMB -> System.out.println(" залезть в " + smth.getName());
            }
        }
    }

    public void climbing(Thing smth) {
        if (smth == null) {
            System.out.println(getName() + " залез");
        } else {
            System.out.println(getName() + " залез в " + smth.getName());
        }

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

    public void doing(Moves move, Thing smth) {
        System.out.print(getName());
        if (smth == null) {
            switch (move) {
                case SIT -> System.out.println(" cел");
                case TIE -> System.out.println(" завязал");
                case FALL -> System.out.println(" упал");
                case FILL -> System.out.println(" заполнил");
                case REMOVE -> System.out.println(" убрал");
                case CLIMB -> System.out.println(" залез");
            }
        } else {
            switch (move) {
                case SIT -> System.out.println(" сел на " + smth.getName());
                case TIE -> System.out.println(" завязал " + smth.getName());
                case FALL -> System.out.println(" упал с " + smth.getName());
                case FILL -> System.out.println(" заполнил " + smth.getName());
                case REMOVE -> System.out.println(" убрал " + smth.getName());
                case CLIMB -> System.out.println(" залез в " + smth.getName());
            }
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
