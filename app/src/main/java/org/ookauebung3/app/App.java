package org.ookauebung3.app;

import org.ookauebung3.cli.Cli;
import org.ookauebung3.component_modell.Injectable;
import org.ookauebung3.re.ComponentAssembler;
import org.ookauebung3.re.ComponentAssemblerImpl;
import org.ookauebung3.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung3.re.exceptions.ComponentNotLoadedInRE;
import org.ookauebung3.re.exceptions.NoAnnotatedMethodPresentOnComponent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class App {

        public static void dump(){
            String jarFilePath2 = "bicycle_store_component/target/bicycle_store_component-1.0-SNAPSHOT.jar";
        String jarFilePath = "hotelsuchen_component/target/hotelsuchen_component-1.0-SNAPSHOT.jar";
            try{
                URL[] urls = { new URL("jar:file:" + jarFilePath + "!/") };
                Enumeration<JarEntry> jarEntries = new JarFile(jarFilePath).entries();
                while(jarEntries.hasMoreElements()){
                    System.out.println(jarEntries.nextElement());
                }
                URLClassLoader classLoader = URLClassLoader.newInstance(urls);
                String classToLoad = "org.ookauebung3.hotelsuchen_component.ports.provided.HotelSuchenImpl";
                String classToLoad2 = "org.ookauebung3.bicycle_store_component.BicycleWorkShop";
                Class<?> hotelSuchenImplClass = classLoader.loadClass(classToLoad);
                Object hSII = hotelSuchenImplClass.getDeclaredConstructor().newInstance(); // private Constructor with getInstance Method of a Singleton
                Method hSIIOpenSession = hSII.getClass().getMethod("openSession");
                System.out.println(Arrays.toString(hSIIOpenSession.getAnnotations()));
                Method hSIIGetHotelByName = hSII.getClass().getDeclaredMethod("getHotelByName",String.class);
                Method hSIICloseSession = hSII.getClass().getMethod("closeSession");
                Field f = Arrays.stream(hSII.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Injectable.class)).findFirst().orElseThrow(Exception::new);
                f.setAccessible(true);
                f.set(hSII, new Logger());
                f.setAccessible(false);
                hSIIOpenSession.invoke(hSII);
                Object[] hotels1 = (Object[]) hSIIGetHotelByName.invoke(hSII,"H");
                for(Object h : hotels1){
                    System.out.println(h);
                }
                hSIICloseSession.invoke(hSII);
            }
            catch (MalformedURLException | ClassNotFoundException malformedURLException){
                malformedURLException.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    public static void main(String[] args) {
        // loadComponent hotelsuchen_component/target/hotelsuchen_component-1.0-SNAPSHOT.jar
        // listLoadedComponents
        // startComponentInstance TestComponent 0.0.1
        // listComponentInstances
        // stopComponentInstance 0
        // unloadComponent TestComponent 0.0.1
        // listLoadedComponents

        // loadComponent bicycle_store_component/target/bicycle_store_component-1.0-SNAPSHOT.jar
        // listLoadedComponents
        // startComponentInstance BicycleComponent 0.0.1
        // stopComponentInstance 0
        try{
            ComponentAssembler componentAssembler = new ComponentAssemblerImpl();
            new Cli(componentAssembler);
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        } catch (ComponentNotLoadedInRE e) {
            throw new RuntimeException(e);
        } catch (ComponentAlreadyLoadedInRE e) {
            throw new RuntimeException(e);
        } catch (NoAnnotatedMethodPresentOnComponent e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //dump();
    }
}
