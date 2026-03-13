import org.example.Task1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTask1 {

    @ParameterizedTest
    @CsvSource({
            "-1.7320508075, -1.0471975511",
            "-1, -0.7853981633",
            "-0.5773502691, -0.5235987755",
            "0, 0",
            "0.5773502691, 0.5235987755",
            "1, 0.7853981633",
            "1.7320508075, 1.0471975511"
    })
    void testTablePoints(double point, double expected) {
        double actual = Task1.arctg_series(point);
        assertEquals(expected, actual, 1e-9);
    }

    @ParameterizedTest
    @CsvSource({
            "0.599999",
            "0.600001",
            "0.999999",
            "1.000001"
    })
    void testAlgorithmBoundaries(double x) {
        assertEquals(Math.atan(x), Task1.arctg_series(x), 1e-9);
    }

    @Test
    void testSpecialDoubleValues() {
        org.junit.jupiter.api.Assertions.assertTrue(Double.isNaN(Task1.arctg_series(Double.NaN)));

        assertEquals(Math.PI / 2, Task1.arctg_series(Double.POSITIVE_INFINITY), 1e-9);
        assertEquals(-Math.PI / 2, Task1.arctg_series(Double.NEGATIVE_INFINITY), 1e-9);
    }
}