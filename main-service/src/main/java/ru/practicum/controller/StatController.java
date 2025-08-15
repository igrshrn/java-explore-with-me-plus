package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.practicum.client.StatClient;
import ru.practicum.ewm.dto.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class StatController {

    private final StatClient statClient;

    @ResponseBody
    @GetMapping("/hit")
    public ResponseEntity<Object> saveHit(@Valid EndpointHit dto) {
        log.info("main-service handled: StatController.saveHit endpoint - {}", dto.getUri());
        return statClient.saveHit(dto);
    }

    @ResponseBody
    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @PastOrPresent
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                           @RequestParam @PastOrPresent
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("main-service handled: StatController.getStats from {} to {}", start, end);
        return statClient.getStats(start, end, uris, unique);
    }
}
