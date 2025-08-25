package ru.practicum.ewm.service.category;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(NewCategoryDto categoryDto);

    CategoryDto update(NewCategoryDto categoryDto, Long categoryId);

    void delete(Long categoryId);

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long categoryId);
}
