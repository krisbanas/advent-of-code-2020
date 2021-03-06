package com.github.krisbanas.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileHelper {

    public static List<String> loadFileFromResources(String filename) {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/" + filename).toAbsolutePath());
        } catch (IOException ex) {
            return Collections.emptyList();
        }
    }

    public static String loadFileFromResourcesAsString(String filename) {
        try {
            return Files.readString(Paths.get("src/main/resources/" + filename).toAbsolutePath());
        } catch (IOException ex) {
            return "";
        }
    }
}
