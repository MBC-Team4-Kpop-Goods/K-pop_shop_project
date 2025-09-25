package com.dhunters.kpop.core.util;

import com.dhunters.kpop.core.exception.ImageExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtil {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "webp", "bmp"
    );

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp", "image/bmp"
    );

    public static String generateStoredFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);

        return timestamp + "_" + uuid + "." + extension;
    }

    public static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new ImageExceptions.InvalidFileType("파일 확장자를 찾을 수 없습니다.");
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    public static void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ImageExceptions.InvalidFileType("업로드할 파일이 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new ImageExceptions.InvalidFileType("파일명이 없습니다.");
        }

        String extension = getFileExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ImageExceptions.InvalidFileType("지원하지 않는 파일 형식입니다: " + extension);
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new ImageExceptions.InvalidFileType("지원하지 않는 MIME 타입입니다: " + contentType);
        }
    }

    public static ImageDimension getImageDimensions(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image != null) {
                return new ImageDimension(image.getWidth(), image.getHeight());
            }
        } catch (IOException e) {
            log.warn("이미지 크기를 읽을 수 없습니다: {}", e.getMessage());
        }
        return new ImageDimension(null, null);
    }

    public static void createDirectoryIfNotExists(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("디렉토리 생성: {}", path);
            }
        } catch (IOException e) {
            throw new ImageExceptions.UploadFailed("디렉토리 생성 실패: " + path, e);
        }
    }

    public static class ImageDimension {
        public final Integer width;
        public final Integer height;

        public ImageDimension(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }
    }
}
