package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.ewm.dto.event.*;
import ru.practicum.ewm.entity.event.Event;
import ru.practicum.ewm.entity.event.Event.EventState;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class})
public interface EventMapper {
    @Mapping(source = "category", target = "category")
    @Mapping(source = "initiator", target = "initiator")
    EventFullDto toEventFullDto(Event event);

    @Mapping(source = "category", target = "category")
    @Mapping(source = "initiator", target = "initiator")
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    Event toEvent(NewEventDto newEventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    void updateEventFromUserRequest(UpdateEventUserRequest updateEventUserRequest, @MappingTarget Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "compilations", ignore = true)
    void updateEventFromAdminRequest(UpdateEventAdminRequest updateEventAdminRequest, @MappingTarget Event event);

    default String mapEventStateToString(EventState state) {
        return state != null ? state.name() : null;
    }

    default EventState mapStringToEventState(String state) {
        return state != null ? EventState.valueOf(state) : null;
    }
}
