package com.nhnacademy.nhnpage;

import com.nhnacademy.nhnpage.controller.InquiryController;
import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.repository.PostRepository;
import com.nhnacademy.nhnpage.request.PostRegisterRequest;
import com.nhnacademy.nhnpage.validator.PostRegisterRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InquiryController.class)
@ContextConfiguration(classes = {PostRepository.class, PostRegisterRequestValidator.class})
public class InquiryControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostRegisterRequestValidator validator;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testInquiryForm() throws Exception {
        mockMvc.perform(get("/cs/inquiry"))
                .andExpect(status().isOk())
                .andExpect(view().name("inquiryForm"));
    }

    @Test
    void testRegisterInquiry() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", "user");

        PostRegisterRequest request = new PostRegisterRequest("title","content", "Categoty");

        mockMvc.perform(post("/cs/inquiry")
                        .session(session)
                        .param("title", "Test Title")
                        .param("content", "Test Content")
                        .param("category", "GENERAL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/"));

        verify(postRepository, times(1)).registerPost(any(Post.class));
    }
}
