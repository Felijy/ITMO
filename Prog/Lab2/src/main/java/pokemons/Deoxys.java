package pokemons;

import moves.ShadowPunch;
import moves.ThunderShock;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Deoxys extends Pokemon {
    public Deoxys(String name, int lv) {
        super(name, lv);
        setType(Type.PSYCHIC);
        setStats(50, 150, 50, 150, 50, 150);
        setMove(new ShadowPunch(), new ThunderShock());
    }
}
