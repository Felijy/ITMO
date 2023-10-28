package moves;
import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove{
    public Thunderbolt() {
        super(Type.ELECTRIC, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect eff = new Effect();
        eff.chance(0.1).condition(Status.PARALYZE);
        pokemon.addEffect(eff);
        //System.out.println(pokemon.getCondition());
    }

    @Override
    protected String describe() {
        return "ударяет с вероятностью парализовать";
    }
}
