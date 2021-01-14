package com.rivrs_project.blog.controllers;

import com.rivrs_project.blog.models.BlogPost;
import com.rivrs_project.blog.repo.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class BlogPostController {
    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/blog/add")
    public String addEditPost(Model model) {
        setDefaultBlogPost(model);
        model.addAttribute("title", "Добавление статьи");
        return "admin/blog-add";
    }

    @PostMapping("/blog/add")
    public String addEditPostSubmit(@RequestParam String title, @RequestParam String anons, @RequestParam String body, Model model, BlogPost blogPost) {
        System.out.println("Title is " + blogPost.getTitle());
        System.out.println("Anons is " + blogPost.getAnons());
        System.out.println("Body is " + blogPost.getBody());
        blogRepository.save(blogPost);
        System.out.println("New post was successfully added");
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        BlogPost blogPost = blogRepository.findById(id).orElseThrow(IllegalStateException::new);
        model.addAttribute("blogPost", blogPost);
        return "admin/blog-edit";
    }
    @PostMapping("/blog/{id}/edit")
    public String blogEditSubmit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String body, Model model){
        BlogPost blogPost = blogRepository.findById(id).orElseThrow(IllegalStateException::new);
        blogPost.setTitle(title);
        blogPost.setAnons(anons);
        blogPost.setBody(body);
        blogRepository.save(blogPost);
        model.addAttribute("title", "Изменение статьи");
        System.out.println("post was successfully edited");
        return "redirect:/blog/{id}";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model){
        if (!blogRepository.existsById(id)){
            System.out.println("Error: trying to delete non-existent post");
            return "redirect:/blog";
        }
        BlogPost blockPost = blogRepository.findById(id).orElseThrow(IllegalStateException::new);
        blogRepository.delete(blockPost);
        System.out.println("post was successfully deleted");
        return "redirect:/blog";
    }
    private void setDefaultBlogPost(Model model) {
        BlogPost blogPost = new BlogPost();
        model.addAttribute("blogPost", blogPost);
    }
}