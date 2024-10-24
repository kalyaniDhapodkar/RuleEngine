package com.ruleengine;

import com.ruleengine.services.RuleService;
import org.json.JSONObject;

public class RuleEngineApp {
    public static void main(String[] args) {
        RuleService ruleService = new RuleService();

        // Example rule
        String rule1 = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)";
        
        // Create and store rule
        ruleService.createRule(rule1);

        // Modify existing rule
        String modifiedRule = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 60000 OR experience > 5)";
        ruleService.modifyRule(rule1, modifiedRule);

        // Example JSON data
        JSONObject data = new JSONObject();
        data.put("age", 35);
        data.put("department", "Sales");
        data.put("salary", 60000);
        data.put("experience", 3);

        // Evaluate the rule
        boolean result = ruleService.evaluateRule(ruleService.createAST(modifiedRule), data);
        System.out.println("Rule Evaluation Result: " + result);
    }
}
