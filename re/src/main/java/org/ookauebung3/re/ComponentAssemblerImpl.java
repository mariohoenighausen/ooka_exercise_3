package org.ookauebung3.re;

import org.ookauebung3.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung3.re.exceptions.ComponentInstanceNotFoundInRE;
import org.ookauebung3.re.exceptions.ComponentNotLoadedInRE;
import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for the component assembly
 *
 * @author mariohoenighausen
 * @version 1.0
 * @since 1.0
 */
public class ComponentAssemblerImpl implements ComponentAssembler {
    private final Map<String, ComponentInstanceFactory> loadedComponents;
    private final Map<Long, REComponent> componentInstances;
    private File propertiesFile;
    private Properties persitedProperties;
    private long componentInstanceCount;
    private long maxPropertyInstanceId;

    public ComponentAssemblerImpl() throws IOException, ComponentNotLoadedInRE, ComponentAlreadyLoadedInRE, NoAnnotatedMethodPresentOnComponent, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        loadedComponents = new HashMap<>();
        componentInstances = new HashMap<>();

        loadProperties();
    }
    private void loadProperties() throws FileNotFoundException, ComponentNotLoadedInRE,ComponentAlreadyLoadedInRE,ComponentAlreadyLoadedInRE,NoAnnotatedMethodPresentOnComponent,IOException,InvocationTargetException,InvocationTargetException,NoSuchMethodException,InstantiationException,IllegalAccessException {
        propertiesFile = new File("persistent.properties");
        persitedProperties = new Properties();
        if (propertiesFile.exists()) {
            try (FileReader fileReader = new FileReader(propertiesFile)) {
                persitedProperties.load(fileReader);
                loadPersistedStateFromPropertiesFile(persitedProperties);
            }
        }
    }

    private void loadPersistedStateFromPropertiesFile(Properties persitedProperties) throws IOException, NoAnnotatedMethodPresentOnComponent, ComponentAlreadyLoadedInRE, ComponentNotLoadedInRE, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Enumeration<?> propertyEnum = persitedProperties.propertyNames();
        while (propertyEnum.hasMoreElements()) {
            String key = (String) propertyEnum.nextElement();
            if (key.startsWith("PATH__")) {
                loadComponentIntoRE(persitedProperties.getProperty(key));
            }
        }
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public REComponent loadComponentIntoRE(String pathToJarFile) throws NoAnnotatedMethodPresentOnComponent, ComponentAlreadyLoadedInRE, NoAnnotatedMethodPresentOnComponent, IOException {
        URL[] urlPath = new URL[]{new File(pathToJarFile).toURI().toURL()};
        REComponent component = new REComponentImpl(urlPath);
        if (this.loadedComponents.containsKey(component.getIdNameCombinationOfComponent())) {
            throw new ComponentAlreadyLoadedInRE();
        }
        this.loadedComponents.put(component.getIdNameCombinationOfComponent(), new ComponentInstanceFactory(component));
        component.setComponentStatus(State.LOADED);

        try (FileWriter fileWriter = new FileWriter(propertiesFile)) {
            persitedProperties.setProperty("PATH__" + component.getIdNameCombinationOfComponent(), pathToJarFile);
            persitedProperties.store(fileWriter, "");
        }
        return component;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public long startComponentInstance(String name, String version) throws ComponentNotLoadedInRE, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoAnnotatedMethodPresentOnComponent, IOException {
        String componentName = getComponentName(name, version);
        ComponentRunnerImpl componentRunner = loadedComponents.get(componentName).getComponentInstance();

        componentRunner.start();
        REComponent component = loadedComponents.get(componentName).getComponent();
        component.getComponentInstances().put(componentInstanceCount, componentRunner);
        componentInstances.put(componentInstanceCount, component);
        component.setComponentStatus(State.RUNNING);
        try (FileWriter fileWriter = new FileWriter(propertiesFile)) {
            persitedProperties.setProperty(Long.toString(maxPropertyInstanceId), componentName + "--" + componentInstanceCount);
            maxPropertyInstanceId++;
            persitedProperties.store(fileWriter, "");
        }

        return componentInstanceCount++;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public void stopComponentInstance(long instanceId) throws InterruptedException, ComponentInstanceNotFoundInRE, IOException {
        if (!componentInstances.containsKey(instanceId)) {
            throw new ComponentInstanceNotFoundInRE();
        }

        ComponentRunnerImpl componentRunner = componentInstances.get(instanceId).getComponentInstances().get(instanceId);
        componentRunner.stopComponentInstance();
        for (int i = 0; !componentRunner.getState().equals(Thread.State.TERMINATED) && i < 600; i++) {
            Thread.sleep(100);
        }
        destroyComponentInstance(instanceId);
    }

    /**
     * ${@inheritDoc}
     */
    public void destroyComponentInstance(long instanceId) throws ComponentInstanceNotFoundInRE, IOException {
        if (!componentInstances.containsKey(instanceId)) {
            throw new ComponentInstanceNotFoundInRE();
        }
        Map<Long, ComponentRunnerImpl> componentInstances = this.componentInstances.get(instanceId).getComponentInstances();
        componentInstances.get(instanceId).interrupt();
        this.componentInstances.get(instanceId).setComponentStatus(State.LOADED);
        componentInstances.remove(instanceId);
        this.componentInstances.remove(instanceId);

        try (FileWriter fileWriter = new FileWriter(propertiesFile)) {
            Enumeration<?> propertiesEnum = persitedProperties.propertyNames();
            while (propertiesEnum.hasMoreElements()) {
                String key = (String) propertiesEnum.nextElement();
                String value = persitedProperties.getProperty(key);
                String valueInstanceId = value.split("-")[1];
                if (Long.parseLong(valueInstanceId) == instanceId) {
                    persitedProperties.remove(key);
                    persitedProperties.store(fileWriter, "");
                    return;
                }
            }
        }
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public REComponent unloadComponentFromRE(String name, String version) throws ComponentNotLoadedInRE {
        String componentName = getComponentName(name, version);

        REComponent component = loadedComponents.get(componentName).getComponent();
        for (Long instanceId : component.getComponentInstances().keySet()) {
            try {
                stopComponentInstance(instanceId);
                componentInstances.remove(instanceId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ComponentInstanceNotFoundInRE e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        loadedComponents.remove(component.getIdNameCombinationOfComponent());
        component.setComponentStatus(State.UNAVAILABLE);

        try (FileWriter fileWriter = new FileWriter(propertiesFile)) {
            persitedProperties.remove("PATH__" + componentName);
            persitedProperties.store(fileWriter, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return component;
    }

    public Set<REComponent> getComponentStates() {
        return this.loadedComponents.values().stream().map(ComponentInstanceFactory::getComponent).collect(Collectors.toSet());
    }

    public Map<Long, ComponentRunnerImpl> getComponentInstances(String name, String version) {
        return loadedComponents.get(name + "-" + version).getComponent().getComponentInstances();
    }

    private String getComponentName(String name, String version) throws ComponentNotLoadedInRE {
        String componentName = name + "-" + version;
        if (version == null || version.isEmpty()) {
            componentName = this.loadedComponents.keySet().stream().filter(k -> k.startsWith(name)).findFirst().orElseThrow(ComponentNotLoadedInRE::new);
        } else if (!this.loadedComponents.containsKey(componentName)) {
            throw new ComponentNotLoadedInRE();
        }
        return componentName;
    }
}
