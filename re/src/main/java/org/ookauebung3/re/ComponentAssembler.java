package org.ookauebung3.re;

import org.ookauebung3.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung3.re.exceptions.ComponentInstanceNotFoundInRE;
import org.ookauebung3.re.exceptions.ComponentNotLoadedInRE;
import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

/**
 * An interface for the component assembler
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public interface ComponentAssembler {

    /**
     * Loads a component into the runtime environment
     * @param pathToJarFile: A path to a jarFile that contains a component
     * @return REComponent
     */
    REComponent loadComponentIntoRE(String pathToJarFile) throws ComponentAlreadyLoadedInRE, NoAnnotatedMethodPresentOnComponent, IOException;

    /**
     * Unloads a component from the runtime environment
     * @param name: Name of the component
     * @param version Version of the component
     * @return REComponent
     * @throws Exception A exception
     */
    REComponent unloadComponentFromRE(String name, String version) throws ComponentNotLoadedInRE;

    /**
     * Starts a component instance
     * @param name:
     * @param version:
     * @return Long
     * @throws ComponentNotLoadedInRE An exception thrown when the to be started component isn't loaded in the runtime environment
     * @throws InvocationTargetException An exception thrown when no invocation of the starter method of the component is possible
     * @throws NoSuchMethodException An exception thrown when no start method could be extracted
     * @throws InstantiationException An exception thrown when no instantiation of the starter class is possible
     * @throws IllegalAccessException An exception thrown when the access to an attribute or class is not valid
     */
    long startComponentInstance(String name, String version) throws ComponentNotLoadedInRE, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoAnnotatedMethodPresentOnComponent, IOException;

    /**
     * A method that stops a component instance
     * @param instanceId The id of the component instance to be started
     * @throws InterruptedException An exception that is thrown when the thread of the component instance is interrupted
     * @throws ComponentInstanceNotFoundInRE An exception that is thrown when the component instance is not found in the runtime environment
     * @throws IOException An IOException
     */
    void stopComponentInstance(long instanceId) throws InterruptedException, ComponentInstanceNotFoundInRE, IOException;

    /**
     * A method that destroys a component instance e.g. ungracefully stops it
     * @param instanceId The id of the component instance to be destroyed
     * @throws ComponentInstanceNotFoundInRE An exception that is thrown when the component instance is not found in the runtime environment
     * @throws IOException An IOException
     */
    void destroyComponentInstance(long instanceId) throws ComponentInstanceNotFoundInRE, IOException;
    Set<REComponent> getComponentStates();
    Map<Long, ComponentRunnerImpl> getComponentInstances(String name, String version);
}
