package ru.practicum.ewm.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.entity.event.EventSort;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventPublicFilter {
    String text;
    List<Long> categoryIds;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    Boolean onlyAvailable;
    EventSort sort;

    public void validateDates() {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Время начала события позже времени окончания");
        }
    }
}
