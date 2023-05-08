package org.ookauebung3.bicycle_store_component;

import org.ookauebung3.component_modell.Injectable;
import org.ookauebung3.component_modell.Logging;
import org.ookauebung3.component_modell.Startable;
import org.ookauebung3.component_modell.Stoppable;

/**
 * A Simple Component that represents a bicycle repair shop
 * @implNote WARNING currently no advanced functionality is available
 * @since 1.0
 * @version  1.0
 * @author mariohoenighausen
 */
public class BicycleWorkShop {
    @Injectable
    private Logging logger;
    @Startable
    public void openShop(){
        this.logger.sendLog("Component was started");
    }
    @Stoppable
    public void closeShop(){
        this.logger.sendLog("Component was stopped");
    }

}
