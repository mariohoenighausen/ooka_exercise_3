package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.exceptions.ComponentNotLoadedInRE;
import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


/**
 * A command implementation for starting a component instance for a component which was already loaded into the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class StartComponentInstance implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the StartComponentInstance command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public StartComponentInstance(ComponentAssembler componentAssembler) {
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
                System.out.println("The expected signature for the command is: startComponentInstance <component-name> [<component-version>]");
                return;
            }
            if (parameters.length > 1) version = parameters[1];
            long instanceId = componentAssembler.startComponentInstance(parameters[0], version);
            System.out.println("An instance of the component was started with ID " + instanceId);
        } catch (ComponentNotLoadedInRE e) {
            System.err.println("The component with the name and or version " + parameters[0] + " " + version + "couldn't be found!");
        }
        catch (InvocationTargetException | NoSuchMethodException | InstantiationException | RuntimeException e) {
            System.err.println("The component couldn't be instantiated!");
        } catch (IllegalAccessException e){
            System.err.println("The logger for the component instance couldn't be injected!");
        } catch (NoAnnotatedMethodPresentOnComponent | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
