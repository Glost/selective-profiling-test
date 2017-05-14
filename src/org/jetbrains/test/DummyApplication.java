/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Nikolay.Tropin
 * 18-Apr-17
 */
public class DummyApplication {

    /**
     * The map which matches the DummyApplication instances and the trees of calls.
     */
    private static Map<DummyApplication, CallTree> callTreeMap = new HashMap<>();

    private final List<String> args;

    private Random random = new Random(System.nanoTime());

    public DummyApplication(List<String> args) {
        this.args = args;
    }

    /**
     * Returns the map which matches the DummyApplication instances and the trees of calls.
     * @return The map which matches the DummyApplication instances and the trees of calls.
     */
    public static Map<DummyApplication, CallTree> getCallTreeMap() {
        return callTreeMap;
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }

    private boolean stop() {
        return random.nextDouble() < 0.05;
    }

    private String nextArg() {
        int idx = random.nextInt(args.size());
        return args.get(idx);
    }

    private void sleep() {
        try {
            Thread.sleep(random.nextInt(20));
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * Handles the method's call at the start of its running.
     * Saves necessary information to the tree of calls.
     * @param method The called method's name.
     * @param arg The argument of the method's call.
     */
    private void preHandleCall(String method, Object arg) {
        CallTree callTree = callTreeMap.get(this);
        if(callTree == null) {
            callTreeMap.put(this, new CallTree(method, arg.toString()));
            return;
        }

        CallTree.Iterator iterator = callTree.getIterator();
        CallTree.CallNode currentCallNode = iterator.getCurrentCallNode();
        CallTree.CallNode newChild = currentCallNode.addChild(method, arg.toString());
        iterator.goToChild(newChild);
    }

    /**
     * Handles the method's call at the end of its running.
     * Returns the tree's iterator to the parent node.
     */
    private void postHandleCall() {
        CallTree callTree = callTreeMap.get(this);
        CallTree.Iterator iterator = callTree.getIterator();
        iterator.goToParent();
    }

    private void abc(String s) {
        preHandleCall("abc", s);

        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            def(nextArg());
        }
        else {
            xyz(nextArg());
        }

        postHandleCall();
    }

    private void def(String s) {
        preHandleCall("def", s);

        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            abc(nextArg());
        }
        else {
            xyz(nextArg());
        }

        postHandleCall();
    }

    private void xyz(String s) {
        preHandleCall("xyz", s);

        sleep();
        if (stop()) {
            //do nothing
        }
        else if (nextBoolean()) {
            abc(nextArg());
        }
        else {
            def(nextArg());
        }

        postHandleCall();
    }

    public void start() {
        abc(nextArg());
    }
}
