package web.lab.web21;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        try {
            double x = Double.parseDouble(xParam);
            double y = Double.parseDouble(yParam);
            double r = Double.parseDouble(rParam);

            boolean isInside = checkArea(x, y, r);

            HttpSession session = request.getSession();

            if (!checkIfCorrect(x, y, r)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные. Введите правильные значения.");
            } else {

                Result result = new Result(x, y, r, isInside, LocalDateTime.now(), System.nanoTime());

                List<Result> results = (List<Result>) session.getAttribute("results");
                if (results == null) {
                    results = new ArrayList<>();
                }

                results.add(result);
                session.setAttribute("results", results);

                response.sendRedirect(request.getContextPath() + "/results.jsp");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные. Введите правильные значения.");
        }
    }

    private boolean checkArea(double x, double y, double r) {
        return (x <= 0 && y <= 0 && x*x + y*y <= ((r/2)*(r/2))) ||   // четверть круга
                (x >= 0 && y <= 0 && x <= r/2 && y >= -r) ||         // прямоугольник
                (x >= 0 && y >= 0 && y <= r/2 - x);                  // треугольник
    }

    private boolean checkIfCorrect(double x, double y, double r) {
        return ((-3 <= x) && (x <= 5)) &&
                ((-5 <= y) && (y <= 3)) &&
                ((r == 1) || (r == 2) || (r == 3) || (r == 4) || (r == 5));
    }

    public static class Result {
        private final double x;
        private final double y;
        private final double r;
        private final boolean result;
        private final LocalDateTime time;
        private final long executionTime;

        public Result(double x, double y, double r, boolean result, LocalDateTime time, long executionTime) {
            this.x = x;
            this.y = y;
            this.r = r;
            this.result = result;
            this.time = time;
            this.executionTime = executionTime;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getR() {
            return r;
        }

        public boolean isResult() {
            return result;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public long getExecutionTime() {
            return executionTime;
        }
    }
}
