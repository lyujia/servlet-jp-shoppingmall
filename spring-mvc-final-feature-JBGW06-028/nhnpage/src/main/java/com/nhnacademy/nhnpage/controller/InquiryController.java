package com.nhnacademy.nhnpage.controller;

import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.domain.Post.*;
import com.nhnacademy.nhnpage.repository.PostRepository;
import com.nhnacademy.nhnpage.request.PostRegisterRequest;
import com.nhnacademy.nhnpage.validator.PostRegisterRequestValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cs/inquiry")
public class InquiryController {
    private final PostRepository postRepository;
    private final PostRegisterRequestValidator validator;
    public InquiryController(PostRepository postRepository, PostRegisterRequestValidator validator) {
        this.postRepository = postRepository;
        this.validator = validator;
    }
    @GetMapping
    public String inquiry() {
        System.out.println("문의 창");
        return "inquiryForm";
    }
    @PostMapping
    public String registerInquiry(@Validated @ModelAttribute PostRegisterRequest postRegisterRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("id");
        System.out.println("id: " + id);
        System.out.println("머 인식은됌");
        postRepository.registerPost(new Post(postRegisterRequest.getTitle(), postRegisterRequest.getContent(), id,Category.valueOf(postRegisterRequest.getCategory())));
        System.out.println("여긱까지도됌 ");
        return "redirect:/cs/";
    }
    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
