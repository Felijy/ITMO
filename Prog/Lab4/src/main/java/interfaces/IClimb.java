package interfaces;

import classes.Thing;
import exceptions.NullThingException;

public interface IClimb {
    void climbing(Thing smth) throws NullThingException;
    void climbingWithoutThing();
}
