package ru.otus.infrastructure;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleIOService implements IOService {

    private final PrintStream output;
    private final Scanner input;

    public ConsoleIOService(PrintStream output, InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public void printLine(String line) {
        output.println(line);
    }

    @Override
    public String readLine() {
        return input.next();
    }
}
