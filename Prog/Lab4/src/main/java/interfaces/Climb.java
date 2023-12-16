package interfaces;

import classes.Thing;
import exceptions.NullThingException;

public interface Climb {
    void climbing(Thing smth) throws NullThingException;
    void climbingWithoutThing();
}
