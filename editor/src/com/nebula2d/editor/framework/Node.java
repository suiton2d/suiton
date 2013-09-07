package com.nebula2d.editor.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Node {
    public Node parent;
    public Node nextSibling;
    public Node prevSibling;
    public List<Node> children;

    public Node() {
        this.children = Collections.synchronizedList(new LinkedList<Node>());
    }

    public boolean hasParent() {
        return this.parent != null;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public boolean hasSibling() {
        return this.nextSibling != null || this.prevSibling != null;
    }

    public void attach(Node node) {
        this.children.add(node);
    }

    public void attachTo(Node node) {
        node.attach(this);
    }

    public Node next() {
        if (nextSibling != null) {
            return nextSibling;
        }

        if (!hasChildren()) {
            return null;
        }

        return children.get(0);
    }

    public Node prev() {
        return parent;
    }

    public int getChildCount() {
        return children.size();
    }
}
