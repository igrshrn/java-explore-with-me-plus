package ru.practicum.ewm.controller.user;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.user.NewUserRequest;
import ru.practicum.ewm.dto.user.UserDto;
import ru.practicum.ewm.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody NewUserRequest request) {
        log.info("Получен HTTP-запрос на создание пользователя: {}", request);
        UserDto createdUserDto = userService.create(request);
        log.info("Успешно обработан HTTP-запрос на создание пользователя: {}", createdUserDto);
        return createdUserDto;
    }

    @GetMapping
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids,
                                @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Получен HTTP-запрос на получение списка пользователей c ids: {}", ids);
        return userService.getAll(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        log.info("Получен HTTP-запрос на удаление пользователя с id: {}", userId);
        userService.delete(userId);
        log.info("Пользователь с id: {} успешно удален", userId);
    }
}
