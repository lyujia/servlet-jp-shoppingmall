package com.nhnacademy.nhnpage.controller;

import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminPageController {
    private final PostRepository postRepository;
    public AdminPageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @GetMapping("/cs/admin")
    public ModelAndView index() {
        List<Post> posts = postRepository.getPostsByCommentExists();
        ModelAndView modelAndView = new ModelAndView("adminMainPage");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

}
