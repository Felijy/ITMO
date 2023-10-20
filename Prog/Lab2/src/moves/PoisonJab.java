package moves;

import ru.ifmo.se.pokemon.*;

public class PoisonJab extends PhysicalMove {
    public PoisonJab () {
        super(Type.POISON, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect eff = new Effect();
        eff.condition(Status.POISON).chance(0.3);
        pokemon.addEffect(eff);
    }

    @Override
    protected String describe() {
        return "атакует с вероятностью отравить";
    }
}
