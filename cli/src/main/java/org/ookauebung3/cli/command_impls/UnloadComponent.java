package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.REComponent;
import org.ookauebung3.re.exceptions.ComponentNotLoadedInRE;

/**
 * A command implementation for unloading a component which was previously loaded from the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class UnloadComponent implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the UnloadComponent command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public UnloadComponent(ComponentAssembler componentAssembler){
        this.componentAssembler = componentAssembler;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        String version = null;
        try {
            if (parameters.length < 1) {
                System.out.println("Command signature mismatch");
                System.out.println("The expected signature for the command is: unloadComponent <component-name> [<component-version>]");
                return;
            }
            if (parameters.length > 1) version = parameters[1];
            REComponent component = componentAssembler.unloadComponentFromRE(parameters[0], version);
            System.out.println("REComponent " + component.getComponentName() + " version " + component.getComponentVersion() + " was removed");
        }
        catch (ComponentNotLoadedInRE e) {
            System.err.println("The component couldn't be found in the runtime environment!");
        }
    }
}
