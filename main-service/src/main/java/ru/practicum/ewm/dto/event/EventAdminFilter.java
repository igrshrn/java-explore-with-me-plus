package ru.practicum.ewm.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.entity.event.EventState;
import ru.practicum.ewm.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventAdminFilter {
    List<Long> userIds;
    List<EventState> states;
    List<Long> categoryIds;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;

    public void validateDates() {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Дата начала должна быть раньше даты окончания");
        }
    }
}
