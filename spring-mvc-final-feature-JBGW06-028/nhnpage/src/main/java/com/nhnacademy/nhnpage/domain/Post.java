package com.nhnacademy.nhnpage.domain;


import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    public static int count = 0;
    String title;
    String content;
    String authorId;
    boolean isExistscomment;
    Category category;
    LocalDate date;
    public enum Category{
        COMPLAINT,SUGGESTION,REFUND,COMPLIMENT,GENERAL
    }
    public Post(String title, String content, String authorId, Category category) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.category = category;
        this.date = LocalDate.now();
        this.isExistscomment = false;
    }//postdate랑 isexistscommit은 자동으로 들어가는거다...포스트 아이디도!

}
