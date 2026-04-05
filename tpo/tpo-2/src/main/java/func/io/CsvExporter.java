package func.io;

import func.api.MathModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class CsvExporter {
    public static void export(MathModule module, double from, double to, double step, String delimiter, Path out) throws IOException {
        StringBuilder sb = new StringBuilder("X").append(delimiter).append("Result\n");
        for (double x = from; x <= to; x += step) {
            sb.append(String.format(Locale.US, "%.10f%s%.10f%n", x, delimiter, module.calculate(x)));
        }
        Files.writeString(out, sb.toString());
    }
}
