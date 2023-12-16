package interfaces;

import classes.Thing;
import classes.Human;
import exceptions.NullThingException;

public interface IFall {
    void fallingFrom(Thing smth, boolean complete) throws NullThingException;
    void fallingTo(Human smb, boolean complete) throws NullThingException;
}
