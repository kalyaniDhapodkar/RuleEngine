package com.ruleengine.models;

public class Node {
    private String type; // "operator" or "operand"
    private Node left; // Left child
    private Node right; // Right child (for operators)
    private String value; // Value for operand nodes

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (type.equals("operator")) {
            return "(" + left.toString() + " " + value + " " + right.toString() + ")";
        } else {
            return value;
        }
    }
}
