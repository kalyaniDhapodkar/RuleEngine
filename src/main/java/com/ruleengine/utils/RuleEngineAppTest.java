package com.ruleengine;

import com.ruleengine.services.RuleService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RuleEngineAppTest {
    private RuleService ruleService;

    @BeforeEach
    public void setUp() {
        ruleService = new RuleService();
    }

    @Test
    public void testEvaluateRuleTrue() {
        String rule = "age > 30 AND department = 'Sales'";
        JSONObject data = new JSONObject();
        data.put("age", 35);
        data.put("department", "Sales");

        boolean result = ruleService.evaluateRule(ruleService.createAST(rule), data);
        assertTrue(result);
    }

    @Test
    public void testEvaluateRuleFalse() {
        String rule = "age > 30 AND department = 'Sales'";
        JSONObject data = new JSONObject();
        data.put("age", 25);
        data.put("department", "Marketing");

        boolean result = ruleService.evaluateRule(ruleService.createAST(rule), data);
        assertFalse(result);
    }

    @Test
    public void testModifyRule() {
        String oldRule = "age > 30 AND department = 'Sales'";
        String newRule = "age > 40 AND department = 'Sales'";
        
        ruleService.createRule(oldRule);
        ruleService.modifyRule(oldRule, newRule);

        JSONObject data = new JSONObject();
        data.put("age", 45);
        data.put("department", "Sales");

        boolean result = ruleService.evaluateRule(ruleService.createAST(newRule), data);
        assertTrue(result);
    }
}
