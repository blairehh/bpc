package org.bpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Util {
    public static String read(String file) {
        StringBuilder builder = new StringBuilder();
        InputStream is = Util.class.getClassLoader().getResourceAsStream(file);
        if (is == null) {
            throw new IllegalArgumentException("file not found");
        }
        InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try (BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builder.toString().stripTrailing();
    }
}
