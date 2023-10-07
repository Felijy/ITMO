package moves;

import ru.ifmo.se.pokemon.*;

public class ThunderShock extends SpecialMove {
    public ThunderShock() {
        super(Type.ELECTRIC, 40, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect eff = new Effect();
        eff.chance(0.1).condition(Status.PARALYZE);
        pokemon.addEffect(eff);
    }

    @Override
    protected String describe() {
        return "атакует с вероятностью парализовать";
    }
}
