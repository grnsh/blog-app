package com.gouransh.blogplatform.GlobalException;

import java.nio.file.AccessDeniedException;

public class UnauthorizedAccessException extends AccessDeniedException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}