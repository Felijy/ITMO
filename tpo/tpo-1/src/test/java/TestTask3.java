import org.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTask3 {

    private Vogon jeltz;
    private MuscleController muscleController;

    @BeforeEach
    void setUp() {
        muscleController = new MuscleController(false);
        jeltz = new Vogon(muscleController);
    }

    @Test
    void testSlowSmileDueToMuscleMemory() {
        // медленная улыбка, забыл как
        Smile actionResult = jeltz.smile();

        assertEquals(Smile.SLOW, actionResult);
    }

    @Test
    void testFastSmileWithGoodMemory() {
        // если память работает - улыбка быстрая (обратный)
        Vogon smartVogon = new Vogon(new MuscleController(true));

        assertEquals(Smile.FAST, smartVogon.smile());
    }

    @Test
    void testShoutingRefreshesVogon() {
        // покричав, отдыхает и готов к гадостям
        Prisoner human = new Prisoner();
        Prisoner dent = new Prisoner();
        List<Prisoner> prisoners = Arrays.asList(human, dent);

        assertFalse(jeltz.hasMood(Mood.READY_FOR_NASTINESS));

        jeltz.shoutAt(prisoners);

        assertTrue(jeltz.hasMood(Mood.RESTED));
        assertTrue(jeltz.hasMood(Mood.READY_FOR_NASTINESS));
    }

    @Test
    void testPrisonersSuffering() {
        // страдают от крика
        Prisoner arthur = new Prisoner();
        int initialMorale = arthur.getMorale();

        jeltz.shoutAt(List.of(arthur));

        assertTrue(arthur.getMorale() < initialMorale);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testShoutAtInvalidInputs(List<Prisoner> prisoners) {
        // если нет пленников, то это ничего не меняет
        jeltz.shoutAt(prisoners);

        assertFalse(jeltz.hasMood(Mood.RESTED));

        assertFalse(jeltz.hasMood(Mood.READY_FOR_NASTINESS));
    }
}