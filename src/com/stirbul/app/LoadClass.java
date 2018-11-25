package com.stirbul.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.util.Enumeration;

public class LoadClass extends ClassLoader {

    void init() {
        try {
            Enumeration<URL> resourses = ClassLoader.getSystemClassLoader().getResources("");
            while (resourses.hasMoreElements()) {
                URL url = resourses.nextElement(); //adress
                File file = new File(url.getPath()); // opens file
                System.out.println(file);
                traverse(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void traverse(File root) throws IOException, IllegalAccessException, InstantiationException {
        if (root.isDirectory()) {
            File[] rootfiles = root.listFiles();
            assert rootfiles != null; // expected boolean condition
            for (var r : rootfiles){
                traverse(r);
            }
        } else {
            if(root.getName().endsWith(".class")) {
                System.out.println(root.getName());
                byte[] bytes = Files.readAllBytes(root.toPath());
                Class<?> loadedClass = defineClass(bytes, 0, bytes.length);
                System.out.println(loadedClass.getName());
                System.out.println(loadedClass.isAnnotationPresent(Managed.class));
                if(!loadedClass.isAnnotation()) {
                    Object o = loadedClass.newInstance();
                    if (loadedClass.isAnnotationPresent(DefaultValue.class)) {
                        try {
                            Method method = loadedClass.getDeclaredMethod("name");
                            String methodName = (String) method.getDefaultValue();
                            loadedClass.getDeclaredField("name").set(o,methodName);
                            System.out.println("Default value is set");
                        } catch (NoSuchMethodException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                    if(loadedClass.isAnnotationPresent(Deprecated.class)) {
                        try {
                            System.out.println("New instance created");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
