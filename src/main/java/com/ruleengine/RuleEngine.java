package com.ruleengine;

import java.util.Map;

public class RuleEngine {
    private final RuleParser parser;

    public RuleEngine() {
        this.parser = new RuleParser();
    }

    public Node createRule(String ruleString) {
        return parser.parse(ruleString);
    }

    public boolean evaluateRule(Node node, Map<String, Object> data) {
        if (node == null) {
            return false;
        }

        if (node.getType().equals("operator")) {
            switch (node.getValue()) {
                case "AND":
                    return evaluateRule(node.getLeft(), data) && evaluateRule(node.getRight(), data);
                case "OR":
                    return evaluateRule(node.getLeft(), data) || evaluateRule(node.getRight(), data);
                case ">":
                case "<":
                case "=":
                    return evaluateComparison(node, data);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + node.getValue());
            }
        } else if (node.getType().equals("operand")) {
            // For operands that are part of a comparison, we'll handle them in evaluateComparison
            Object value = data.get(node.getValue());
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            // If it's not a boolean, this operand is probably part of a comparison
            // and will be handled by evaluateComparison
            return true;
        }

        return false;
    }

    private boolean evaluateComparison(Node node, Map<String, Object> data) {
        int leftValue = getNumericValue(node.getLeft().getValue(), data);
        int rightValue = getNumericValue(node.getRight().getValue(), data);

        switch (node.getValue()) {
            case ">":
                return leftValue > rightValue;
            case "<":
                return leftValue < rightValue;
            case "=":
                return leftValue == rightValue;
            default:
                throw new IllegalArgumentException("Invalid comparison operator: " + node.getValue());
        }
    }

    private int getNumericValue(String key, Map<String, Object> data) {
        Object value = data.get(key);
        if (value == null) {
            try {
                return Integer.parseInt(key);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("No value found for key: " + key);
            }
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new IllegalArgumentException("Value is not a number: " + value);
    }
}