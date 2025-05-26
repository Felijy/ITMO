package ui;

import exceptions.TooLittlePointsException;
import exceptions.TooManyPointsException;
import model.ExponentialFunction;
import model.Function;
import model.LinearFunction;
import model.LogarithmicFunction;
import model.Polynomial2Function;
import model.Polynomial3Function;
import model.PowerFunction;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final JFileChooser fileChooser = new JFileChooser();
    private final ChartPanel chart;
    private final JTextArea dataArea;
    private final JTextArea outputArea;

    public MainFrame() {
        super("Аппроксимация функций");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);

        JPanel left = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Входные точки (x; y по линии)"));
        dataArea = new JTextArea();
        inputPanel.add(new JScrollPane(dataArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JButton loadBtn = new JButton("Загрузить");
        JButton fitBtn = new JButton("Go!");
        JButton saveBtn = new JButton("Сохранить");
        btnPanel.add(loadBtn);
        btnPanel.add(fitBtn);
        btnPanel.add(saveBtn);
        inputPanel.add(btnPanel, BorderLayout.SOUTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Результаты"));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        outputPanel.setPreferredSize(new Dimension(0, 200));

        JSplitPane ioSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputPanel, outputPanel);
        ioSplit.setResizeWeight(0.6);
        left.add(ioSplit, BorderLayout.CENTER);

        chart = new ChartPanel();

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, chart);
        mainSplit.setDividerLocation(300);
        add(mainSplit);

        loadBtn.addActionListener(e -> loadFromFile());
        saveBtn.addActionListener(e -> saveToFile());
        fitBtn.addActionListener(e -> onFit());
    }

    private void loadFromFile() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                dataArea.read(br, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveToFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                outputArea.write(bw);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onFit() {
        try {
            String[] lines = dataArea.getText().split("\n");
            List<Double> xs = new ArrayList<>(), ys = new ArrayList<>();
            for (String l : lines) {
                if (l.trim().isEmpty()) continue;
                String[] p = l.trim().replace(',', '.').split("[;\s]+");
                xs.add(Double.parseDouble(p[0]));
                ys.add(Double.parseDouble(p[1]));
            }
            int n = xs.size();
            if (n < 7) throw new TooLittlePointsException();
            if (n > 12) throw new TooManyPointsException();

            double[] x = xs.stream().mapToDouble(d -> d).toArray();
            double[] y = ys.stream().mapToDouble(d -> d).toArray();

            List<Function> funcs = Arrays.asList(
                    new LinearFunction(), new Polynomial2Function(), new Polynomial3Function(),
                    new ExponentialFunction(), new LogarithmicFunction(), new PowerFunction()
            );

            StringBuilder sb = new StringBuilder();
            double bestRmse = Double.MAX_VALUE;
            Function best = null;

            double meanY = Arrays.stream(y).average().orElse(0);
            double ssTot = Arrays.stream(y).map(val -> (val - meanY) * (val - meanY)).sum();

            for (Function f : funcs) {
                f.fit(x, y);
                double ssRes = 0;
                double[] phi = new double[n], eps = new double[n];
                for (int i = 0; i < n; i++) {
                    phi[i] = f.evaluate(x[i]);
                    eps[i] = y[i] - phi[i];
                    ssRes += eps[i] * eps[i];
                }
                double rmse = Math.sqrt(ssRes / n);
                double r2 = 1 - ssRes / ssTot;
                sb.append(String.format("Функция: %s%n", f.getName()));
                if (!Double.isNaN(ssRes)) {
                    sb.append("Коэффициенты: ").append(Arrays.toString(f.getCoefficients())).append("\n");
                    sb.append(String.format("Мера отклонения S: %.5f", ssRes)).append("\n");
                    sb.append(String.format("Среднекв. отклонение: %.5f, R²: %.5f%n", rmse, r2));
                    if (r2 >= 0.95) {
                        sb.append("Высокая точность по R² \n");
                    } else if (0.75 <= r2) {
                        sb.append("Удовлетворительная точность по R² \n");
                    } else if (0.5 <= r2) {
                        sb.append("Слабая точность по R² \n");
                    } else {
                        sb.append("Недостаточная точность по R² \n");
                    }
                    if (f instanceof LinearFunction) {
                        double r = ((LinearFunction) f).getPearson();
                        sb.append(String.format("Пирсон r: %.5f%n", r));
                    }
                    sb.append("Data (x, y, φ(x), ε): \n");
                    for (int i = 0; i < n; i++) {
                        sb.append(String.format("%.3f, %.3f, %.3f, %.3f%n", x[i], y[i], phi[i], eps[i]));
                    }
                } else {
                    sb.append("Не может быть определена из-за наличия неположительных чисел\n");
                }
                sb.append("------------------------------------------------\n");
                if (rmse < bestRmse) {
                    bestRmse = rmse;
                    best = f;
                }
            }
            sb.append(String.format("Лучше всего описывает: \n%s \n(Среднекв. отклонение: %.5f)%n", best.getName(), bestRmse));

            outputArea.setText(sb.toString());
            chart.setData(x, y, funcs);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ошибка ввода: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
