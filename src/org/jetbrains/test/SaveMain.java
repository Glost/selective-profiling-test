/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The program for saving the trees of calls to the file.
 */
public class SaveMain {

    private final static int N_THREADS = 3;

    private final static int N_TASKS = 5;

    public static void main(String[] args) {
        if(args.length != 1 || args[0] == null) {
            System.out.println("Please, pass the correct file name in an argument. There should be only one argument.");
            return;
        }
        String fileName = args[0];

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

        File file = new File(fileName);
        try (DataOutputStream dataOutputStream = new DataOutputStream(Files.newOutputStream(file.toPath()))) {
            Map<DummyApplication, CallTree> callTreeMap = DummyApplication.getCallTreeMap();
            dataOutputStream.writeInt(N_TASKS);
            int task = 0;
            for(Map.Entry<DummyApplication, CallTree> entry : callTreeMap.entrySet()) {
                CallTreeOperations.saveCallTreeToFile(entry.getValue(), dataOutputStream);
                System.out.println("Task " + (task++) + ":");
                CallTreeOperations.printCallTree(entry.getValue());
            }
            System.out.println("File has been successfully written.");;
        } catch (IOException e) {
            System.out.println("Sorry, there is some problem with writing the file: " + e.getMessage());
        }
    }
}
