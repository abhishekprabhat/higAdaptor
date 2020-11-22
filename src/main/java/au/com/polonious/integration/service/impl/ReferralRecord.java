package au.com.polonious.integration.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
public class ReferralRecord {
    private String exposireId;
    private String detectionMethod;
    private String detectionMethod2;
    private float score;
    private LocalDateTime timestamp;
}
