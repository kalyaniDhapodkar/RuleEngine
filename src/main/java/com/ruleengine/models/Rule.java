package com.ruleengine.models;

public class Rule {
    private String ruleString;
    private Node ast; // Abstract Syntax Tree representation

    public Rule(String ruleString, Node ast) {
        this.ruleString = ruleString;
        this.ast = ast;
    }

    // Getters and setters
    public String getRuleString() {
        return ruleString;
    }

    public void setRuleString(String ruleString) {
        this.ruleString = ruleString;
    }

    public Node getAst() {
        return ast;
    }

    public void setAst(Node ast) {
        this.ast = ast;
    }
}
