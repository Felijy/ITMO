package pokemons;

import moves.HydroPump;
import moves.ShadowPunch;
import moves.SweetScent;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Teddiursa extends Pokemon {
    public Teddiursa(String name, int lv) {
        super(name, lv);
        setType(Type.NORMAL);
        setStats(60, 80, 50, 50, 50, 40);
        setMove(new ThunderWave(), new HydroPump(), new ShadowPunch(), new SweetScent());
    }
}
