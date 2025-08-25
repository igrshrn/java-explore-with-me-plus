package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.event.LocationDto;

public class LocationMapper {
    public static LocationDto mapToLocation(LocationDto locationDto) {
        return LocationDto.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto mapToLocationDto(LocationDto location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
