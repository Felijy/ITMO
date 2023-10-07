package pokemons;

import moves.ShadowPunch;
import moves.ThunderShock;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Jirachi extends Pokemon {
    public Jirachi(String name, int lv) {
        super(name, lv);
        setType(Type.STEEL, Type.PSYCHIC);
        setStats(100, 100, 100, 100, 100, 100);
        setMove(new ShadowPunch(), new ThunderShock());
    }
}
