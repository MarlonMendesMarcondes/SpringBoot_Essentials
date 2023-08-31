package academy.devdojo.CursoSpringBoot2Essentials.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class BadRequestExceptionDetails {
    private String title;
    private int status;
    private String details;
    private String developerMessage;
    private LocalDateTime timestamp;
}
