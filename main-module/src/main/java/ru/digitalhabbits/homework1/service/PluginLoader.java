package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = ".jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {

        File pluginDirectory = new File(pluginDirName);
        if (!pluginDirectory.exists())pluginDirectory.mkdir();
        File[] files = pluginDirectory.listFiles((dir, name) -> name.endsWith(PLUGIN_EXT));
        ArrayList<Class<? extends PluginInterface>> plugins = new ArrayList<>();

        if(files != null && files.length > 0) {
            try {
                for (File file : files) {

                    JarFile jarFile = new JarFile(file);
                    Enumeration<JarEntry> e = jarFile.entries();

                    URL[] urls = {new URL("jar:file:" + file.toString() + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);

                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');
                        Class c = cl.loadClass(className);
                        plugins.add(c);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


            /*
            ArrayList<String> classes = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>(files.length);
            for(File file:files)
            {
                JarFile jar = null;
                try {
                    jar = new JarFile(file);
                } catch (IOException e) {
                    logger.error("Загрузка плагинов: ошибка ввода-вывода", e);
                }
                jar.stream().forEach(jarEntry -> {
                    if(jarEntry.getName().endsWith(".class"))
                    {
                        classes.add(jarEntry.getName());
                    }
                });
                URL url= null;
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    logger.error("Загрузка плагинов: не корректный URL", e);
                }
                urls.add(url);
            }

            URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));

            for (URL url: urls) {
                try {
                    Class c = urlClassLoader.loadClass(url);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            classes.forEach(className->{
                try
                {
                    Class cls = urlClassLoader.loadClass(className.replaceAll("/",".").replace(".class",""));
                    Class[] interfaces = cls.getInterfaces();
                    for(Class intface:interfaces)
                    {
                        if(intface.equals(PluginInterface.class))
                        {
                            Class<? extends PluginInterface> plugin = (Class<? extends PluginInterface>) cls.newInstance();
                            plugins.add(plugin);
                            break;
                        }
                    }
                }
                catch (Exception e){
                    logger.error("Загрузка плагинов: ошибка ClassLoader'а", e);
                }
            });

            */




        return plugins;
    }
}
