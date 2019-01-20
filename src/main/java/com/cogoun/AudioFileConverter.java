package com.cogoun;

import java.io.File;

public interface AudioFileConverter {

    void convert(File sourceFile, File targetFile) throws Exception;
}
