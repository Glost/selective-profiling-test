/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the tree of calls.
 */
public class CallTree {

    /**
     * The root node of the tree.
     */
    private CallNode root;

    /**
     * The CallTree's iterator.
     * Helps to traverse the tree.
     */
    private Iterator iterator;

    /**
     * The id of the next added node.
     */
    private int nextId;

    /**
     * Constructor.
     * @param rootMethod The method's name of the root node's call.
     * @param rootArg The argument of the root node's call.
     */
    public CallTree(String rootMethod, String rootArg) {
        root = new CallNode(rootMethod, rootArg, null);
        iterator = new Iterator(root);
    }

    /**
     * Returns the root node.
     * @return The root node.
     */
    public CallNode getRoot() {
        return root;
    }

    /**
     * Returns the CallTree's iterator.
     * @return The CallTree's iterator.
     */
    public Iterator getIterator() {
        return iterator;
    }

    /**
     * Represents the node of the tree of calls.
     */
    public class CallNode {

        /**
         * The method name of the node's call.
         */
        private String method;

        /**
         * The argument of the node's call.
         */
        private String arg;

        /**
         * The node's id in the tree.
         */
        private int id;

        /**
         * The node's parent.
         * If the node is root, the parent equals null.
         */
        private CallNode parent;

        /**
         * The node's children list.
         */
        private List<CallNode> children = new ArrayList<CallNode>();

        /**
         * Constructor.
         * @param method The method name of the node's call.
         * @param arg The argument of the node's call.
         * @param parent The node's id in the tree.
         */
        private CallNode(String method, String arg, CallNode parent) {
            this.method = method;
            this.arg = arg;
            this.parent = parent;
            id = nextId++;
        }

        /**
         * Returns the method name of the node's call
         * @return The method name of the node's call
         */
        public String getMethod() {
            return method;
        }

        /**
         * Returns the argument of the node's call.
         * @return The argument of the node's call.
         */
        public String getArg() {
            return arg;
        }

        /**
         * Returns the node's id in the tree.
         * @return The node's id in the tree.
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the node's parent.
         * If the node is root, the parent equals null.
         * @return The node's parent.
         */
        public CallNode getParent() {
            return parent;
        }

        /**
         * Returns the tree of the node.
         * @return The tree of the node.
         */
        public CallTree getCallTree() {
            return CallTree.this;
        }

        /**
         * Adds new child to the node.
         * @param method The method name of the child node's call.
         * @param arg The argument of the child node's call.
         * @return The new created child node.
         */
        public CallNode addChild(String method, String arg) {
            CallNode child = new CallNode(method, arg, this);
            children.add(child);
            return child;
        }

        /**
         * Returns the node's children list's clone (for better protecting the inner structure of the tree).
         * @return The node's children list's clone.
         */
        public List<CallNode> getChildren() {
            return ((List<CallNode>)((ArrayList<CallNode>)children).clone());
        }

        /**
         * Returns the string equivalent of the node.
         * @return The string equivalent of the node.
         */
        @Override
        public String toString() {
            return "(" + method + "," + arg + "," + id + ")";
        }
    }

    /**
     * Represents the CallTree's iterator which helps to traverse the tree.
     */
    public class Iterator {

        /**
         * The current node - the current position in the tree of the iterator.
         */
        private CallNode currentCallNode;

        /**
         * Constructor.
         * @param currentCallNode The current node - the current position in the tree of the iterator.
         */
        private Iterator(CallNode currentCallNode) {
            this.currentCallNode = currentCallNode;
        }

        /**
         * Returns the current node - the current position in the tree of the iterator.
         * @return The current node - the current position in the tree of the iterator.
         */
        public CallNode getCurrentCallNode() {
            return currentCallNode;
        }

        /**
         * Makes iterator go to the parent if the current node is not root.
         */
        public void goToParent() {
            if(currentCallNode.parent == null) {
                return;
            }

            currentCallNode = currentCallNode.parent;
        }

        /**
         * Makes iterator go to the current node's given child.
         * @param child The current node's given child.
         * @throws IllegalArgumentException If the given node is not a child of the current node.
         */
        public void goToChild(CallNode child) throws IllegalArgumentException {
            if(!currentCallNode.children.contains(child)) {
                throw new IllegalArgumentException("No such a child");
            }

            currentCallNode = child;
        }
    }
}
