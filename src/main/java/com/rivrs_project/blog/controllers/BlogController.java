package com.rivrs_project.blog.controllers;

import com.rivrs_project.blog.models.BlogPost;
import com.rivrs_project.blog.repo.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    //TODO buttons only for admin
    @GetMapping("/blog")
    public String blogMain(Model model){
        model.addAttribute("title", "Блог");
        Iterable<BlogPost> blogPosts = blogRepository.findAllByOrderByIdAsc();
        model.addAttribute("posts", blogPosts);
        return "blog-main";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if (!blogRepository.existsById(id)){
            System.out.println("Error: trying to access non-existent post");
            return "redirect:/blog";
        }
        BlogPost blogPost = blogRepository.findById(id).orElseThrow(IllegalStateException::new);
        blogPost.newView();
        model.addAttribute("blogPost", blogPost);
        return "blog-detail";
    }
}
