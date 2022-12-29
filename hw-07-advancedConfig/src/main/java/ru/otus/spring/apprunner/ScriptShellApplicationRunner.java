package ru.otus.spring.apprunner;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.shell.Shell;
import org.springframework.shell.context.ShellContext;
import org.springframework.shell.jline.InteractiveShellRunner;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;
import ru.otus.spring.processors.MessageProcessor;

@Component
public class ScriptShellApplicationRunner extends InteractiveShellRunner {

    private final MessageProcessor messageProcessor;

    @Autowired
    public ScriptShellApplicationRunner(LineReader lineReader, PromptProvider promptProvider, Shell shell, ShellContext shellContext, MessageProcessor messageProcessor) {
        super(lineReader, promptProvider, shell, shellContext);
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println();
        messageProcessor.showLocaleMessage("shell.greeting", null);
        super.run(args);
    }
}
