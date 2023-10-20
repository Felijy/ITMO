package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Throh extends Pokemon {
    public Throh (String name, int lvl) {
        super(name, lvl);
        setType(Type.FIGHTING);
        setStats(120, 100, 85, 30, 85, 45);
        setMove(new Rest(), new Swagger());//, new PoisonJab(), new FocusBlast());
    }
}
