package com.musinsa.minsungson;

import com.musinsa.minsungson.domain.Category;
import com.musinsa.minsungson.domain.CategoryRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
@Profile("test")
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Test");
        categoryRepository.save(new Category("ROOT"));
    }
}
