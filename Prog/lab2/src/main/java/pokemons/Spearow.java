package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Spearow extends Pokemon {
    public Spearow (String name, int lv) {
        super(name, lv);
        setType(Type.NORMAL, Type.FLYING);
        setStats(40, 60, 30, 31, 31, 70);
        setMove(new Yawn(), new CottonSpore(), new Thunderbolt(), new ThunderWave());
    }
}
