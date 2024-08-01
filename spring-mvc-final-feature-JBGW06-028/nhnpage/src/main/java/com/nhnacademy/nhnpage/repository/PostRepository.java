package com.nhnacademy.nhnpage.repository;

import com.nhnacademy.nhnpage.domain.Post;
import com.nhnacademy.nhnpage.domain.Post.Category;


import java.util.List;

public interface PostRepository {
    Post registerPost(Post post);
    List<Post> getPostsByAuthor(String authorId);
    List<Post> getPostsByAuthorAndCategory(String authorId, Category category);
    List<Post> getPostsByCategory(Category category);
    List<Post> getPostsByCommentExists();

}
