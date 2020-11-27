package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        Map<String, Integer> dictionary = new TreeMap<>();

        Matcher matcher = Pattern.compile("\\b[a-zA-Z][a-zA-Z.0-9]*\\b").matcher(text.toLowerCase());
        while (matcher.find()) {
            String match = matcher.group();
            if (dictionary.containsKey(match)) dictionary.put(match, dictionary.get(match) + 1);
            else dictionary.put(match, 1);
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry: dictionary.entrySet()){
            builder.append(entry.getKey() + " " + entry.getValue() + "\n");
        }

        return builder.toString();
    }
}
