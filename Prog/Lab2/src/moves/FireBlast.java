package moves;

import ru.ifmo.se.pokemon.*;

public class FireBlast extends SpecialMove {
    public FireBlast () {
        super(Type.FIRE, 110, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect eff = new Effect();
        eff.condition(Status.BURN).chance(0.1);
        pokemon.addEffect(eff);
    }

    @Override
    protected String describe() {
        return "атакует с вероятностью поджечь";
    }
}
