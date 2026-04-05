package org.example;

import func.SystemFunction;
import func.api.MathModule;
import func.base.CosSeries;
import func.base.LnSeries;
import func.io.CsvExporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        double eps = 0.000001;

        MathModule cos = new CosSeries(eps);
        MathModule ln = new LnSeries(eps);

        MathModule sin = x -> cos.calculate(x - Math.PI / 2);

        MathModule tan = x -> sin.calculate(x) / cos.calculate(x);
        MathModule cot = x -> cos.calculate(x) / sin.calculate(x);
        MathModule sec = x -> 1 / cos.calculate(x);
        MathModule csc = x -> 1 / sin.calculate(x);

        MathModule log2 = x -> ln.calculate(x) / ln.calculate(2.0);
        MathModule log3 = x -> ln.calculate(x) / ln.calculate(3.0);
        MathModule log10 = x -> ln.calculate(x) / ln.calculate(10.0);

        SystemFunction system = new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log2, log3, log10);

        String folder = "csv_output/";
        Files.createDirectories(Path.of(folder));

        CsvExporter.export(cos, -6.28, 0, 0.1, ";", Path.of(folder + "base_cos.csv"));
        CsvExporter.export(sin, -6.28, 0, 0.1, ";", Path.of(folder + "base_sin.csv"));
        CsvExporter.export(ln, 0.1, 10, 0.1, ";", Path.of(folder + "base_ln.csv"));

        CsvExporter.export(tan, -6.28, 0, 0.01, ";", Path.of(folder + "module_tan.csv"));
        CsvExporter.export(cot, -6.28, 0, 0.01, ";", Path.of(folder + "module_cot.csv"));
        CsvExporter.export(sec, -6.28, 0, 0.01, ";", Path.of(folder + "module_sec.csv"));
        CsvExporter.export(csc, -6.28, 0, 0.01, ";", Path.of(folder + "module_csc.csv"));
        CsvExporter.export(log2, 0.1, 5, 0.05, ";", Path.of(folder + "module_log2.csv"));
        CsvExporter.export(log3, 0.1, 5, 0.05, ";", Path.of(folder + "module_log3.csv"));
        CsvExporter.export(log10, 0.1, 5, 0.05, ";", Path.of(folder + "module_log10.csv"));

        CsvExporter.export(system, -5.0, 5.0, 0.01, ";", Path.of(folder + "system_final.csv"));
    }
}