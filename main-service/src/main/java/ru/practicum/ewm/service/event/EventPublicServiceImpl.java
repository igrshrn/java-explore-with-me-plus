package ru.practicum.ewm.service.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.ewm.dto.EndpointHit;
import ru.practicum.ewm.dto.StatRequest;
import ru.practicum.ewm.dto.ViewStatDto;
import ru.practicum.ewm.dto.event.EventFullDto;
import ru.practicum.ewm.dto.event.EventPublicFilter;
import ru.practicum.ewm.dto.event.EventShortDto;
import ru.practicum.ewm.entity.event.Event;
import ru.practicum.ewm.entity.event.EventSort;
import ru.practicum.ewm.entity.event.EventState;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.repository.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventPublicServiceImpl implements EventPublicService {

    final EventRepository eventRepository;
    final StatClient statsClient;
    final EventMapper eventMapper;
    private long id = 0;

    @Override
    public List<EventShortDto> getAll(EventPublicFilter publicFilter, Integer from, Integer size,
                                      HttpServletRequest httpServletRequest) {
        publicFilter.validateDates();
        Specification<Event> specification = DbSpecification.getPublicSpecification(
                publicFilter.getText(),
                publicFilter.getCategoryIds(),
                publicFilter.getPaid(),
                publicFilter.getRangeStart(),
                publicFilter.getRangeEnd(),
                publicFilter.getOnlyAvailable());
        Sort sort = Optional.ofNullable(publicFilter.getSort())
                .map(s -> Sort.by(Sort.Direction.DESC, s == EventSort.EVENT_DATE ? "eventDate" : "views"))
                .orElse(Sort.unsorted());
        hit(httpServletRequest);
        return eventRepository.findAll(specification, PageRequest.of(from / size, size).withSort(sort))
                .stream()
                .map(eventMapper::toEventShortDto)
                .toList();
    }

    @Override
    public EventFullDto getById(Long eventId, HttpServletRequest httpServletRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Событие id = %d не найдено".formatted(eventId)));
        if (event.getState() != EventState.PUBLISHED) {
            throw new NotFoundException("getById: Событие id = %d не опубликовано".formatted(eventId));
        }
        hit(httpServletRequest);
        StatRequest statsRequest = StatRequest.builder()
                .start(event.getPublishedOn())
                .end(LocalDateTime.now())
                .uris(List.of("/events/" + eventId))
                .unique(true)
                .build();
        List<ViewStatDto> stats = statsClient.getStats(statsRequest);
        log.info("Метод getById, длина списка stats: {}", stats.size());
        Long views = stats.isEmpty() ? 0L : stats.getFirst().getHits();
        event.setViews(views);
        log.info("Метод getById, количество сохраняемых просмотров: {}", views);
        return eventMapper.toEventFullDto(event);
    }

    private void hit(HttpServletRequest httpServletRequest) {
        EndpointHit hitDtoRequest = new EndpointHit(
                id++,
                "main-server",
                httpServletRequest.getRequestURI(),
                httpServletRequest.getRemoteAddr(),
                LocalDateTime.now()
        );
        statsClient.saveHit(hitDtoRequest);
    }
}
