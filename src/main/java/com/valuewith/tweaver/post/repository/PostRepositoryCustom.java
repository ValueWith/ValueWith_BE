package com.valuewith.tweaver.post.repository;

import com.valuewith.tweaver.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

  List<Post> findPostByTitleAndTripGroupArea(String title, String area, Pageable pageable);
}
