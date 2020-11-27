package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        long countLines = IntStream.iterate(
                text.indexOf("\n"),
                i -> i != -1,
                i -> text.indexOf("\n", i + 1))
                .count();
        if (!text.endsWith("\n")) countLines++;

        Matcher matcher = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b").matcher(text);
        long countWords = 0;
        while(matcher.find()){
            countWords++;
        }

        long countLetters = 0;
        matcher = Pattern.compile("[\\w\\s^[\\.,()!?]]").matcher(text);
        while(matcher.find()){
            countLetters++;
        }

        return String.format("%d;%d;%d", countLines, countWords, countLetters);
    }
}
