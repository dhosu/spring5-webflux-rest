package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class CategoryControllerTest {

    public static final String ID = "someid";
    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception {

        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);

        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {

        Category cat1 = Category.builder().description("Cat1").build();
        Category cat2 = Category.builder().description("Cat2").build();

        given(categoryRepository.findAll()).willReturn(Flux.just(cat1, cat2));

        webTestClient.get()
            .uri("/api/v1/categories")
            .exchange()
            .expectBodyList(Category.class)
            .hasSize(2);
    }

    @Test
    public void getById() {

        Category cat = Category.builder().description("Cat1").build();

        given(categoryRepository.findById(ID)).willReturn(Mono.just(cat));

        webTestClient.get()
                .uri("/api/v1/categories/" + ID)
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategory() {

        given(categoryRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some Cat").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus().isCreated();
    }
}