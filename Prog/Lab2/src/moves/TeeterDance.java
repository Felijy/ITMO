package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

import java.awt.*;

public class TeeterDance extends StatusMove {
    public TeeterDance() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.confuse();
    }

    @Override
    protected String describe() {
        return "приводит противника в замешательство";
    }
}
