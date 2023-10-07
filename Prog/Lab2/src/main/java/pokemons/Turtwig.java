package pokemons;

import moves.Aeroblast;
import moves.ShadowPunch;
import moves.ThunderShock;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Turtwig extends Pokemon {
    public Turtwig(String name, int lv) {
        super(name, lv);
        setType(Type.GRASS);
        setStats(55, 68, 64, 45, 55, 31);
        setMove(new ShadowPunch(), new ThunderShock(), new Aeroblast());
    }
}
