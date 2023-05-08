package org.ookauebung3.cli.command_impls;

import org.ookauebung3.cli.CliCommand;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.REComponent;
import org.ookauebung3.re.State;

public class ListLoadedComponents implements CliCommand {

    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the DestroyComponentInstance command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public ListLoadedComponents(ComponentAssembler componentAssembler) {
        this.componentAssembler = componentAssembler;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] parameters) {
        if(componentAssembler.getComponentStates().isEmpty()){
            System.out.println("There are no components present in the runtime environment!");
        }
        else{
            for(REComponent reComponent : this.componentAssembler.getComponentStates()){
                if(reComponent.getComponentStatus() == State.LOADED){
                    System.out.println(reComponent.getComponentName() + " " + reComponent.getComponentVersion());
                }
            }
        }
    }
}
