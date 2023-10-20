package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class TropKick extends PhysicalMove {
    public TropKick () {
        super(Type.GRASS, 70, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, -1);
    }

    @Override
    protected String describe() {
        return "атакует, снижая атаку на 1";
    }
}

