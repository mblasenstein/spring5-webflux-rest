package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    WebTestClient webTestClient;

    @Mock
    CategoryRepository categoryRepository;

    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        Category category1 = new Category();
        category1.setDescription("Category 1");

        Category category2 = new Category();
        category2.setDescription("Category 2");

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(category1, category2));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {

        Category category3 = new Category();
        category3.setId("3");
        category3.setDescription("Category 3");

        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category3));

        webTestClient.get()
                .uri("/api/v1/categories/abc")
                .exchange()
                .expectBody(Category.class);
    }
}