package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class Confusion extends SpecialMove {
    public Confusion () {
        super(Type.PSYCHIC, 50, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() <= 0.1) {
            pokemon.confuse();
        }
    }

    @Override
    protected String describe() {
        return "атакует с вероятностью confuse";
    }
}
