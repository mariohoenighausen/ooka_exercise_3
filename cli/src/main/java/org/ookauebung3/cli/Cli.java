package org.ookauebung3.cli;

import org.ookauebung3.cli.command_impls.*;
import org.ookauebung3.re.ComponentAssembler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class for the cli
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class Cli {
    /**
     * A reference for a component Assembler
     * @since 1.0
     */
    private final ComponentAssembler assembler;
    /**
     * A reference to a Scanner
     */
    private final Scanner scanner;
    /**
     * A variable to keep track of whether the loop for the cli is still valid
     */
    public boolean isRERunning;

    /**
     * Instantiates a new CLI with a componentAssembler reference
     * @param assembler: A ComponentAssembler
     */
    public Cli(ComponentAssembler assembler) throws IOException {
        this.assembler = assembler;
        scanner = new Scanner(System.in);
        isRERunning = true;

        getCommandByName("help").execute(new String[0]);
        while(isRERunning){
            readCommand();
        }
    }

    /**
     * A method to read a command from console
     * @throws IOException An IOException
     */
    private void readCommand(){
        String input = scanner.nextLine();
        String[] seperatedInput = input.split(" ");
        String[] params = Arrays.copyOfRange(seperatedInput,1,seperatedInput.length);
        CliCommand command = getCommandByName(seperatedInput[0]);
        if(command != null){
            command.execute(params);
        }
    }

    /**
     * A method to get an instance of a command by the name of the command
     * @param commandName The name of the command
     * @return A CliCommand
     */
    private CliCommand getCommandByName(String commandName) {
        return switch(commandName){
            case "help" -> new Help();
            case "exit" -> {this.isRERunning = false;yield new Exit();}
            case "loadComponent" -> new LoadComponent(assembler);
            case "unloadComponent" -> new UnloadComponent(assembler);
            case "startComponentInstance" -> new StartComponentInstance(assembler);
            case "stopComponentInstance" -> new StopComponentInstance(assembler);
            case "destroyComponentInstance" -> new DestroyComponentInstance(assembler);
            case "listLoadedComponents" -> new ListLoadedComponents(assembler);
            case "listComponentInstances" -> new ListComponentInstances(assembler);
            default -> new IllegalCommand();
        };
    }
}
