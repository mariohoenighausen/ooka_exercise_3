package org.ookauebung3.re;

import java.lang.reflect.InvocationTargetException;

/**
 * A class for running components inside the runtime environment
 * @version 1.0
 * @author mariohoenighausen
 */
public class ComponentRunnerImpl extends Thread{
    /**
     * A reference to an instance of a component loaded into the runtime environment
     * @since 1.0
     */
    private final Object componentInstance;
    /**
     * A reference to a ComponentLoader
     */
    private final ComponentLoader componentLoader;
    /**
     * Instantiates a ComponentRunnerImpl instance
     * @param componentLoader A Reference to a componentLoader instance.
     * @param componentInstance A reference to the componentInstance object
     */
    public ComponentRunnerImpl(ComponentLoader componentLoader, Object componentInstance){
        this.componentLoader = componentLoader;
        this.componentInstance = componentInstance;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void run(){
        try{
            this.componentLoader
                    .getStartMethod()
                    .invoke(componentInstance);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    /**
     * ${@inheritDoc}
     */
    public void stopComponentInstance() {
        try{
            this.componentLoader
                    .getStopMethod()
                    .invoke(componentInstance);
        }
        catch (IllegalAccessException | InvocationTargetException exception){
            throw new RuntimeException(exception);
        }
    }
}
