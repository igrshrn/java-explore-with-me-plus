package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.dto.EndpointHit;
import ru.practicum.ewm.dto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatClient {
    private final RestClient restClient;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void saveHit(EndpointHit hit) {
        try {
            log.info("Сохранение информации о запросе {}", hit);
            restClient.post()
                    .uri("/hit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(hit)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Информация о запросе сохранена: {}", hit);
        } catch (Exception e) {
            log.error("Oшибка при сохранении информации {}", e.getMessage(), e);
        }
    }

    public List<ViewStats> getStats(LocalDateTime start,
                                    LocalDateTime end,
                                    List<String> uris,
                                    boolean unique) {
        try {
            log.info("Запрос статистики {}", start);
            UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/stats")
                    .queryParam("start", start.format(FORMATTER))
                    .queryParam("end", end.format(FORMATTER))
                    .queryParam("unique", unique);

            if (uris != null && !uris.isEmpty()) {
                builder.queryParam("uris", uris.toArray());
            }

            return restClient.get()
                    .uri(builder.build().toUri())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(List.class);
        } catch (Exception e) {
            log.error("Ошибка при получении статистики {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}