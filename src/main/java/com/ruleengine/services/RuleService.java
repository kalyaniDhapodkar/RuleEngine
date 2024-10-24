package com.ruleengine.services;

import com.ruleengine.models.Node;
import com.ruleengine.models.Rule;
import com.ruleengine.utils.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

import java.util.List;

public class RuleService {
    private final MongoDatabase database = MongoDBConnection.connect();

    // Create Rule and Store in Database
    public void createRule(String ruleString) {
        try {
            Node ast = createAST(ruleString);
            Rule rule = new Rule(ruleString, ast);

            MongoCollection<Document> collection = database.getCollection("rules");
            Document doc = new Document("ruleString", rule.getRuleString())
                    .append("ast", rule.getAst().toString());
            collection.insertOne(doc);
        } catch (Exception e) {
            System.out.println("Error creating rule: " + e.getMessage());
        }
    }

    // Evaluate Rule based on JSON data
    public boolean evaluateRule(Node ast, JSONObject data) {
        return evaluateAST(ast, data);
    }

    // Convert rule string to AST
    public Node createAST(String ruleString) throws IllegalArgumentException {
        if (ruleString == null || ruleString.isEmpty()) {
            throw new IllegalArgumentException("Rule string cannot be null or empty");
        }

        // A very simplified parsing logic for the sake of demonstration.
        // You may implement a proper parser based on your rule syntax.
        if (ruleString.contains("AND")) {
            String[] parts = ruleString.split(" AND ");
            Node left = createAST(parts[0].trim());
            Node right = createAST(parts[1].trim());
            return new Node("operator", left, right, "AND");
        } else if (ruleString.contains("OR")) {
            String[] parts = ruleString.split(" OR ");
            Node left = createAST(parts[0].trim());
            Node right = createAST(parts[1].trim());
            return new Node("operator", left, right, "OR");
        } else {
            return new Node("operand", null, null, ruleString);
        }
    }

    // Evaluate the AST against JSON data
    private boolean evaluateAST(Node node, JSONObject data) {
        if (node.getType().equals("operator")) {
            if (node.getValue().equals("AND")) {
                return evaluateAST(node.getLeft(), data) && evaluateAST(node.getRight(), data);
            } else if (node.getValue().equals("OR")) {
                return evaluateAST(node.getLeft(), data) || evaluateAST(node.getRight(), data);
            }
        } else if (node.getType().equals("operand")) {
            String[] parts = node.getValue().split(" ");
            String attribute = parts[0];
            String operator = parts[1];
            String value = parts[2].replace("'", ""); // Remove quotes for string comparison

            // Evaluate condition based on the operator
            switch (operator) {
                case ">":
                    return data.getInt(attribute) > Integer.parseInt(value);
                case "<":
                    return data.getInt(attribute) < Integer.parseInt(value);
                case "=":
                    return data.getString(attribute).equals(value);
                default:
                    return false;
            }
        }
        return false;
    }

    // Modify existing rule in the database
    public void modifyRule(String oldRuleString, String newRuleString) {
        try {
            Node newAST = createAST(newRuleString);
            MongoCollection<Document> collection = database.getCollection("rules");

            Document query = new Document("ruleString", oldRuleString);
            Document updatedDocument = new Document("$set", new Document("ruleString", newRuleString)
                    .append("ast", newAST.toString()));
            collection.updateOne(query, updatedDocument);
        } catch (Exception e) {
            System.out.println("Error modifying rule: " + e.getMessage());
        }
    }

    // User-defined function example
    public boolean evaluateUserDefinedFunction(String functionName, JSONObject data) {
        // Example of user-defined function handling
        if (functionName.equals("isSenior")) {
            return data.getInt("age") > 65;
        }
        return false;
    }
}
