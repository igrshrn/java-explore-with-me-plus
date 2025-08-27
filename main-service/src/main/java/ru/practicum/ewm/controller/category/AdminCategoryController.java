package ru.practicum.ewm.controller.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("Запрос на создание категории name {}", categoryDto.getName());
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto categoryDto, @PathVariable Long categoryId) {
        log.info("Запрос на изменение категории id = {}", categoryId);
        return categoryService.update(categoryDto, categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long categoryId) {
        log.info("Запрос на удаление категории id = {}", categoryId);
        categoryService.delete(categoryId);
    }
}
