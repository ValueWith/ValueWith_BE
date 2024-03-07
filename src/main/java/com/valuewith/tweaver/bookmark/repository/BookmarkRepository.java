package com.valuewith.tweaver.bookmark.repository;

import com.valuewith.tweaver.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  @Query("DELETE FROM Bookmark b WHERE b.member.memberId = :memberId AND b.bookmarkId = :bookmarkId")
  @Modifying
  void deleteByMemberIdAndBookmarkId(@Param("memberId") Long memberId, @Param("bookmarkId") Long bookmarkId);
}
