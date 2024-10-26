package com.ruleengine;

public class RuleParser {
    public Node parse(String ruleString) {
        String[] parts = ruleString.split(" AND | OR ");
        if (parts.length == 1) {
            return parseComparison(parts[0].trim());
        }

        String operator = ruleString.contains(" AND ") ? "AND" : "OR";
        Node left = parseComparison(parts[0].trim());
        Node right = parseComparison(parts[1].trim());
        return new Node("operator", operator, left, right);
    }

    private Node parseComparison(String comparison) {
        String[] parts = comparison.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid comparison: " + comparison);
        }

        Node left = new Node("operand", parts[0]);
        Node right = new Node("operand", parts[2]);
        return new Node("operator", parts[1], left, right);
    }
}