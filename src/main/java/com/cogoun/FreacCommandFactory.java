package com.cogoun;

import java.io.File;

public class FreacCommandFactory {

    private File sourceFile;
    private File targetFile;
    private FreacAudioEncoder format;

    public FreacCommandFactory(File sourceFile, File targetFile, FreacAudioEncoder format) {
        this.sourceFile = sourceFile;
        this.targetFile = targetFile;
        this.format = format;
    }

    public String getCommand() {
        return command()
                + convertTo(format)
                + outputDirectory(targetFile)
                + pattern("<track> - <title>")
                + quiet()
                + sourceFile(sourceFile);
    }

    private String sourceFile(File sourceFile) {
        return " " + escape(sourceFile.getAbsolutePath());
    }

    private String quiet() {
        return " --quiet";
    }

    private String pattern(String pattern) {
        return " -p " + escape(pattern);
    }

    private String outputDirectory(File targetFile) {
        return " -d " + escape(targetFile.getParent());
    }

    private String convertTo(FreacAudioEncoder encoder) {
        return " -e " + encoder.getFileType();
    }

    private String command() {
        return "freaccmd";
    }


    private String escape(String input) {
        return "\"" + input + "\"";
    }
}
