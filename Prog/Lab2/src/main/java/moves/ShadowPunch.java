package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class ShadowPunch extends PhysicalMove {
    public ShadowPunch() {
        super(Type.GHOST, 60, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.restore();
    }

    @Override
    protected String describe() {
        return "атакует, восстанавливая своих характеристики";
    }
}
