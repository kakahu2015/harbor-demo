package org.kakahu.harbordemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Command {

    public static void exec(String message, String[] args) throws Exception {

        print(message + ":");
        Process process = Runtime.getRuntime().exec(args);
        for (String arg : args) {
            System.out.println(arg);
            System.out.print(" ");
        }
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        String line = null;
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }
        errorReader.close();
        BufferedReader infoReader = new BufferedReader(new InputStreamReader(
                process.getErrorStream()));
        while ((line = infoReader.readLine()) != null) {
            System.out.println(line);
        }
        infoReader.close();
        print("");

    }

    public static void print(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
            System.out.print(" ");
        }
    }

    public static void print(String arg) {
        System.out.println(arg);
    }



}
