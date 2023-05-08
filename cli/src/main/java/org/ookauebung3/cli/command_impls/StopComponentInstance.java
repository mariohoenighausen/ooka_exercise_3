package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.exceptions.ComponentInstanceNotFoundInRE;

import java.io.IOException;

/**
 * A command implementation for stopping a component instance for a component which was already loaded into the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class StopComponentInstance implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;
    /**
     * Constructor for creating an instance of the StopComponentInstance command
     * @param assembler A reference to a ComponentAssembler
     */
    public StopComponentInstance(ComponentAssembler assembler) {
        this.componentAssembler = assembler;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        try {
            if (parameters.length < 1) {
                System.out.println("Command signature mismatch");
                System.out.println("The expected signature for the command is: stopInstance <instance-id>");
                return;
            }
            componentAssembler.stopComponentInstance(Long.parseLong(parameters[0]));
            System.out.println("Stopping instance with ID " + parameters[0]);
        } catch (ComponentInstanceNotFoundInRE e) {
            System.err.println("Can't find an instance with the ID " + parameters[0]);
        } catch (InterruptedException | IOException e){
            throw new RuntimeException(e);
        }
    }
}
