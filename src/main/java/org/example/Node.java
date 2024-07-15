package org.example;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String label;
    private List<Node> children;

    public Node(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public Node(String label, String value) {
        this.label = label;
        this.children = new ArrayList<>();
        this.children.add(new Node(value));
    }

    public Node(String label, Node... children) {
        this.label = label;
        this.children = new ArrayList<>();
        for (Node child : children) {
            this.children.add(child);
        }
    }

    public String getLabel() {
        return label;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
