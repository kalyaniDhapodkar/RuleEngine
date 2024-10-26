package com.ruleengine;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        RuleEngine ruleEngine = new RuleEngine();
        Map<String, Object> data = new HashMap<>();
        data.put("age", 35); // Ensure this key exists
        data.put("salary", 40000); // Ensure this key exists

        String ruleString = "age > 30 AND salary > 30000"; // Example rule
        Node rule = ruleEngine.createRule(ruleString);
        boolean result = ruleEngine.evaluateRule(rule, data);
        System.out.println("Rule evaluation result: " + result);
    }
}