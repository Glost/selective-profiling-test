/**
 * The task is done by Anton Rigin in 2017.
 */

package org.jetbrains.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * The program for reading the trees of calls from the file.
 */
public class ReadMain {

    public static void main(String[] args) {
        if(args.length != 1 || args[0] == null) {
            System.out.println("Please pass the correct file name in an argument. There should be only one argument.");
            return;
        }
        String fileName = args[0];

        File file = new File(fileName);
        try (DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(file.toPath()))) {
            int tasks = dataInputStream.readInt();
            for(int task = 0; task < tasks; task++) {
                CallTree callTree = CallTreeOperations.readCallTreeFromFile(dataInputStream);
                System.out.println("Task " + task + ":");
                CallTreeOperations.printCallTree(callTree);
            }
        } catch (IOException e) {
            System.out.println("Sorry, there is some problem with reading the file: " + e.getMessage() +
                    "\nMay be, file has incorrect format.");
        }
    }
}
