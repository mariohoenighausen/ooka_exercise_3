package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.REComponent;
import org.ookauebung3.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.IOException;

/**
 * A class for loading Components into the runtime environment
 * @version 1.0
 * @author mariohoenighausen
 */
public class LoadComponent implements CliCommand {

    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the LoadComponent
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public LoadComponent(ComponentAssembler componentAssembler){
        this.componentAssembler = componentAssembler;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        try{
            if(parameters.length < 1){
                System.out.println("Command signature mismatch!");
                System.out.println("The expected signature for the command is: loadComponent <jar-file-path>");
                return;
            }
            REComponent component = this.componentAssembler.loadComponentIntoRE(parameters[0]);
            System.out.println("The Component: " + component.getComponentName() + " in version: " + component.getComponentVersion() + ", was loaded into the runtime environment");
        }
        catch (ComponentAlreadyLoadedInRE e) {
            throw new RuntimeException(e);
        }
        catch( NoAnnotatedMethodPresentOnComponent e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
