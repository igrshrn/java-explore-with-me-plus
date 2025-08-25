package ru.practicum.ewm.service.event;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventPublicFilter;
import ru.practicum.ewm.dto.event.EventShortDto;

import java.util.List;

public interface EventPublicService {

    List<EventShortDto> getAll(EventPublicFilter publicFilter, Integer from, Integer size,
                               HttpServletRequest httpServletRequest);

    EventFullDto getById(Long eventId, HttpServletRequest httpServletRequest);
}
