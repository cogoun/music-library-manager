package com.cogoun;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static List<String> notConvertedFiles = new ArrayList<>();
    public static Long numberOfConvertedFiles = 0L;

    /**
     * Usage: outputFormat convertLossy sourceDir targetDir
     * @param args
     */
    public static void main(String... args) {

        String outputFormat = args[0];
        boolean convertLossy = Boolean.valueOf(args[1]);
        File sourceDirectory = new File(args[3]);
        File targetDirectory = new File(args[4]);

        LibraryConverter libraryConverter = new LibraryConverter();
        libraryConverter.convert(sourceDirectory, targetDirectory);
    }
}
