package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;

public class IllegalCommand implements CliCommand {
    /**
     * @param parameters: The command as well as additional parameters for the to be executed command
     */
    @Override
    public void execute(String[] parameters) {
        System.err.println("An invalid command was used!");
    }
}
