package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        Matcher matcher = Pattern.compile("\\n").matcher(text);
        long countLines = matcher.results().count();
        if (!text.endsWith("\n")) countLines++;

        matcher = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b").matcher(text);
        long countWords = matcher.results().count();

        matcher = Pattern.compile("[\\w\\s^[\\.,()!?]]").matcher(text);
        long countLetters = matcher.results().count();

        return String.format("%d;%d;%d", countLines, countWords, countLetters);
    }
}
