package com.nhnacademy.nhnpage.validator;

import com.nhnacademy.nhnpage.request.PostRegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class PostRegisterRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz){return PostRegisterRequest.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors){
        PostRegisterRequest postRegisterRequest = (PostRegisterRequest) target;
        ValidationUtils.rejectIfEmpty(errors,"title","404","Title is required");//post할때 이거를 거쳐서 간다.
        ValidationUtils.rejectIfEmpty(errors,"content","404","Content is required");
    }
}
