package tool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    private final JTextField tField;
    private final JTextField rField;

    private final Stack<Double> numStack = new Stack<>();
    private final Stack<String> opStack = new Stack<>();
    private final StringBuffer num = new StringBuffer();

    public Calculator() {
        tField = new JTextField(35);
        rField = new JTextField(35);
        JPanel tPanel = new JPanel();
        JPanel panel = new JPanel();

        tPanel.setLayout(new GridLayout(2, 1, 3, 3));
        panel.setLayout(new GridLayout(0, 5, 3, 3));
        JButton[] buttons = new JButton[25];

        int index = 0;
        for (int rows = 0; rows < 5; rows++) {
            for (int cols = 0; cols < 5; cols++) {
                String[] labels = {
                        "", "", "", "CE", "%",
                        "7", "8", "9", "/", "sqrt",
                        "4", "5", "6", "x", "^",
                        "1", "2", "3", "-", "1/x",
                        "0", "+/-", ".", "+", "=",
                };
                buttons[index] = new JButton(labels[index]);
                buttons[index].addActionListener(this);
                panel.add(buttons[index]);
                index++;
            }
        }

        tPanel.add(tField, BorderLayout.NORTH);
        tPanel.add(rField, BorderLayout.CENTER);
        add(tPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String key = source.getText();

        if ("0123456789.".contains(key)) {
            num.append(key);
            tField.setText(tField.getText() + key);
        } else if ("+-x/^".contains(key)) {
            if (!num.isEmpty()) {
                numStack.push(Double.parseDouble(num.toString()));
                num.setLength(0);
            }
            while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(key)) {
                processOperation();
            }
            opStack.push(key);
            tField.setText(tField.getText() + key);
        } else if (key.equals("=")) {
            if (!num.isEmpty()) {
                numStack.push(Double.parseDouble(num.toString()));
                num.setLength(0);
            }
            while (!opStack.isEmpty()) {
                processOperation();
            }
            if (!numStack.isEmpty()) {
                double result = numStack.pop();
                rField.setText(String.valueOf(result));
            }
        } else if (key.equals("CE")) {
            numStack.clear();
            opStack.clear();
            num.setLength(0);
            tField.setText("");
            rField.setText("");
        }
    }

    private void processOperation() {
        if (numStack.size() < 2) return;
        double b = numStack.pop();
        double a = numStack.pop();
        String op = opStack.pop();

        switch (op) {
            case "+" -> numStack.push(a + b);
            case "-" -> numStack.push(a - b);
            case "x" -> numStack.push(a * b);
            case "/" -> numStack.push(a / b);
            case "^" -> numStack.push(Math.pow(a, b));
        }
    }

    private int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "x", "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
