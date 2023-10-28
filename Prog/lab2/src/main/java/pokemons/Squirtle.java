package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Squirtle extends Pokemon {
    public Squirtle(String name, int lv) {
        super(name, lv);
        setType(Type.WATER);
        setStats(44, 48, 65, 50, 64, 43);
        setMove(new ThunderWave(), new HydroPump(), new ShadowPunch());
    }
}
