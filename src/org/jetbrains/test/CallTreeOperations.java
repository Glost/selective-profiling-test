/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Represents the static methods for the working with the trees of calls: trees' printing to the console,
 * writing to the file and reading from the written file.
 */
public class CallTreeOperations {

    /**
     * Prints the given tree of calls to the console.
     * @param callTree The given tree of calls.
     */
    public static void printCallTree(CallTree callTree) {
        printCallNode(callTree.getRoot());
    }

    /**
     * Prints the given tree's node to the console.
     * @param callNode The given tree's node.
     */
    private static void printCallNode(CallTree.CallNode callNode) {
        System.out.print(callNode + " -> ");

        List<CallTree.CallNode> children = callNode.getChildren();
        if(children.size() == 0) {
            System.out.println("X;");
            return;
        }

        for(CallTree.CallNode child : children) {
            System.out.print(child + ";");
        }
        System.out.println();

        for(CallTree.CallNode child : children) {
            printCallNode(child);
        }
    }

    /**
     * Saves the given tree of calls to the file.
     * @param callTree The given tree of calls.
     * @param dataOutputStream The DataOutputStream instance.
     * @throws IOException If some problem during writing to the file happened.
     */
    public static void saveCallTreeToFile(CallTree callTree, DataOutputStream dataOutputStream) throws IOException {
        saveCallNodeToFile(callTree.getRoot(), dataOutputStream);
    }

    /**
     * Saves the given tree's node to the file.
     * @param callNode The given tree's node.
     * @param dataOutputStream The DataOutputStream instance.
     * @throws IOException If some problem during writing to the file happened.
     */
    private static void saveCallNodeToFile(CallTree.CallNode callNode, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(callNode.getMethod());
        dataOutputStream.writeUTF(callNode.getArg());

        List<CallTree.CallNode> children = callNode.getChildren();
        dataOutputStream.writeInt(children.size());

        for(CallTree.CallNode child : children) {
            saveCallNodeToFile(child, dataOutputStream);
        }
    }

    /**
     * Reads the tree of calls from the file.
     * @param dataInputStream The DataInputStream instance.
     * @return The tree of calls which was read from the file.
     * @throws IOException If some problem during reading from the file happened.
     */
    public static CallTree readCallTreeFromFile(DataInputStream dataInputStream) throws IOException {
        return readCallNodeFromFile(dataInputStream, null).getCallTree();
    }

    /**
     * Reads the tree's node from the file.
     * @param dataInputStream The DataInputStream instance.
     * @param parent The parent of the node which will be read. If it is root, the parent equals null.
     * @return The tree's node which was read from the file.
     * @throws IOException If some problem during reading from the file happened.
     */
    public static CallTree.CallNode readCallNodeFromFile(DataInputStream dataInputStream, CallTree.CallNode parent) throws IOException {
        String method = dataInputStream.readUTF();
        String arg = dataInputStream.readUTF();

        CallTree.CallNode callNode = null;
        if(parent == null) {
            CallTree callTree = new CallTree(method, arg);
            callNode = callTree.getRoot();
        } else {
            parent.addChild(method, arg);
            callNode = parent.getChildren().get(parent.getChildren().size() - 1);
        }

        int childrenAmount = dataInputStream.readInt();
        for(int i = 0; i < childrenAmount; i++) {
            readCallNodeFromFile(dataInputStream, callNode);
        }

        return callNode; // Necessary for readCallTreeFromFile() method.
    }
}
