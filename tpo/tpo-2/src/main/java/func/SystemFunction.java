package func;

import func.api.MathModule;

public class SystemFunction implements MathModule {
    private final MathModule sin, cos, tan, cot, sec, csc, ln, log2, log3, log10;

    public SystemFunction(MathModule sin, MathModule cos, MathModule tan, MathModule cot, MathModule sec, MathModule csc,
                          MathModule ln, MathModule log2, MathModule log3, MathModule log10) {
        this.sin = sin; this.cos = cos; this.tan = tan; this.cot = cot; this.sec = sec; this.csc = csc;
        this.ln = ln; this.log2 = log2; this.log3 = log3; this.log10 = log10;
    }

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            return (((((((((((cos.calculate(x) - sec.calculate(x)) * cot.calculate(x)) - cot.calculate(x)) * cot.calculate(x))
                    - tan.calculate(x)) / cos.calculate(x)) - csc.calculate(x))
                    - ((cos.calculate(x) - sin.calculate(x)) + (sec.calculate(x) - cot.calculate(x)))) / csc.calculate(x))
                    - (((Math.pow(tan.calculate(x), 3) + tan.calculate(x)) / tan.calculate(x))))
                    * ((sec.calculate(x) - ((tan.calculate(x) / Math.pow(tan.calculate(x), 3)) * Math.pow(sec.calculate(x), 3)))
                    * Math.pow((csc.calculate(x) * (tan.calculate(x) / cot.calculate(x))), 2)));
        } else {
            return Math.pow((Math.pow((log10.calculate(x) * log3.calculate(x)), 3) + log2.calculate(x)), 3) * ln.calculate(x);
        }
    }
}
