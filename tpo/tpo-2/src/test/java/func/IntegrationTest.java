package func;

import func.api.MathModule;
import func.base.CosSeries;
import func.base.LnSeries;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IntegrationTest {

    private final double EPS = 0.0001;

    static Stream<TestData> testDataProvider() {
        return Stream.of(
                // x, f(x), [sin, cos, tan, cot, sec, csc, ln, l2, l3, l10]

                // Тригонометрия ноль + левее/правее
                new TestData(-0.8384, 0.0, -0.7436, 0.6687, -1.112047, -0.899242, 1.495543, -1.344856, null, null, null, null),
                new TestData(-0.6, -2.4627, -0.5646, 0.8253, -0.684137, -1.461696, 1.211628, -1.771032, null, null, null, null),
                new TestData(-1.0, 13.3586, -0.8415, 0.5403, -1.557408, -0.642093, 1.850816, -1.188395, null, null, null, null),

                // Тригонометрия экстремум-1 + левее/правее
                new TestData(-2.78971, -7.44572, -0.3447, -0.9387, 0.367163, 2.723582, -1.065274, -2.901362, null, null, null, null),
                new TestData(-2.5, -10.746, -0.5985, -0.8011, 0.747022, 1.338648, -1.248216, -1.670922, null, null, null, null),
                new TestData(-3.0, -10.478, -0.1411, -0.99, 0.142547, 7.015253, -1.010109, -7.086167, null, null, null, null),

                // Тригонометрия ноль + левее/правее
                new TestData(-3.49104, 0.0, 0.3424, -0.9396, -0.364402, -2.74422, -1.064326, 2.920743, null, null, null, null),
                new TestData(-3.3, 3.535, 0.1577, -0.9875, -0.159746, -6.259948, -1.012679, 6.339317, null, null, null, null),
                new TestData(-4.0, -10.7516, 0.7568, -0.6536, -1.157821, -0.863691, -1.529886, 1.321349, null, null, null, null),

                // Две точки разрыва тригонометрия
                new TestData(-1.5708, Double.NaN, -1.0, -0.0, Double.NaN, 0.0, Double.NaN, -1.0, null, null, null, null),
                new TestData(-4.71239, Double.NaN, 1.0, 0.0, Double.NaN, 0.0, Double.NaN, 1.0, null, null, null, null),

                // Тригонометрия экстремум-2 + левее/правее
                new TestData(-5.9502, 5.9949, 0.3269, 0.9451, 0.345864, 2.891311, 1.058122, 3.059359, null, null, null, null),
                new TestData(-5.722, 8.01304, 0.5322, 0.8466, 0.628602, 1.590832, 1.181161, 1.879028, null, null, null, null),
                new TestData(-6.122, 7.97803, 0.1605, 0.987, 0.162596, 6.150218, 1.013132, 6.230985, null, null, null, null),

                // Логарифмы экстремум + левее/правее
                new TestData(0.25282, 5.28282, 0.2501, 0.9682, 0.258348, 3.870749, 1.032833, 3.997836, -1.375078, -1.983817, -1.251649, -0.597189),
                new TestData(0.1978, 2.93672, 0.1965, 0.9805, 0.200421, 4.989506, 1.019886, 5.088729, -1.620499, -2.337886, -1.475042, -0.703774),
                new TestData(0.409, 1.7811, 0.3977, 0.9175, 0.433443, 2.307109, 1.089896, 2.514509, -0.89404, -1.289827, -0.81379, -0.388277),

                // Логарифмы ноль-1 + левее (правее в предыдущем)
                new TestData(0.15291, 0.0, 0.1523, 0.9883, 0.154113, 6.488745, 1.011806, 6.565349, -1.877906, -2.709245, -1.709343, -0.815564),
                new TestData(0.13625, -1.98587, 0.1358, 0.9907, 0.137099, 7.293977, 1.009354, 7.362207, -1.993264, -2.875672, -1.814347, -0.865663),

                // Логарифмы ноль-2 + правее (левее в предыдущем)
                new TestData(1.0, 0.0, 0.8415, 0.5403, 1.557408, 0.642093, 1.850816, 1.188395, 0.0, 0.0, 0.0, 0.0),
                new TestData(2.696, 3.27813, 0.431, -0.9024, -0.477631, -2.093667, -1.108211, 2.320225, 0.991769, 1.43082, 0.902747, 0.43072)

        );
    }

    static class TestData {
        double x, expected;
        Double sin, cos, tan, cot, sec, csc, ln, l2, l3, l10;

        TestData(double x, double exp, Double... m) {
            this.x = x; this.expected = exp;
            this.sin = m[0]; this.cos = m[1]; this.tan = m[2]; this.cot = m[3];
            this.sec = m[4]; this.csc = m[5]; this.ln = m[6]; this.l2 = m[7];
            this.l3 = m[8]; this.l10 = m[9];
        }
    }

    private MathModule mSin, mCos, mTan, mCot, mSec, mCsc, mLn, mL2, mL3, mL10;

    @BeforeEach
    void setUp() {
        mSin = mock(MathModule.class); mCos = mock(MathModule.class);
        mTan = mock(MathModule.class); mCot = mock(MathModule.class);
        mSec = mock(MathModule.class); mCsc = mock(MathModule.class);
        mLn = mock(MathModule.class); mL2 = mock(MathModule.class);
        mL3 = mock(MathModule.class); mL10 = mock(MathModule.class);
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Уровень 1: Всё заглушки")
    void level1_allMocks(TestData d) {
        SystemFunction sf = new SystemFunction(mSin, mCos, mTan, mCot, mSec, mCsc, mLn, mL2, mL3, mL10);

        if (d.x > 0) {
            when(mLn.calculate(d.x)).thenReturn(d.ln); when(mL2.calculate(d.x)).thenReturn(d.l2);
            when(mL3.calculate(d.x)).thenReturn(d.l3); when(mL10.calculate(d.x)).thenReturn(d.l10);
        } else {
            when(mSin.calculate(d.x)).thenReturn(d.sin); when(mCos.calculate(d.x)).thenReturn(d.cos);
            when(mTan.calculate(d.x)).thenReturn(d.tan); when(mCot.calculate(d.x)).thenReturn(d.cot);
            when(mSec.calculate(d.x)).thenReturn(d.sec); when(mCsc.calculate(d.x)).thenReturn(d.csc);
        }

        double actual = sf.calculate(d.x);

        if (Double.isNaN(d.expected)) {
            assertTrue(Double.isNaN(actual), "Ожидался NaN в точке " + d.x);
        } else {
            assertEquals(d.expected, actual, 0.1);
        }
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Уровень 2: tg, ctg, csc - real; sin, sec, cos - mock")
    void level2_partialReal(TestData d) {
        MathModule rTan = x -> mSin.calculate(x) / mCos.calculate(x);
        MathModule rCot = x -> mCos.calculate(x) / mSin.calculate(x);
        MathModule rCsc = x -> 1.0 / mSin.calculate(x);

        SystemFunction sf = new SystemFunction(mSin, mCos, rTan, rCot, mSec, rCsc, mLn, mL2, mL3, mL10);

        if (d.x <= 0) {
            when(mSin.calculate(d.x)).thenReturn(d.sin);
            when(mCos.calculate(d.x)).thenReturn(d.cos);
            when(mSec.calculate(d.x)).thenReturn(d.sec);
        } else {
            when(mLn.calculate(d.x)).thenReturn(d.ln);
            when(mL2.calculate(d.x)).thenReturn(d.l2);
            when(mL3.calculate(d.x)).thenReturn(d.l3);
            when(mL10.calculate(d.x)).thenReturn(d.l10);
        }

        double actual = sf.calculate(d.x);

        if (Double.isNaN(d.expected)) {
            assertTrue(Double.isNaN(actual), "Ожидался NaN в точке " + d.x);
        } else {
            assertEquals(d.expected, actual, 0.1);
        }
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Уровень 3: sin, sec - real; cos - mock")
    void level3_moreReal(TestData d) {
        MathModule rSin = x -> mCos.calculate(x - Math.PI / 2);
        MathModule rSec = x -> 1.0 / mCos.calculate(x);
        MathModule rTan = x -> rSin.calculate(x) / mCos.calculate(x);
        MathModule rCot = x -> mCos.calculate(x) / rSin.calculate(x);
        MathModule rCsc = x -> 1.0 / rSin.calculate(x);

        SystemFunction sf = new SystemFunction(rSin, mCos, rTan, rCot, rSec, rCsc, mLn, mL2, mL3, mL10);

        if (d.x <= 0) {
            when(mCos.calculate(d.x)).thenReturn(d.cos);
            when(mCos.calculate(d.x - Math.PI/2)).thenReturn(d.sin);
        } else {
            when(mLn.calculate(d.x)).thenReturn(d.ln);
            when(mL2.calculate(d.x)).thenReturn(d.l2);
            when(mL3.calculate(d.x)).thenReturn(d.l3);
            when(mL10.calculate(d.x)).thenReturn(d.l10);
        }

        double actual = sf.calculate(d.x);

        if (Double.isNaN(d.expected)) {
            assertTrue(Double.isNaN(actual), "Ожидался NaN в точке " + d.x);
        } else {
            assertEquals(d.expected, actual, 0.1);
        }
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Уровень 4: Всё real")
    void level4_allReal(TestData d) {
        CosSeries cosR = new CosSeries(EPS);
        LnSeries lnR = new LnSeries(EPS);

        MathModule s = x -> cosR.calculate(x - Math.PI / 2);
        MathModule t = x -> s.calculate(x) / cosR.calculate(x);
        MathModule ct = x -> cosR.calculate(x) / s.calculate(x);
        MathModule sc = x -> 1.0 / cosR.calculate(x);
        MathModule cs = x -> 1.0 / s.calculate(x);

        MathModule l2 = x -> lnR.calculate(x) / lnR.calculate(2.0);
        MathModule l3 = x -> lnR.calculate(x) / lnR.calculate(3.0);
        MathModule l10 = x -> lnR.calculate(x) / lnR.calculate(10.0);

        SystemFunction sf = new SystemFunction(s, cosR, t, ct, sc, cs, lnR, l2, l3, l10);

        double actual = sf.calculate(d.x);

        if (Double.isNaN(d.expected)) {
            boolean isUnexpectedlyLarge = Double.isNaN(actual) || Math.abs(actual) > 1e30;
            assertTrue(isUnexpectedlyLarge, "В точке " + d.x + " ожидался разрыв");
        } else {
            assertEquals(d.expected, actual, 0.1);
        }
    }
}