package ru.practicum.ewm.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.entity.event.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
