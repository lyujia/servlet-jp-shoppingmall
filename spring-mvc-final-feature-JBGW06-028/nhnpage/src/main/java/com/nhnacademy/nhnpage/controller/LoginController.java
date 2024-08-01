package com.nhnacademy.nhnpage.controller;

import com.nhnacademy.nhnpage.domain.Admin;
import com.nhnacademy.nhnpage.domain.Person;
import com.nhnacademy.nhnpage.domain.User;
import com.nhnacademy.nhnpage.exception.IdPassNotCorrectException;
import com.nhnacademy.nhnpage.repository.PersonRepository;
import com.nhnacademy.nhnpage.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final PersonRepository personRepository;
    public LoginController(PersonRepository personRepository) {this.personRepository = personRepository;}
    @GetMapping("/cs/login")
    public String login() {
        System.out.println("여기");
        return "loginForm";//
    }
    @PostMapping("/cs/login")
    public String login(@RequestParam("id") String id, @RequestParam("pwd") String password, Model model, HttpServletRequest request,HttpServletResponse response) {
        Person person = personRepository.matches(id, password);
        System.out.println(3);
        if (person == null) {
            throw new IdPassNotCorrectException();//맞는사람이 없으면 오류띄운다.
        }else {
            //사람존재하지만 유저랑 admin으로 경우 나눠야함
            //그리고 성공했으니까 세션 쿠키 넣어줘야한다.
            HttpSession session = request.getSession();
            session.setAttribute("id", id);
            //무효화되어야할 쿠키있으면 무효화해준다.

            Cookie cookie = new Cookie("SESSIONNHN", session.getId());
            cookie.setMaxAge(30*60);
            System.out.println(person.getName());
            response.addCookie(cookie);
            if (person instanceof Admin) {
                model.addAttribute("admin", person);
                System.out.println("admin");
                return "redirect:/cs/admin";
            } else{
                System.out.println("user");
                return "redirect:/cs/";
            }

        }
    }



}
