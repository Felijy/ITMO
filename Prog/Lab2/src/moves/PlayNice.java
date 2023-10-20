package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class PlayNice extends StatusMove {
    public PlayNice () {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setMod(Stat.ATTACK, -1);
    }

    @Override
    protected String describe() {
        return "снижает атаку противнику";
    }
}
