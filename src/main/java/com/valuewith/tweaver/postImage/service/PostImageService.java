package com.valuewith.tweaver.postImage.service;

import static com.valuewith.tweaver.constants.ErrorCode.IMAGE_SAVE_ERROR;

import com.valuewith.tweaver.constants.ImageType;
import com.valuewith.tweaver.defaultImage.service.ImageService;
import com.valuewith.tweaver.exception.CustomException;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.postImage.dto.PostImageDto;
import com.valuewith.tweaver.postImage.entity.PostImage;
import com.valuewith.tweaver.postImage.repository.PostImageRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class PostImageService {

  private ImageService imageService;
  private PostImageRepository postImageRepository;

  public void saveImageList(List<MultipartFile> images, Post post) {
    try {
      log.info("포스트 이미지 저장중 - " + post.getTitle());
      List<PostImage> imageList = images.stream()
          .map(file -> new PostImageDto().setPostImageUrlAndPost(
              imageService.uploadImageAndGetUrl(file, ImageType.POST),
              post))
          .collect(Collectors.toList());
      // 저장
      postImageRepository.saveAll(imageList);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new CustomException(IMAGE_SAVE_ERROR);
    }
  }
}
