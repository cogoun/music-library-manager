package com.cogoun;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {

    public static List<String> notConvertedFiles = new ArrayList<>();
    public static Long numberOfConvertedFiles = 0L;

    public static void main(String... args) {

//        File sourceDirectory = new File(args[0]);
//        File targetDirectory = new File(args[1]);
        List<String> dirs = Arrays.asList("classical\\", "collections\\", "ost\\");
        String sourceDirectoryPath = "D:\\music\\";
        String targetDirectoryPath = "K:\\music\\";
        for (String dir : dirs) {
            test(new File(sourceDirectoryPath+dir), new File(targetDirectoryPath+dir));
        }



    }

    private static void test(File library, File targetLibrary) {
        DirectoryConvert directoryConvert = new DirectoryConvert();
        if (library.isDirectory()) {
            try {
                directoryConvert.perform(library, targetLibrary);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Converted files: " + numberOfConvertedFiles);
            System.out.println("Non converted files (fre:ac returned non 0 exit code):");
            notConvertedFiles.stream().forEach(fileName -> System.out.println(fileName));
        }
    }

    public static class DirectoryConvert {

        FileCopyAndConvert fileCopyAndConvert = new FileCopyAndConvert();

        public void perform(File sourceDirectory, File targetDirectory) throws IOException, InterruptedException {
            if (sourceDirectory.isFile()) {
                throw new InvalidParameterException("not a directory");
            }

            if (!targetDirectory.exists()) {
                targetDirectory.mkdirs();
            }

            for (File childFile : sourceDirectory.listFiles()) {
                if (childFile.isFile()) {
                    fileCopyAndConvert.perform(childFile, new File(targetDirectory.getAbsolutePath()+"/"+childFile.getName()));
                }
                if (childFile.isDirectory()) {
                    perform(childFile, new File(targetDirectory.getAbsolutePath()+"/"+childFile.getName()));
                }
            }
        }
    }

    public static class FileCopyAndConvert {
        public void perform(File sourceFile, File targetFile) throws IOException, InterruptedException {
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
                String command = "freaccmd -e flac"
                        + " -d " + escape(targetFile.getParent())
                        + " -p " + escape("<track> - <title>")
                        + " "   + escape(sourceFile.getAbsolutePath());
                System.out.print("COMMAND: " + command + " RESULT: ");
                Process p = Runtime.getRuntime().exec(command);
                while (p.isAlive()) {
                    Thread.sleep(100);
                }
                if (p.exitValue() == 0) {
                    numberOfConvertedFiles++;
                } else {
                    notConvertedFiles.add(sourceFile.getAbsolutePath());
                }
                System.out.println(p.exitValue());
            } else {
                Files.copy(sourceFile.toPath(), targetFile.toPath());
            }
        }

        private String escape(String input) {
            return "\"" + input + "\"";
        }
    }
}
