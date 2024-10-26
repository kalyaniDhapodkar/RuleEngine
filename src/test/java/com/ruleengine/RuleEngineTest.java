package com.ruleengine;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class RuleEngineTest {
    private RuleEngine ruleEngine;

    @Before
    public void setUp() {
        ruleEngine = new RuleEngine();
    }

    @Test
    public void testCombinedRulesTrue() {
        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("salary", 40000);

        String ruleString = "age > 30 AND salary > 30000";
        Node rule = ruleEngine.createRule(ruleString);
        assertTrue(ruleEngine.evaluateRule(rule, data));
    }

    @Test
    public void testCombinedRulesFalse() {
        Map<String, Object> data = new HashMap<>();
        data.put("age", 25);
        data.put("salary", 40000);

        String ruleString = "age > 30 AND salary > 30000";
        Node rule = ruleEngine.createRule(ruleString);
        assertFalse(ruleEngine.evaluateRule(rule, data));
    }
}