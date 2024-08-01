package com.nhnacademy.nhnpage.request;

import com.nhnacademy.nhnpage.domain.Post.Category;
import lombok.Value;

import java.util.Date;

@Value
public class PostRegisterRequest {
    String title;
    String content;
    String category;



}
