package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;

/**
 * A command implementation for getting information about the available cli commands
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class Help implements CliCommand {
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Commands:");
        System.out.println("help");
        System.out.println("loadComponent <jar-file-path>");
        System.out.println("unloadComponent <component-name> [<component-version>]");
        System.out.println("listComponents");
        System.out.println("startComponentInstance <component-name> [<component-version>]");
        System.out.println("stopComponentInstance <component-name> [<component-version>]");
        System.out.println("destroyComponentInstance <componentInstance-id>");
        System.out.println("listComponentInstances");
        System.out.println("exit");
        System.out.println("----------------------------------------------------------------");
    }
}
