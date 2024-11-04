import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fastcgi.FCGIInterface;

public class Main {

    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();

        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
                long startTime = System.nanoTime();
                String requestBody = readRequestBody();

                String x = getParamValue(requestBody, "x");
                String y = getParamValue(requestBody, "y");
                String r = getParamValue(requestBody, "r");

                boolean isValid = validateParameters(x, y, r);

                String result = isValid ? checkHit(x, y, r) : "Неверные данные";

                long executionTime = System.nanoTime() - startTime;
                String currentTime = getCurrentTime();

                String jsonResponse = generateJSON(x, y, r, result, currentTime, executionTime);

                String httpResponse;

                if (!isValid) {
                    httpResponse = """
                        HTTP/1.1 400 Bad Request
                        Content-Type: application/json
                        Content-Length: %d

                        %s
                        """.formatted(jsonResponse.getBytes(StandardCharsets.UTF_8).length, jsonResponse);

                }
                else {
                    httpResponse = """
                        HTTP/1.1 200 OK
                        Content-Type: application/json
                        Content-Length: %d

                        %s
                        """.formatted(jsonResponse.getBytes(StandardCharsets.UTF_8).length, jsonResponse);
                }

                System.out.println(httpResponse);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readRequestBody() throws IOException {
        FCGIInterface.request.inStream.fill();
        int contentLength = FCGIInterface.request.inStream.available();
        var buffer = ByteBuffer.allocate(contentLength);
        int readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
        return new String(buffer.array(), 0, readBytes, StandardCharsets.UTF_8);
    }

    private static String getParamValue(String requestBody, String paramName) {
        String[] params = requestBody.split("&");
        for (String param : params) {
            if (param.startsWith(paramName + "=")) {
                return param.split("=")[1];
            }
        };
        return "";
    }

    private static boolean validateParameters(String x, String y, String r) {
        try {
            double xVal = Double.parseDouble(x);
            double yVal = Double.parseDouble(y);
            double rVal = Double.parseDouble(r);
            return xVal >= -3 && xVal <= 5 && yVal > -5 && yVal < 3 && rVal > 1 && rVal < 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String checkHit(String x, String y, String r) {
        double X = Double.parseDouble(x);
        double Y = Double.parseDouble(y);
        double R = Double.parseDouble(r);

        boolean hit =
                (X <= 0 && Y >= 0 && Y <= R/2 + X) ||
                (X >= 0 && Y >= 0 && X*X + Y*Y <= ((R/2)*(R/2))) ||
                (X >= 0 && Y <= 0 && X <= R && Y >= -R);

        return hit ? "Попадание" : "Промах";
    }

    private static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    private static String generateJSON (String x, String y, String r, String result, String currentTime, long executionTime) {
        return """
                {
                    "x": "%s",
                    "y": "%s",
                    "r": "%s",
                    "result": "%s",
                    "currentTime": "%s",
                    "executionTime": "%d"
                }
                """.formatted(x, y, r, result, currentTime, executionTime);
    }
}
