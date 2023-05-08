package org.ookauebung3.hotelsuchen_component;


import org.ookauebung3.hotelsuchen_component.ports.provided.AdvancedHotelSuchenImpl;
import org.ookauebung3.hotelsuchen_component.ports.provided.HotelSuchenImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvidedInterfaceFacade {
    private final Map<String, Object> providedInterfaces;

    public ProvidedInterfaceFacade(){
        providedInterfaces = new HashMap<>();
        providedInterfaces.put("searchByName", new HotelSuchenImpl());
        providedInterfaces.put("advancedSearch", AdvancedHotelSuchenImpl.getInstance());
    }

//    public static void main(String[] args) {
//        try{
//            String jarFilePath = "codesOOKA-1.0-SNAPSHOT-jar-with-dependencies.jar";
//            URL[] urls = { new URL("jar:file:" + jarFilePath + "!/") };
//            try(URLClassLoader classLoader = URLClassLoader.newInstance(urls)){
//                Class<?> hotelSuchenImplClass = classLoader.loadClass("org.bonn.ooka.buchungssystem.ss2022.ports.provided.HotelSuchenImpl");// Object obj = hotelRetrievalClass.getDeclaredConstructor().newInstance(); //public Constructor
//                Method hSICGI = hotelSuchenImplClass.getDeclaredMethod("getInstance"); // private Constructor with getInstance Method of a Singleton
//                Object hSII = hSICGI.invoke(null); // invoke a static method
//                Method hSIIOpenSession = hSII.getClass().getMethod("openSession");
//                System.out.println(Arrays.toString(hSIIOpenSession.getAnnotations()));
//                Method hSIIGetHotelByName = hSII.getClass().getDeclaredMethod("getHotelByName",String.class);
//                Method hSIICloseSession = hSII.getClass().getMethod("closeSession");
//                hSIIOpenSession.invoke(hSII);
//                Object[] hotels1 = (Object[]) hSIIGetHotelByName.invoke(hSII,"H");
//                for(Object h : hotels1){
//                    System.out.println(h);
//                }
//                hSIICloseSession.invoke(hSII);
//                Method method = HotelSuchenImpl.class.getDeclaredMethod("openSession");
//                System.out.println(Arrays.toString(method.getAnnotations()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public List<String> getProvidedInterfaces(){
        return new ArrayList<>(providedInterfaces.keySet());
    }

    public Object getProvidedInterfaceByName(String name){
        return providedInterfaces.get(name);
    }
}
