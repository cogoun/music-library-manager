package com.cogoun;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.nio.file.Files;
import java.security.InvalidParameterException;

public class FileCopyAndConvert implements AudioFileConverter{
    public void convert(File sourceFile, File targetFile) throws Exception {
        if (!sourceFile.isFile()) {
            throw new InvalidParameterException("not a file");
        }
        if (sourceFile.getName().startsWith(".")) {
            return;
        }
        if (targetFile.exists()) {
            return; // do nothing
        }
        if (sourceFile.getName().endsWith("m4a") || sourceFile.getName().endsWith("flac")) {
            FreacCommandFactory commandFactory = new FreacCommandFactory(sourceFile, targetFile, FreacAudioEncoder.getFromFileType());
            String command = commandFactory.getCommand();
            System.out.print("COMMAND: " + command + " RESULT: ");
            Process p = Runtime.getRuntime().exec(command);
            while (p.isAlive()) {
                Thread.sleep(100);
            }
            System.out.println(p.exitValue());
        } else {
            Files.copy(sourceFile.toPath(), targetFile.toPath());
        }
    }




}
