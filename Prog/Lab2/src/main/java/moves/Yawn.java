package moves;
import ru.ifmo.se.pokemon.*;

public class Yawn extends StatusMove {
    public Yawn() {
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect.sleep(pokemon);
    }

    @Override
    protected String describe() {
        return "отправляет противника спать";
    }
}
