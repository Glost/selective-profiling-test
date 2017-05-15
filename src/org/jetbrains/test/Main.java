/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
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

        ConcurrentMap<Thread, Integer> threadsTasksAmountsMap = DummyApplication.getThreadsTasksAmountsMap();
        ConcurrentMap<DummyApplication, CallTree> callTreeMap = DummyApplication.getCallTreeMap();
        int thread = 0;
        for(ConcurrentMap.Entry<Thread, Integer> threadsEntry : threadsTasksAmountsMap.entrySet()) {
            int task = 0;
            for(ConcurrentMap.Entry<DummyApplication, CallTree> entry : callTreeMap.entrySet()) {
                if(entry.getValue().getThread() == threadsEntry.getKey()) {
                    System.out.println("Task " + (task++) + " in thread " + thread + ":");
                    CallTreeOperations.printCallTree(entry.getValue());
                }
            }
            thread++;
        }
    }
}
