package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.REComponent;
import org.ookauebung3.re.State;

public class ListComponentInstances implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the DestroyComponentInstance command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public ListComponentInstances(ComponentAssembler componentAssembler) {
        this.componentAssembler = componentAssembler;
    }
    /**
     * @param parameters: The command as well as additional parameters for the to be executed command
     */
    @Override
    public void execute(String[] parameters) {
        for(REComponent reComponent : this.componentAssembler.getComponentStates()){
            if(reComponent.getComponentStatus().equals(State.RUNNING)){
                System.out.println("The component: " + reComponent.getComponentName() + " " + reComponent.getComponentVersion() + "is loaded and the following instances are present:");
                for (Long key : reComponent.getComponentInstances().keySet()){
                    System.out.println("\t"+ key + " " + reComponent.getComponentInstances().get(key));
                }
            }
            else{
                System.out.println("No component instances present!");
                System.out.println("Run the: startComponentInstance <component-name> [<component-version>], to start a component instance");
            }
        }
    }
}
