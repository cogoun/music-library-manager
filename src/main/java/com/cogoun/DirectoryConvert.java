package com.cogoun;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

public class DirectoryConvert implements AudioFileConverter {

    FileCopyAndConvert fileCopyAndConvert = new FileCopyAndConvert();

    @Override
    public void convert(File sourceDirectory, File targetDirectory) throws Exception {
        if (sourceDirectory.isFile()) {
            throw new InvalidParameterException("not a directory");
        }

        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        for (File childFile : sourceDirectory.listFiles()) {
            if (childFile.isFile()) {
                fileCopyAndConvert.convert(childFile, new File(targetDirectory.getAbsolutePath()+"/"+childFile.getName()));
            }
            if (childFile.isDirectory()) {
                convert(childFile, new File(targetDirectory.getAbsolutePath()+"/"+childFile.getName()));
            }
        }
    }
}
