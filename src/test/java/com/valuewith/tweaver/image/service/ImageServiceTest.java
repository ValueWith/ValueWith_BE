package com.valuewith.tweaver.image.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.valuewith.tweaver.constants.ImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private AmazonS3 amazonS3;

    String cloudFrontDomain = "test-cloud-domain";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(imageService, "cloudFrontDomain", cloudFrontDomain);
    }

    @Test
    void uploadProfileImageSuccess() {
        MockMultipartFile file = new MockMultipartFile(
            "image", "test.jpeg", "image/jpeg", "image_content".getBytes()
        );

        when(amazonS3.putObject(any(PutObjectRequest.class)))
            .thenReturn(null);

        String result = imageService.uploadImageAndGetUrl(file, ImageType.PROFILE);
        assertTrue(result.startsWith("https://"));
        assertTrue(result.contains(cloudFrontDomain));
        assertTrue(result.endsWith(file.getOriginalFilename().replace(" ", "_")));
    }
    
    @Test
    void uploadProfileImageFailureInvalidFile() {
        MockMultipartFile file = new MockMultipartFile(
            "image", "test.txt", "text/plain", "text_content".getBytes()
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.uploadImageAndGetUrl(file, ImageType.PROFILE);
        });
        assertEquals("올바르지 않은 이미지 파일입니다. PNG, JPG, JPEG 형식만 가능합니다.", exception.getMessage());
    }

    @Test
    void uploadProfileImageFailureEmptyFile() {
        MockMultipartFile file = new MockMultipartFile(
            "image", "", "image/jpeg", "".getBytes()
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            imageService.uploadImageAndGetUrl(file, ImageType.PROFILE);
        });
        assertEquals("추가된 파일이 없습니다.", exception.getMessage());
    }

    @Test
    void modifiedImageWithFallbackSuccess() {
        MockMultipartFile newFile = new MockMultipartFile(
            "image", "new-test.jpeg", "image/jpeg", "new_image_content".getBytes()
        );

        String currentUrl = "https://test-cloud-domain/profile/old-test.jpeg";
        ImageType imageType = ImageType.PROFILE;

        when(imageService.uploadImageAndGetUrl(
            newFile,
            imageType)
        ).thenReturn(null);

        String result = imageService.modifiedImageWithFallback(newFile, currentUrl, imageType);

        assertTrue(result.startsWith("https://"));
        assertTrue(result.contains(cloudFrontDomain));
        assertTrue(result.endsWith("new-test.jpeg"));
    }
}