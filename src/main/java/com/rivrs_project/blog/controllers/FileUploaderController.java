package com.rivrs_project.blog.controllers;

import com.rivrs_project.blog.util.FileUtil;
import com.rivrs_project.blog.util.TerminalUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

//TODO (only for test default  - ADMIN)
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
@RestController
public class FileUploaderController {
    //TODO Security to url. Why? Because if you don't then anybody can upload an image to your server. You don't want that.
    /**
     * return url in saveImage it's the URL of the image after it's copied to the file directory on the server.
     * The WYSIWYG editor will need that to produce the proper <img> tag.
     * But how does the upload actually happen? That's with the assistance of the image upload handler.
     * It's also defined in the TinyMCE lib configuration
      */
    @PostMapping("/upload")
    public String saveFile(@RequestParam("file") MultipartFile file) {
        long start = System.nanoTime();
        String url = null;
        String documentPath = null;
        UUID uuid = UUID.randomUUID(); //unique filename
        String fileName = uuid.toString();

        try {
            String[] result = copyFile2(file, fileName);
            url = result[0];
            documentPath = result[1];
            double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
            System.out.println("Time of loading file on server: " + elapsedTimeInSec);

            long start2 = System.nanoTime();
            String docxCorrectorPath = "C:/LushnikovDM-serverApp-test/DocxCorrectorCore.exe";
            String startId = "0"; // DocxCorrector will start to count paragraphs from this number
            String resultPath1 = getFilePathWithoutFileName(documentPath) + "result1.csv";
            String resultPath2 = getFilePathWithoutFileName(documentPath) + "result2.csv";
            String docxCorrectorCommand = "pull";
            String commandForDocxCorrector = docxCorrectorPath
                    + " " + docxCorrectorCommand
                    + " " + documentPath
                    + " " + startId
                    + " " + resultPath1
                    + " " + resultPath2;
            TerminalUtil csvPuller = new TerminalUtil();
            System.out.println("\n");
            csvPuller.executeCommand(commandForDocxCorrector);
            double elapsedTimeInSec2 = (System.nanoTime() - start2) * 1.0e-9;
            System.out.println("Time of DocxCorrector work: " + elapsedTimeInSec2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    @PostMapping("/upload/image")
    public String saveImage(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = copyImage(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
    private String copyImage(MultipartFile file) throws Exception {
        String url = null;
        //That's because TinyMCE renames the file. The uploaded file name will look like this: blobid1598626219760.jpg.
        String fileName = file.getOriginalFilename();
        try (InputStream is = file.getInputStream()) {
            Path path = FileUtil.getImagePath(fileName);
            Files.copy(is, path);
            url = FileUtil.getImageUrl(fileName);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new Exception("Failed to upload!");
        }
        return url;
    }
    private String[] copyFile2(MultipartFile file, String fileName) throws Exception {
        String[] result = new String[2];
        String url = null;
        String documentPath = null;
        String documentExtension = getFileExtension(file);

        try (InputStream is = file.getInputStream()) {
            Path path = FileUtil.getDocumentPath(fileName, documentExtension);
            documentPath = path.toString();
            Files.copy(is, path);
            url = FileUtil.getDocumentUrl(fileName, documentExtension);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new Exception("Failed to upload!");
        }
        result[0] = url;
        result[1] = documentPath;
        return result;
    }

    private String getFileExtension(MultipartFile file) {
        String name = file.getOriginalFilename();
        assert name != null;
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
    private String getFilePathWithoutFileName(String path) {
        String filePath;
        int lastIndexOf = path.lastIndexOf("\\");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        filePath = path.substring(0, lastIndexOf + 1);
        return filePath;
    }

}
