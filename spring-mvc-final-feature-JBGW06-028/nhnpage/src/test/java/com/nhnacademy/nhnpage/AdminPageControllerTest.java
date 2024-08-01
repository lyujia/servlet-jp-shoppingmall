package com.nhnacademy.nhnpage;

import com.nhnacademy.nhnpage.controller.AdminPageController;
import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminPageController.class)
@ContextConfiguration(classes = {PostRepository.class})
public class AdminPageControllerTest {
    private MockMvc mockMvc;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminPageController(postRepository)).build();
    }

    @Test
    void testAdminPage() throws Exception {
        // 가짜 데이터 생성
        Post post1 = new Post("Title1", "Content1", "Author1", Post.Category.GENERAL);
        Post post2 = new Post("Title2", "Content2", "Author2", Post.Category.GENERAL);
        List<Post> posts = Arrays.asList(post1, post2);

        // 리포지토리의 메소드 호출에 대한 목 설정
        when(postRepository.getPostsByCommentExists()).thenReturn(posts);

        // 컨트롤러의 "/cs/admin" URL에 대한 GET 요청 수행 및 결과 검증
        mockMvc.perform(get("/cs/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminMainPage"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("posts", posts));
    }
}
