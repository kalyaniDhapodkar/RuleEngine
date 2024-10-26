package com.ruleengine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RuleEngineUI extends JFrame {
    private final RuleEngine ruleEngine;
    private final JTextField ruleField;
    private final JPanel variablePanel;
    private final JTextArea resultArea;
    private final Map<String, JTextField> variableFields;

    public RuleEngineUI() {
        ruleEngine = new RuleEngine();
        variableFields = new HashMap<>();

        // Set up the main frame
        setTitle("Rule Engine UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(600, 400));

        // Rule input panel
        JPanel rulePanel = new JPanel(new BorderLayout());
        rulePanel.setBorder(BorderFactory.createTitledBorder("Enter Rule"));
        ruleField = new JTextField();
        ruleField.setToolTipText("Example: age > 30 AND salary > 50000");
        rulePanel.add(ruleField, BorderLayout.CENTER);

        // Variable panel
        variablePanel = new JPanel();
        variablePanel.setLayout(new BoxLayout(variablePanel, BoxLayout.Y_AXIS));
        variablePanel.setBorder(BorderFactory.createTitledBorder("Variables"));

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Result"));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addVarButton = new JButton("Add Variable");
        JButton evaluateButton = new JButton("Evaluate Rule");
        JButton clearButton = new JButton("Clear All");
        buttonPanel.add(addVarButton);
        buttonPanel.add(evaluateButton);
        buttonPanel.add(clearButton);

        // Add components to frame
        add(rulePanel, BorderLayout.NORTH);
        add(new JScrollPane(variablePanel), BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);

        // Button listeners
        addVarButton.addActionListener(e -> addVariableField());
        evaluateButton.addActionListener(e -> evaluateRule());
        clearButton.addActionListener(e -> clearAll());

        // Pack and display
        pack();
        setLocationRelativeTo(null);
    }

    private void addVariableField() {
        JPanel varRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JTextField nameField = new JTextField(10);
        JTextField valueField = new JTextField(10);
        JButton removeButton = new JButton("Remove");

        varRow.add(new JLabel("Name:"));
        varRow.add(nameField);
        varRow.add(new JLabel("Value:"));
        varRow.add(valueField);
        varRow.add(removeButton);

        variableFields.put(nameField.getText(), valueField);
        removeButton.addActionListener(e -> {
            variableFields.remove(nameField.getText());
            variablePanel.remove(varRow);
            variablePanel.revalidate();
            variablePanel.repaint();
        });

        variablePanel.add(varRow);
        variablePanel.revalidate();
        variablePanel.repaint();
    }

    private void evaluateRule() {
        try {
            String ruleString = ruleField.getText().trim();
            if (ruleString.isEmpty()) {
                showError("Please enter a rule");
                return;
            }

            Map<String, Object> data = new HashMap<>();
            for (Component comp : variablePanel.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel row = (JPanel) comp;
                    JTextField nameField = (JTextField) row.getComponent(1);
                    JTextField valueField = (JTextField) row.getComponent(3);
                    
                    String name = nameField.getText().trim();
                    String value = valueField.getText().trim();
                    
                    if (!name.isEmpty() && !value.isEmpty()) {
                        try {
                            data.put(name, Integer.parseInt(value));
                        } catch (NumberFormatException e) {
                            showError("Invalid number format for variable: " + name);
                            return;
                        }
                    }
                }
            }

            Node rule = ruleEngine.createRule(ruleString);
            boolean result = ruleEngine.evaluateRule(rule, data);
            resultArea.setText("Rule Evaluation Result: " + result);
            resultArea.setForeground(result ? new Color(0, 128, 0) : Color.RED);

        } catch (Exception e) {
            showError("Error evaluating rule: " + e.getMessage());
        }
    }

    private void clearAll() {
        ruleField.setText("");
        variablePanel.removeAll();
        variableFields.clear();
        resultArea.setText("");
        variablePanel.revalidate();
        variablePanel.repaint();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RuleEngineUI ui = new RuleEngineUI();
            ui.setVisible(true);
        });
    }
}