package com.nhnacademy.nhnpage.controller;

import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.repository.PostRepository;
import com.nhnacademy.nhnpage.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.nhnacademy.nhnpage.domain.Post.Category;

import java.sql.Connection;
import java.util.List;

@Controller
public class UserPageController {
    private final PostRepository postRepository;
    public UserPageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @GetMapping("/cs/")
    public String showCs(Model model, @PathVariable(value = "catagory",required = false) String category, HttpServletRequest request) {
        String id = (String)request.getSession().getAttribute("id");
        System.out.println("id: " + id);
        if(category !=null) {
            System.out.println("category가 널이 아니다.");
            List<Post> posts = postRepository.getPostsByAuthorAndCategory(id, Category.valueOf(category));
            model.addAttribute("posts", posts);
        }else{

            System.out.println(id);
            List<Post> posts = postRepository.getPostsByAuthor(id);
            System.out.println("포스터사이즈"+posts.size());
            model.addAttribute("posts", posts);
        }
        return "userMainPage";
    }

}
