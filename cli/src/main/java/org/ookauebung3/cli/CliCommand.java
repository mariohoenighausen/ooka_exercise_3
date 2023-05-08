package org.ookauebung3.cli;

/**
 * A interface for a cli command
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public interface CliCommand {
    /**
     * A method for executing a command
     * @author mariohoenighausen
     * @since 1.0
     * @param parameters: The command as well as additional parameters for the to be executed command
     */
    void execute(String[] parameters);
}
