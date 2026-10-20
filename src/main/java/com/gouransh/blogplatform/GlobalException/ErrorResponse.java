package com.gouransh.blogplatform.GlobalException;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime localDateTime,
        int errorCode,
        String error,
        String message) {
}
