package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public class MagicalLeaf extends SpecialMove {
    public MagicalLeaf () {
        super(Type.GRASS, 60, Double.POSITIVE_INFINITY);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.restore();
    }

    @Override
    protected String describe() {
        return "атакует, восстанавливая характеристики";
    }
}
