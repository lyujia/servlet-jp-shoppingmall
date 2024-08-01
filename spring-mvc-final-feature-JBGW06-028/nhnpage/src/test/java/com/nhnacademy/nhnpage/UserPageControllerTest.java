package com.nhnacademy.nhnpage;

import com.nhnacademy.nhnpage.controller.UserPageController;
import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserPageController.class)
@ContextConfiguration(classes = {PostRepository.class})
public class UserPageControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testShowCsWithoutCategory() throws Exception {
        String authorId = "user";
        List<Post> posts = Arrays.asList(
                new Post("Title1", "Content1", authorId, Post.Category.GENERAL)
        );

        when(postRepository.getPostsByAuthor(authorId)).thenReturn(posts);

        mockMvc.perform(get("/cs/")
                        .sessionAttr("id", authorId))
                .andExpect(status().isOk())
                .andExpect(view().name("userMainPage"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", posts));

        verify(postRepository, times(1)).getPostsByAuthor(authorId);
    }

    @Test
    void testShowCsWithCategory() throws Exception {
        String authorId = "user";
        String category = "GENERAL";
        List<Post> posts = Arrays.asList(
                new Post("Title1", "Content1", authorId, Post.Category.valueOf(category))
        );

        when(postRepository.getPostsByAuthorAndCategory(authorId, Post.Category.valueOf(category))).thenReturn(posts);

        mockMvc.perform(get("/cs/" + category)
                        .sessionAttr("id", authorId))
                .andExpect(status().isOk())
                .andExpect(view().name("userMainPage"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", posts));

        verify(postRepository, times(1)).getPostsByAuthorAndCategory(authorId, Post.Category.valueOf(category));
    }
}
