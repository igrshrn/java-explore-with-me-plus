package ru.practicum.ewm.repository.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.entity.category.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
