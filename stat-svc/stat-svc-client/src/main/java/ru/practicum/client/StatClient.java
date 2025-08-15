package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.dto.EndpointHit;
import ru.practicum.ewm.exception.ApiError;
import ru.practicum.utils.ResponseGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class StatClient extends ResponseGenerator {

    private final RestClient restClient;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatClient(@Value("${stat-svc-service.url}") String statServiceUrl) {
        restClient = RestClient.builder()
                .baseUrl(statServiceUrl)
                .build();
    }

    public ResponseEntity<Object> saveHit(EndpointHit hit) {
        try {
            log.info("Сохранение информации о запросе {}", hit);
            return makeResult(restClient.post()
                    .uri("/hit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(hit)
                    .retrieve()
                    .body(EndpointHit.class), HttpStatus.CREATED);
        } catch (Exception e) {
            String msg = "Oшибка при сохранении информации";
            log.error(msg + " {}", e.getMessage(), e);
            return makeResult(ApiError.builder()
                    .code(500)
                    .error(msg)
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getStats(LocalDateTime start,
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

            return makeResult(restClient.get()
                    .uri(builder.build().toUri())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {}), HttpStatus.OK);
        } catch (Exception e) {
            String msg = "Oшибка при получении статистики";
            log.error(msg + " {}", e.getMessage(), e);
            return makeResult(ApiError.builder()
                            .code(500)
                            .error(msg),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}