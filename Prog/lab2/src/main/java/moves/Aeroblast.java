package moves;

import ru.ifmo.se.pokemon.*;

public class Aeroblast extends SpecialMove {
    public Aeroblast() {
        super(Type.FLYING, 100, 95);
    }

    @Override
    protected double calcCriticalHit(Pokemon pokemonAtt, Pokemon pokemonDef) {
        if (pokemonAtt.getStat(Stat.SPEED) / 256.0 > Math.random()) {
            return 2.0;
        } else {
            return 1.0;
        }
    }

    @Override
    protected String describe() {
        return "атакует с увеличенным шансом крита";
    }
}
