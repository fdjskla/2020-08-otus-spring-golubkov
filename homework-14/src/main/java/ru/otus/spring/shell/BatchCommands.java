package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.config.AppProps;


@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final AppProps appProps;

    private final JobOperator jobOperator;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        Long executionId = jobOperator.start(IMPORT_USER_JOB_NAME,
                INPUT_FILE_NAME + "=" + appProps.getInputFile() + "\n" +
                        OUTPUT_FILE_NAME + "=" + appProps.getOutputFile()
        );
        System.out.println(jobOperator.getSummary(executionId));
    }

}
