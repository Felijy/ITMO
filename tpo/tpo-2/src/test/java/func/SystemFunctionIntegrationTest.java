package func;

import func.api.MathModule;
import func.base.CosSeries;
import func.base.LnSeries;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SystemFunctionIntegrationTest {

    private MathModule sin, cos, tan, cot, sec, csc;
    private MathModule ln, log2, log3, log10;
    private SystemFunction systemFunction;

    private final double EPS = 0.000001;

    @BeforeEach
    void setUp() {
        sin = mock(MathModule.class);
        cos = mock(MathModule.class);
        tan = mock(MathModule.class);
        cot = mock(MathModule.class);
        sec = mock(MathModule.class);
        csc = mock(MathModule.class);
        ln = mock(MathModule.class);
        log2 = mock(MathModule.class);
        log3 = mock(MathModule.class);
        log10 = mock(MathModule.class);

        systemFunction = new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log2, log3, log10);
    }

    @Nested
    @DisplayName("Изолированное тестирование формулы")
    class FormulaLogicTests {

        @Nested
        @DisplayName("Правая часть (x > 0)")
        class LogarithmicTests {

            @ParameterizedTest
            @CsvSource({
                    "1.0, 0.0, 0.0, 0.0, 0.0, 0.0", // ln(1)=0
                    "2.0, 0.6931, 1.0, 0.6309, 0.3010, 0.7074", // Точка основания log2
                    "2.7182, 1.0, 1.4426, 0.9102, 0.4342, 3.4047", // Точка x = e (ln(x) = 1)
                    "10.0, 2.3025, 3.3219, 2.0959, 1.0, 4528.1" // Точка основания log10
            })
            void testLogarithmicPoints(double x, double lnVal, double l2, double l3, double l10, double expected) {
                when(ln.calculate(x)).thenReturn(lnVal);
                when(log2.calculate(x)).thenReturn(l2);
                when(log3.calculate(x)).thenReturn(l3);
                when(log10.calculate(x)).thenReturn(l10);

                double actual = systemFunction.calculate(x);
                assertEquals(expected, actual, 0.1);
            }

            @Test
            void testLogAsymptote() {
                double x = 0.000001;
                when(ln.calculate(x)).thenReturn(-13.8);
                when(log2.calculate(x)).thenReturn(-19.9);
                when(log3.calculate(x)).thenReturn(-12.5);
                when(log10.calculate(x)).thenReturn(-6.0);

                double result = systemFunction.calculate(x);
                assertTrue(Double.isInfinite(result) || result < -1000.0);
            }
        }

        @Nested
        @DisplayName("Левая часть (x <= 0)")
        class TrigonometricTests {

            @ParameterizedTest
            @CsvSource({
                    "-0.7853, 0.7071, -0.7071, -1.0, -1.0, 1.4142, -1.4142", // x = -pi/4
                    "-3.9269, -0.7071, 0.7071, -1.0, -1.0, -1.4142, 1.4142"  // x = -5pi/4
            })
            void testTrigTablePoints(double x, double c, double s, double t, double ct, double sc, double cs) {
                when(cos.calculate(x)).thenReturn(c);
                when(sin.calculate(x)).thenReturn(s);
                when(tan.calculate(x)).thenReturn(t);
                when(cot.calculate(x)).thenReturn(ct);
                when(sec.calculate(x)).thenReturn(sc);
                when(csc.calculate(x)).thenReturn(cs);

                assertDoesNotThrow(() -> systemFunction.calculate(x));
            }

            @ParameterizedTest
            @ValueSource(doubles = {0.0, -1.5707, -3.1415, -4.7123, -6.2831})
            void testTrigAsymptotes(double x) {
                // либо sin=0, либо cos=0
                when(sin.calculate(x)).thenReturn(0.0);
                when(cos.calculate(x)).thenReturn(0.0);
                when(tan.calculate(x)).thenReturn(Double.NaN);
                when(cot.calculate(x)).thenReturn(Double.NaN);

                double result = systemFunction.calculate(x);
                assertTrue(Double.isNaN(result) || Double.isInfinite(result));
            }
        }

        @Nested
        @DisplayName("Интеграция базовых модулей (ряды)")
        class ModuleIntegrationTests {

            @Test
            @DisplayName("Интеграция CosSeries")
            void testCosIntegration() {
                CosSeries realCos = new CosSeries(EPS);
                systemFunction = new SystemFunction(sin, realCos, tan, cot, sec, csc, ln, log2, log3, log10);

                assertEquals(1.0, realCos.calculate(0.0), EPS);
                assertEquals(-1.0, realCos.calculate(-3.1415926535), EPS); // -pi
                assertEquals(1.0, realCos.calculate(-6.2831853071), EPS); // -2pi (период)
            }

            @Test
            @DisplayName("Интеграция LnSeries")
            void testLnIntegration() {
                LnSeries realLn = new LnSeries(EPS);
                systemFunction = new SystemFunction(sin, cos, tan, cot, sec, csc, realLn, log2, log3, log10);

                assertEquals(0.0, realLn.calculate(1.0), EPS);
                assertTrue(realLn.calculate(0.5) < -0.69);
                assertTrue(realLn.calculate(2.7182818284) > 0.99); // ln(e)
            }
        }

        @Nested
        @DisplayName("Асимптоты и окрестности выколотых точек")
        class BoundaryAnalysisTests {

            @ParameterizedTest
            @ValueSource(doubles = {0.0, -3.1415926535, -6.2831853071})
            @DisplayName("Асимптоты в точках k*pi (деление на sin=0)")
            void testSinAsymptotes(double x) {
                when(sin.calculate(x)).thenReturn(0.0);
                when(tan.calculate(x)).thenReturn(0.0);
                when(cot.calculate(x)).thenReturn(Double.NaN);
                when(csc.calculate(x)).thenReturn(Double.POSITIVE_INFINITY);

                double result = systemFunction.calculate(x);
                // т.к. cot и csc, то ожидаем неопределенность
                assertTrue(Double.isNaN(result) || Double.isInfinite(result));
            }

            @ParameterizedTest
            @ValueSource(doubles = {-1.5707963267, -4.7123889803})
            @DisplayName("Асимптоты в точках PI/2 + k*PI (деление на cos=0)")
            void testCosAsymptotes(double x) {
                when(cos.calculate(x)).thenReturn(0.0);
                when(sec.calculate(x)).thenReturn(Double.POSITIVE_INFINITY);
                when(tan.calculate(x)).thenReturn(Double.POSITIVE_INFINITY);

                double result = systemFunction.calculate(x);
                assertTrue(Double.isNaN(result) || Double.isInfinite(result));
            }

            @Test
            @DisplayName("Окрестность нуля справа")
            void testLogLimitNearZero() {
                double x = 0.000001;
                // логарифм в этой точке стремится к -inf
                when(ln.calculate(x)).thenReturn(-13.81551);
                when(log2.calculate(x)).thenReturn(-19.93156);
                when(log3.calculate(x)).thenReturn(-12.57542);
                when(log10.calculate(x)).thenReturn(-6.0);

                double result = systemFunction.calculate(x);
                // итоговое значение должно быть очень большим
                assertTrue(Math.abs(result) > 1000.0);
            }
        }

        @Nested
        @DisplayName("Проверка зависимостей логарифмов")
        class LogarithmicHierarchyTests {

            @Test
            @DisplayName("Проверка log_n через базовый ln")
            void testLogHierarchy() {
                MathModule realLn = new LnSeries(EPS);
                MathModule realLog2 = x -> realLn.calculate(x) / realLn.calculate(2.0);
                MathModule realLog10 = x -> realLn.calculate(x) / realLn.calculate(10.0);

                assertEquals(3.0, realLog2.calculate(8.0), 0.001);
                assertEquals(2.0, realLog10.calculate(100.0), 0.001);
            }
        }
    }
    @Nested
    @DisplayName("Полный тест")
    class FullSystemTest {

        private SystemFunction fullSystem;

        @BeforeEach
        void initRealSystem() {
            MathModule cosReal = new CosSeries(EPS);
            MathModule lnReal = new LnSeries(EPS);

            MathModule sinReal = x -> cosReal.calculate(x - Math.PI / 2);
            MathModule tanReal = x -> sinReal.calculate(x) / cosReal.calculate(x);
            MathModule cotReal = x -> cosReal.calculate(x) / sinReal.calculate(x);
            MathModule secReal = x -> 1.0 / cosReal.calculate(x);
            MathModule cscReal = x -> 1.0 / sinReal.calculate(x);

            MathModule log2Real = x -> lnReal.calculate(x) / lnReal.calculate(2.0);
            MathModule log3Real = x -> lnReal.calculate(x) / lnReal.calculate(3.0);
            MathModule log10Real = x -> lnReal.calculate(x) / lnReal.calculate(10.0);

            fullSystem = new SystemFunction(
                    sinReal, cosReal, tanReal, cotReal, secReal, cscReal,
                    lnReal, log2Real, log3Real, log10Real
            );
        }

        @ParameterizedTest
        @CsvSource({
                "-1.0, 13.3586",
                "1.0, 0.0",
                "0.5, 0.6789",
                "2.0, 0.7074"
        })
        void testEntireSystem(double x, double expected) {
            double actual = fullSystem.calculate(x);

            assertEquals(expected, actual, 0.01);
        }

        @Test
        @DisplayName("Проверка непрерывности")
        void testSystemStability() {
            // проходя, исключаем известные точки разрыва
            for (double x = -2.0; x <= 2.0; x += 0.1) {
                if (Math.abs(x + Math.PI/2) < 0.1) continue;
                if (x == 0) continue;

                double result = fullSystem.calculate(x);
                assertNotNull(result);
                assertFalse(Double.isNaN(result) && x > 0);
            }
        }
    }
}