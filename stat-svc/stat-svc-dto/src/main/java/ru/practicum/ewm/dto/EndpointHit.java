package ru.practicum.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

    public LocalDateTime getTimestampAsDateTime() {
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}