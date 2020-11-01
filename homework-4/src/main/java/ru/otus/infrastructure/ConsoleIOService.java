package ru.otus.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Component
public class ConsoleIOService implements IOService {

    private final PrintStream output;
    private final Scanner input;

    public ConsoleIOService(
            @Value("#{ T(java.lang.System).out}") PrintStream out,
            @Value("#{ T(java.lang.System).in}") InputStream in
    ) {
        this.output = out;
        this.input = new Scanner(in);
    }

    @Override
    public void printLine(String line) {
        output.println(line);
    }

    @Override
    public String readLine() {
        return input.nextLine();
    }
}
