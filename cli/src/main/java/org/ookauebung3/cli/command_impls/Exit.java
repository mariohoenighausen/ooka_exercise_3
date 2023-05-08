package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;

/**
 * A command implementation for exiting the cli
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class Exit implements CliCommand {
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        System.out.println("The Runtime environment will be terminated!");
    }
}
