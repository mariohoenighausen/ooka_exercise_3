package org.ookauebung3.re;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An interface for the component loader
 * @version 1.0
 * @author mariohoenighausen
 */
public interface ComponentLoader {
    /**
     * Returns the name of the loaded component
     * @return component name
     */
    String getComponentName();

    /**
     * Returns the version of the loaded component
     * @return component version
     */
    String getComponentVersion();

    /*
     * @return component meta info
     */
    //LzuComponentMetaInfo getComponentMetaInfo();

    /**
     * Returns the start Method of a to be started component
     * @return start method
     */
    Method getStartMethod();

    /**
     * Returns the stop Method of a to be stoped component
     * @return stop method
     */
    Method getStopMethod();

    /**
     * Returns the starting class of a component
     * @return Class
     */
    Class<?> getStartClass();

    /**
     * Returns the logger field of a component
     * @return Field
     */
    Field getLogger();
}
