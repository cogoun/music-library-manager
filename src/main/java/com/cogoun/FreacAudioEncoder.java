package com.cogoun;

import java.util.Arrays;

public enum FreacAudioEncoder {

    MP3("lame", "mp3"), FLAC("flac", "flac"), ALAC("coreaudio", "m4a"), NONE("none", "");

    private final String encoder;
    private final String fileType;

    FreacAudioEncoder(String encoder, String fileType) {
        this.encoder = encoder;
        this.fileType = fileType;
    }

    public static FreacAudioEncoder getFromFileType(String fileType) {
        return Arrays.stream(values()).filter(encoder -> encoder.fileType.equals(fileType)).findFirst().orElse(NONE);
    }

    public String getEncoder() {
        return encoder;
    }

    public String getFileType() {
        return fileType;
    }
}
