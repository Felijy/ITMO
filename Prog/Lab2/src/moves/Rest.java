package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 0);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.restore();
        Effect eff = new Effect();
        eff.condition(Status.SLEEP).turns(2);
        pokemon.setCondition(eff);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "засыпает на 2 хода";
    }
}
