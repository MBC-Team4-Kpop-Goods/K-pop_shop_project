package com.dhunters.kpop.core.exception;

public class ImageExceptions {

    /**
     * 이미지를 찾을 수 없을 때 발생하는 예외
     */
    public static class NotFound extends RuntimeException {
        public NotFound(String message) {
            super(message);
        }

        public NotFound(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 파일 업로드 중 발생하는 예외
     */
    public static class UploadFailed extends RuntimeException {
        public UploadFailed(String message) {
            super(message);
        }

        public UploadFailed(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 지원하지 않는 파일 형식일 때 발생하는 예외
     */
    public static class InvalidFileType extends RuntimeException {
        public InvalidFileType(String message) {
            super(message);
        }
    }

    /**
     * 파일 크기가 제한을 초과했을 때 발생하는 예외
     */
    public static class FileSizeExceeded extends RuntimeException {
        public FileSizeExceeded(String message) {
            super(message);
        }
    }

    /**
     * 이미지 처리 중 발생하는 예외
     */
    public static class ProcessingFailed extends RuntimeException {
        public ProcessingFailed(String message) {
            super(message);
        }

        public ProcessingFailed(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
