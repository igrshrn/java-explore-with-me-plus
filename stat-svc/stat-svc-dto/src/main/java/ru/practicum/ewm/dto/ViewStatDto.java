package ru.practicum.ewm.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatDto {
    String app;
    String uri;
    Long hits;
}
