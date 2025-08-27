package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.event.LocationDto;
import ru.practicum.ewm.entity.event.Location;

public class LocationMapper {
    public static Location mapToLocation(LocationDto locationDto) {
        return Location.builder()
                .lat(locationDto.getLat())
                .lon(locationDto.getLon())
                .build();
    }

    public static LocationDto mapToLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
