/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The program only for printing the result to the console.
 */
public class Main {

    private final static int N_THREADS = 3;

    private final static int N_TASKS = 5;

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(N_THREADS);
        for(int i = 0; i < N_TASKS; i++) {
            int start = 100 * i;
            List<String> arguments = IntStream.range(start, start + 10)
                    .mapToObj(Integer :: toString)
                    .collect(Collectors.toList());
            service.submit(() -> new DummyApplication(arguments).start());
        }
        service.shutdown();
        try {
            while(!service.awaitTermination(1, TimeUnit.SECONDS));
        } catch (InterruptedException ignored) {

        }

        Map<DummyApplication, CallTree> callTreeMap = DummyApplication.getCallTreeMap();
        int task = 0;
        for(Map.Entry<DummyApplication, CallTree> entry : callTreeMap.entrySet()) {
            System.out.println("Task " + (task++) + ":");
            CallTreeOperations.printCallTree(entry.getValue());
        }
    }
}
