package com.taskbuddy.structures.tree;

import com.taskbuddy.models.Task;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private Task task;
    private TreeNode parent;
    private List<TreeNode> children;

    public TreeNode(Task task) {
        this.task = task;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void removeChild(TreeNode child) {
        child.setParent(null);
        this.children.remove(child);
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }
}