package com.rivrs_project.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({ WebSecurityConfig.class })
public class WebMvcConfig implements WebMvcConfigurer {
    //The IMAGE_RESOURCE_BASE constant defines the path segment that will serve images stored outside of the JAR file on the local server.
    public final static String IMAGE_RESOURCE_BASE = "/images/";
    public final static String DOCUMENT_RESOURCE_BASE = "/files/";
    public final static String FILE_RESOURCE_BASE = "/files/";
    //TODO Check how this works
    //TODO Create unabsolute path
    public final static String IMAGE_FILE_BASE = "C:/LushnikovDM-serverApp-test/blog/src/main/resources/images/";
    public final static String DOCUMENT_FILE_BASE = "C:/LushnikovDM-serverApp-test/blog/src/main/resources/documents/";
    public final static String BASE_URL = "http://localhost:8080";

    //The addResourceHandlers() method enables you to map file locations on the server to URL paths.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(IMAGE_RESOURCE_BASE + "**")
                .addResourceLocations("file:" + IMAGE_FILE_BASE);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}
