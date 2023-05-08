package org.ookauebung3.re;

import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.IOException;
import java.util.Map;

/**
 * An interface for a component wrapper
 * @version 1.0
 * @author mariohoenighausen
 */
public interface REComponent {
    //ComponentMetaInfo getMetaInfo();
    /**
     * Returns a unique Id-Name-combination for the component
     * @return String
     */
    String getIdNameCombinationOfComponent();

    /**
     * A method that returns the name of the component
     * @return The name of the component
     */
    String getComponentName();
    /**
     * A method that returns the version of the component
     * @return The version of the component
     */
    String getComponentVersion();


    ComponentLoader getComponentLoader() throws NoAnnotatedMethodPresentOnComponent, IOException;
    /**
     * Returns a component instance of a component that was loaded into the runtime environment
     * @since 1.0
     * @return Map of Long as key and ComponentRunnerImpl as value
     */
    Map<Long, ComponentRunnerImpl> getComponentInstances();
    /**
     * Returns the URLPath for the JarFile of a component
     * @return string
     */
    String getURLPath();
    public State getComponentStatus();

    public void setComponentStatus(State componentStatus);
}
