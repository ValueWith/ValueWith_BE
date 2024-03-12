package com.valuewith.tweaver.postLike.repository;

import com.valuewith.tweaver.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long>, PostLikeRepositoryCustom {

}
