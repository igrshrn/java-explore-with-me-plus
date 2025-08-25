package ru.practicum.ewm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.entity.request.ParticipationRequest;
import ru.practicum.ewm.entity.request.RequestStatus;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request);

    default String mapRequestStatusToString(RequestStatus status) {
        return status != null ? status.name() : null;
    }

    default RequestStatus mapStringToRequestStatus(String status) {
        return status != null ? RequestStatus.valueOf(status) : null;
    }
}
