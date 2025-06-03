package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    private final ChartPanel chart;
    private final JTextArea outputArea;

    private final JComboBox<String> odeSelector;
    private final JTextField x0Field;
    private final JTextField xnField;
    private final JTextField y0Field;
    private final JTextField stepField;
    private final JTextField accuracyField;

    public MainFrame() {
        super("Решение ОДУ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);

        JPanel left = new JPanel(new BorderLayout());

        
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Параметры решения ОДУ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Выберите ОДУ:"), gbc);
        gbc.gridx = 1;
        String[] odes = {"y' = x + y", "y' = sin(x) - y", "y' = e^x"};
        odeSelector = new JComboBox<>(odes);
        topPanel.add(odeSelector, gbc);

        
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("x₀:"), gbc);
        gbc.gridx = 1;
        x0Field = new JTextField("0", 8);
        topPanel.add(x0Field, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        topPanel.add(new JLabel("xₙ:"), gbc);
        gbc.gridx = 1;
        xnField = new JTextField("1", 8);
        topPanel.add(xnField, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        topPanel.add(new JLabel("y₀:"), gbc);
        gbc.gridx = 1;
        y0Field = new JTextField("1", 8);
        topPanel.add(y0Field, gbc);

        
        gbc.gridx = 0; gbc.gridy = 4;
        topPanel.add(new JLabel("Начальный шаг h:"), gbc);
        gbc.gridx = 1;
        stepField = new JTextField("0.1", 8);
        topPanel.add(stepField, gbc);

        
        gbc.gridx = 0; gbc.gridy = 5;
        topPanel.add(new JLabel("Требуемая точность ε:"), gbc);
        gbc.gridx = 1;
        accuracyField = new JTextField("0.1", 8);
        topPanel.add(accuracyField, gbc);

        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton calculateBtn = new JButton("Рассчитать");
        topPanel.add(calculateBtn, gbc);

        left.add(topPanel, BorderLayout.NORTH);

        
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Результаты"));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        outputPanel.setPreferredSize(new Dimension(0, 300));
        left.add(outputPanel, BorderLayout.CENTER);

        
        chart = new ChartPanel();
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, chart);
        mainSplit.setDividerLocation(500);
        add(mainSplit);

        
        calculateBtn.addActionListener(e -> onCalculate());
    }

    private void onCalculate() {
        try {
            
            int odeIndex = odeSelector.getSelectedIndex();
            double x0 = Double.parseDouble(x0Field.getText().replace(',', '.'));
            double xn = Double.parseDouble(xnField.getText().replace(',', '.'));
            double y0 = Double.parseDouble(y0Field.getText().replace(',', '.'));
            double initialStep = Double.parseDouble(stepField.getText().replace(',', '.'));
            double eps = Double.parseDouble(accuracyField.getText().replace(',', '.'));

            if (x0 >= xn) {
                throw new IllegalArgumentException("x₀ должно быть меньше xₙ");
            }
            if (initialStep <= 0) {
                throw new IllegalArgumentException("Начальный шаг должен быть положительным");
            }
            if (eps <= 0) {
                throw new IllegalArgumentException("Точность должна быть положительной");
            }

            ODESystem odeSystem = ODEFactory.createODE(odeIndex);

            StringBuilder result = new StringBuilder();
            result.append(String.format("Решение ОДУ: %s\n", odeSelector.getSelectedItem()));
            result.append(String.format("Интервал: [%.3f, %.3f], y₀ = %.3f\n", x0, xn, y0));
            result.append(String.format("Начальный шаг h = %.6f, требуемая точность ε = %.8f\n\n", initialStep, eps));

            
            RungeHelper.RungeResult eulerRunge =
                    EulerMethod.solveWithRunge(odeSystem, x0, xn, y0, eps, initialStep);
            List<Double> xEuler = eulerRunge.getXValues();
            List<Double> yEuler = eulerRunge.getYValues();
            List<Double> localErrE = eulerRunge.getRungeErrs();
            double actualStepE = eulerRunge.getHUsed();

            
            double maxLocalE = RungeHelper.max(localErrE);

            
            RungeHelper.RungeResult improvedRunge =
                    ImprovedEulerMethod.solveWithRunge(odeSystem, x0, xn, y0, eps, initialStep);
            List<Double> xImproved = improvedRunge.getXValues();
            List<Double> yImproved = improvedRunge.getYValues();
            List<Double> localErrI = improvedRunge.getRungeErrs();
            double actualStepI = improvedRunge.getHUsed();

            
            double maxLocalI = RungeHelper.max(localErrI);

            
            List<Double> xMilne = RungeHelper.buildXGrid(x0, xn, initialStep);
            List<Double> yMilne = MilneMethod.solve(odeSystem, x0, xn, y0, initialStep, eps / 100);

            
            List<Double> globalErrMilne = MilneMethod.calculateGlobalErrors(odeSystem, xMilne, yMilne, x0, y0);
            double maxGlobalM = RungeHelper.max(globalErrMilne);

            
            List<Double> exactXUnion = mergeSortedLists(xEuler, xImproved);
            exactXUnion = mergeSortedLists(exactXUnion, xMilne);
            List<Double> exactYUnion = new ArrayList<>();
            for (double xi : exactXUnion) {
                exactYUnion.add(odeSystem.exactSolution(xi, x0, y0));
            }

            
            List<Double> exactEuler = new ArrayList<>();
            for (double xi : xEuler) {
                exactEuler.add(odeSystem.exactSolution(xi, x0, y0));
            }
            List<Double> exactImproved = new ArrayList<>();
            for (double xi : xImproved) {
                exactImproved.add(odeSystem.exactSolution(xi, x0, y0));
            }
            List<Double> exactMilne = new ArrayList<>();
            for (double xi : xMilne) {
                exactMilne.add(odeSystem.exactSolution(xi, x0, y0));
            }

            
            result.append("=== Метод Эйлера ===\n");
            result.append(String.format("Фактический шаг h = %.8f", actualStepE));
            if (Math.abs(actualStepE - initialStep) > 1e-10) {
                result.append(" (уменьшен из начального)");
            } else {
                result.append(" (начальный шаг подошел)");
            }
            result.append("\n");
            result.append(String.format("Достигнутая точность (max по Рунге) = %.8e\n\n", maxLocalE));

            result.append(String.format("%-8s %-12s\n", "x", "y"));
            result.append("-".repeat(25)).append("\n");
            for (int i = 0; i < xEuler.size(); i++) {
                result.append(String.format("%-8.3f %-12.6f\n", xEuler.get(i), yEuler.get(i)));
            }
            result.append("\n");

            
            result.append("=== Модифицированный метод Эйлера ===\n");
            result.append(String.format("Фактический шаг h = %.8f", actualStepI));
            if (Math.abs(actualStepI - initialStep) > 1e-10) {
                result.append(" (уменьшен из начального)");
            } else {
                result.append(" (начальный шаг подошел)");
            }
            result.append("\n");
            result.append(String.format("Достигнутая точность (max по Рунге) = %.8e\n\n", maxLocalI));

            result.append(String.format("%-8s %-12s\n", "x", "y"));
            result.append("-".repeat(25)).append("\n");
            for (int i = 0; i < xImproved.size(); i++) {
                result.append(String.format("%-8.3f %-12.6f\n", xImproved.get(i), yImproved.get(i)));
            }
            result.append("\n");

            
            result.append("=== Метод Милна ===\n");
            result.append(String.format("Шаг h = %.8f\n", initialStep));
            result.append(String.format("Максимальная глобальная погрешность = %.8e\n\n", maxGlobalM));

            result.append(String.format("%-8s %-12s %-12s %-12s\n", "x", "y_числ", "y_точн", "погрешн"));
            result.append("-".repeat(50)).append("\n");
            for (int i = 0; i < xMilne.size(); i++) {
                result.append(String.format("%-8.3f %-12.6f %-12.6f %-12.2e\n",
                        xMilne.get(i), yMilne.get(i), exactMilne.get(i), globalErrMilne.get(i)));
            }
            result.append("\n");

            outputArea.setText(result.toString());

            
            chart.setData(
                    exactXUnion, exactYUnion,
                    xEuler, exactEuler, yEuler,
                    xImproved, exactImproved, yImproved,
                    xMilne, exactMilne, yMilne
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка: " + ex.getMessage(),
                    "Ошибка расчёта",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Объединяет две отсортированные (по возрастанию) сетки без дублей в одну:
     * x1 и x2 – возрастающие списки узлов. Результат – также строго возрастающий список.
     */
    private List<Double> mergeSortedLists(List<Double> x1, List<Double> x2) {
        List<Double> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < x1.size() && j < x2.size()) {
            double v1 = x1.get(i);
            double v2 = x2.get(j);
            if (Math.abs(v1 - v2) < 1e-12) {
                merged.add(v1);
                i++; j++;
            } else if (v1 < v2) {
                merged.add(v1);
                i++;
            } else {
                merged.add(v2);
                j++;
            }
        }
        while (i < x1.size()) {
            merged.add(x1.get(i++));
        }
        while (j < x2.size()) {
            merged.add(x2.get(j++));
        }
        
        List<Double> unique = new ArrayList<>();
        Double prev = null;
        for (Double v : merged) {
            if (prev == null || Math.abs(v - prev) > 1e-12) {
                unique.add(v);
                prev = v;
            }
        }
        return unique;
    }
}