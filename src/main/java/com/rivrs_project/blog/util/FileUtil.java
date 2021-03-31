package com.rivrs_project.blog.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.YearMonth;

import com.rivrs_project.blog.config.WebMvcConfig;

/**
 * FileUtil it's a class that offers several static utility methods for handling files.
 * Creates path to image file, which contents current year and month.
 */
public class FileUtil {

    public static void createDirectoryIfItDoesntExist(String dir) {
        final Path path = Paths.get(dir);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static Path getImagePath(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(WebMvcConfig.IMAGE_FILE_BASE);
        sb.append(Year.now().getValue());
        createDirectoryIfItDoesntExist(sb.toString());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        createDirectoryIfItDoesntExist(sb.toString());
        sb.append("/");
        sb.append(fileName);
        return Paths.get(sb.toString());
    }

    public static String getImageUrl(String imageFileName) {
        String baseUrl = WebMvcConfig.BASE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        sb.append(WebMvcConfig.IMAGE_RESOURCE_BASE);
        sb.append(getYearAndMonthUrlFragment());
        sb.append(imageFileName);
        return sb.toString();
    }
    public static Path getDocumentPath(String fileName, String documentExtension) {
        StringBuilder sb = new StringBuilder();
        sb.append(WebMvcConfig.DOCUMENT_FILE_BASE);
        /*
        sb.append(Year.now().getValue());
        createDirectoryIfItDoesntExist(sb.toString());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        createDirectoryIfItDoesntExist(sb.toString());
        sb.append("/");
         */
        //TODO Add here user_id folder
        //appending uuid to create folder
        sb.append(fileName);
        createDirectoryIfItDoesntExist(sb.toString());
        sb.append("/");
        fileName += documentExtension;
        //appending full name with extension
        sb.append(fileName);
        return Paths.get(sb.toString());
    }

    public static String getDocumentUrl(String fileName, String documentExtension) {
        String baseUrl = WebMvcConfig.BASE_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl);
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        sb.append(WebMvcConfig.DOCUMENT_RESOURCE_BASE);
        /*
        sb.append(getYearAndMonthUrlFragment());
         */
        sb.append(fileName);
        sb.append("/");
        fileName += documentExtension;
        sb.append(fileName);
        return sb.toString();
    }

    private static String getYearAndMonthUrlFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append(Year.now().getValue());
        sb.append("/");
        sb.append(YearMonth.now().getMonthValue());
        sb.append("/");
        return sb.toString();
    }

}