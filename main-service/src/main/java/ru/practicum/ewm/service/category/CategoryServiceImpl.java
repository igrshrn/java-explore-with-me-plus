package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.dto.category.NewCategoryDto;
import ru.practicum.ewm.entity.category.Category;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.repository.category.CategoryRepository;
import ru.practicum.ewm.repository.event.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(NewCategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ConflictException("Категория %s уже существует".formatted(categoryDto.getName()));
        }
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(NewCategoryDto categoryDto, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Категория id = %d не найдена".formatted(categoryId)));
        if (!existingCategory.getName().equals(categoryDto.getName())) {
            categoryRepository.findByName(categoryDto.getName()).ifPresent(c -> {
                throw new ConflictException("Категория  %s уже существует".formatted(categoryDto.getName()));
            });
        }
        existingCategory.setName(categoryDto.getName());
        return categoryMapper.toCategoryDto(categoryRepository.save(existingCategory));
    }

    @Override
    public void delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Категория id = %d не найдена".formatted(categoryId));
        }
        if (eventRepository.existsByCategoryId(categoryId)) {
            throw new ConflictException("Попытка удалить категорию с привязанными событиями");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return categories.stream()
                .map(categoryMapper::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Категория id = %d не найдена".formatted(categoryId)));
        return categoryMapper.toCategoryDto(category);
    }
}
