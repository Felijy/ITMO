package moves;

import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove {
    public FocusBlast () {
        super(Type.FIGHTING, 120, 70);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() <= 0.1) {
            pokemon.setMod(Stat.SPECIAL_ATTACK, -1);
        }
    }

    @Override
    protected String describe() {
        return "атакует с вероятностью уменьшить специальную атаку";
    }
}
