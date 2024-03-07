package com.valuewith.tweaver.bookmark.service;

import com.valuewith.tweaver.bookmark.entity.Bookmark;
import com.valuewith.tweaver.bookmark.repository.BookmarkRepository;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.group.repository.TripGroupRepository;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
  private final MemberRepository memberRepository;
  private final TripGroupRepository tripGroupRepository;
  private final BookmarkRepository bookmarkRepository;

  public Bookmark createBookmark(Long memberId, Long tripGroupId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> {
          throw new RuntimeException("멤버가 존재 하지 않습니다.");
        });
    TripGroup tripGroup = tripGroupRepository.findById(tripGroupId)
        .orElseThrow(() -> {
          throw new RuntimeException("그룹이 존재 하지 않습니다.");
        });
    return bookmarkRepository.save(Bookmark.builder().member(member).tripGroup(tripGroup).build());
  }

  @Transactional
  public void removeBookmark(Long memberId, Long bookmarkId) {
    bookmarkRepository.deleteByMemberIdAndBookmarkId(memberId, bookmarkId);
  }
}
