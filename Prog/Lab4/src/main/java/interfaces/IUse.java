package interfaces;

import classes.Thing;

public interface IUse {
    public void addThing(Thing smth);
    public void removeThing();
    public void printThing();
    public Thing getThing();
}
