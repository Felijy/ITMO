package interfaces;

import exceptions.NullThingException;
import other.Moves;
import classes.Thing;

public interface Do {
    void doing(Moves move, Thing smth) throws NullThingException;
    void doingWithoutThing(Moves move);
}
