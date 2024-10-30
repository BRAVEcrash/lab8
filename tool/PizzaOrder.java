package tool;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PizzaOrder extends JFrame implements ActionListener {
    private int baseCost, toppingCost;
    private double sizeMultiplier = 1.0;
    private final JButton orderButton;
    private final JButton cancelButton;
    private final JTextField text;

    WelcomePanel welcomePanel = new WelcomePanel();
    TypePanel typePanel = new TypePanel();
    ToppingPanel toppingPanel = new ToppingPanel();
    SizePanel sizePanel = new SizePanel();

    public PizzaOrder() {
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("피자 주문");

        this.orderButton = new JButton("주문");
        this.orderButton.addActionListener(this);
        this.cancelButton = new JButton("취소");
        this.cancelButton.addActionListener(this);

        this.text = new JTextField();
        text.setFont(new Font("Arial", Font.BOLD, 20));
        text.setEditable(false);
        text.setColumns(6);

        JPanel downPanel = new JPanel();
        downPanel.add(this.orderButton);
        downPanel.add(this.cancelButton);
        downPanel.add(this.text);

        this.setLayout(new BorderLayout());
        this.add(welcomePanel, BorderLayout.NORTH);
        this.add(downPanel, BorderLayout.SOUTH);
        this.add(sizePanel, BorderLayout.EAST);
        this.add(typePanel, BorderLayout.WEST);
        this.add(toppingPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.orderButton) {
            // 주문 버튼을 클릭하면 총 가격 계산
            int totalCost = (int) Math.round((baseCost + toppingCost) * sizeMultiplier);
            this.text.setText("" + totalCost);
        }
        if (e.getSource() == this.cancelButton) {
            // 취소 버튼을 클릭하면 모든 값 초기화
            baseCost = toppingCost = 0;
            sizeMultiplier = 1.0;
            this.text.setText("");
        }
    }


    static class WelcomePanel extends JPanel {
        public WelcomePanel() {
            JLabel message = new JLabel("자바 피자에 오신 것을 환영합니다.");
            add(message);
        }
    }

    class TypePanel extends JPanel {

        public TypePanel() {
            setLayout(new GridLayout(3, 1));
            JRadioButton combo = new JRadioButton("콤보", true);
            JRadioButton potato = new JRadioButton("포테이토");
            JRadioButton bulgogi = new JRadioButton("불고기");

            combo.addActionListener(e -> baseCost = 12000);
            potato.addActionListener(e -> baseCost = 15000);
            bulgogi.addActionListener(e -> baseCost = 18000);

            ButtonGroup bg = new ButtonGroup();
            bg.add(combo);
            bg.add(potato);
            bg.add(bulgogi);

            setBorder(BorderFactory.createTitledBorder("종류"));
            add(combo);
            add(potato);
            add(bulgogi);
        }
    }

    class ToppingPanel extends JPanel {
        private final JCheckBox pepper;
        private final JCheckBox cheese;
        private final JCheckBox peperoni;
        private final JCheckBox bacon;

        public ToppingPanel() {
            setLayout(new GridLayout(4, 1));

            pepper = new JCheckBox("피망");
            cheese = new JCheckBox("치즈");
            peperoni = new JCheckBox("페페로니");
            bacon = new JCheckBox("베이컨");

            pepper.addActionListener(e -> toppingCost += pepper.isSelected() ? 1000 : -1000);
            cheese.addActionListener(e -> toppingCost += cheese.isSelected() ? 1200 : -1200);
            peperoni.addActionListener(e -> toppingCost += peperoni.isSelected() ? 1400 : -1400);
            bacon.addActionListener(e -> toppingCost += bacon.isSelected() ? 1600 : -1600);

            setBorder(BorderFactory.createTitledBorder("추가토핑"));
            add(pepper);
            add(cheese);
            add(peperoni);
            add(bacon);
        }
    }

    class SizePanel extends JPanel {

        public SizePanel() {
            setLayout(new GridLayout(3, 1));
            JRadioButton small = new JRadioButton("Small", true);
            JRadioButton medium = new JRadioButton("Medium");
            JRadioButton large = new JRadioButton("Large");

            small.addActionListener(e -> sizeMultiplier = 1.0);
            medium.addActionListener(e -> sizeMultiplier = 1.5);
            large.addActionListener(e -> sizeMultiplier = 2.0);

            ButtonGroup bg = new ButtonGroup();
            bg.add(small);
            bg.add(medium);
            bg.add(large);

            setBorder(BorderFactory.createTitledBorder("크기"));
            add(small);
            add(medium);
            add(large);
        }
    }
}
