package com.pjkurs.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilesUitl {

    public static Path getCurrentPath() {
        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath();
    }

    public static File getFile(String path) {
        Path relativePath = Paths.get(getCurrentPath().toAbsolutePath().toString()+ "\\" + path);
        File file = new File(relativePath.toString());
        return file;
    }

    public static File createFile(String fileName) {
        return new File(getCurrentPath().toAbsolutePath().toString()+ "\\" + fileName);
    }

    public static void deleteFile(String path) {
        try {
            File fileToDel = new File(getCurrentPath().toString() + path);
            Files.deleteIfExists(fileToDel.toPath());

        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "Delete file exception", e);
        }
    }
}

