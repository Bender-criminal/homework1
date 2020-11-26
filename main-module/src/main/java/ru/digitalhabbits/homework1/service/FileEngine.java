package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = ".txt";
    private static int fileNum = 1;

    private static final Logger logger = getLogger(FileEngine.class);

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);

        if (!resultDir.exists()) {
            try {
                resultDir.createNewFile();
            } catch (IOException e) {
                logger.error("Не удалось создать директорию результатов", e);
                return false;
            }
        }

        try (FileWriter fw = new FileWriter(String.format(currentDir + "/" + RESULT_DIR + "/" + RESULT_FILE_PATTERN, fileNum))){
            fw.write(pluginName + ": " + text + "\n");
            fileNum++;

        } catch (IOException e) {
            logger.error("Не удалось записать в файл-результатов", e);
            return  false;
        }

        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        if (!resultDir.exists()) return;
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
