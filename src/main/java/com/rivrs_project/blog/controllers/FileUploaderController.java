package com.rivrs_project.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.rivrs_project.blog.util.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class FileUploaderController {
    //TODO Security to url. Why? Because if you don't then anybody can upload an image to your server. You don't want that.
    /**
     * return url in saveImage it's the URL of the image after it's copied to the file directory on the server.
     * The WYSIWYG editor will need that to produce the proper <img> tag.
     * But how does the upload actually happen? That's with the assistance of the image upload handler.
     * It's also defined in the TinyMCE lib configuration
      */
    @PostMapping("/image")
    public String saveImage(@RequestParam("file") MultipartFile file) {
        String url = null;
        try {
            url = copyFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String copyFile(MultipartFile file) throws Exception {
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
}