package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginEngine {

    private static final Logger logger = getLogger(PluginEngine.class);

    @Nonnull
    public  <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
            String result = null;
        try {
            result = (String) cls.getMethod("apply", String.class).invoke(text);
        } catch (Exception e) {
            logger.error("Плагин не выполнился", e);
        }
        return result;
    }
}
