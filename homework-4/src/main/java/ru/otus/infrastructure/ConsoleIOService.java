package ru.otus.infrastructure;

import lombok.AllArgsConstructor;

import java.io.PrintStream;
import java.util.Scanner;

@AllArgsConstructor
public class ConsoleIOService implements IOService {

    private final PrintStream output;
    private final Scanner input;

    @Override
    public void printLine(String line) {
        output.println(line);
    }

    @Override
    public String readLine() {
        return input.nextLine();
    }
}
