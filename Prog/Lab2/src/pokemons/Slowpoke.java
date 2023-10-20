package pokemons;

import moves.Confusion;
import moves.FireBlast;
import moves.WaterGun;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Slowpoke extends Pokemon {
    public Slowpoke (String name, int lvl) {
        super(name, lvl);
        setType(Type.WATER, Type.PSYCHIC);
        setStats(90, 65, 65, 40, 40, 15);
        setMove(new Confusion(), new WaterGun(), new FireBlast());
    }
}
