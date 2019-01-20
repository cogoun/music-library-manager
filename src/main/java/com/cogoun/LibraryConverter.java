package com.cogoun;

import java.io.File;

public class LibraryConverter implements AudioFileConverter {

    public void convert(File sourceDirectory, File targetDirectory) {
        DirectoryConvert directoryConvert = new DirectoryConvert();
        if (sourceDirectory.isDirectory()) {
            try {
                directoryConvert.convert(sourceDirectory, targetDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
