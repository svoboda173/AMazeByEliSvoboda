package edu.wm.cs.cs301.EliSvoboda.generation;

public class TreeNode {
    TreeNode parent = null;
    TreeNode left = null;
    TreeNode right = null;
    int value = 0;

    public TreeNode () {
    }

    public TreeNode getRoot() {
        TreeNode p = this;
        while (p.parent != null) {
            p = p.parent;
        }
        return p;
    }

    public void insert(TreeNode newChild) {
        newChild.parent = this;
        if (this.left == null) {
            this.left = newChild;
        } else {
            this.right = newChild;
        }
    }
}
