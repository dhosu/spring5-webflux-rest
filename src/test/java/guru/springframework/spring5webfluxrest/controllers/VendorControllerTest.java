package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

public class VendorControllerTest {

    public static final String ID = "someid";
    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @Before
    public void setUp() throws Exception {

        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {

        Vendor vendor1 = Vendor.builder().firstName("first1").lastName("last1").build();
        Vendor vendor2 = Vendor.builder().firstName("first2").lastName("last2").build();

        given(vendorRepository.findAll()).willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {

        Vendor cat = Vendor.builder().firstName("first").build();

        given(vendorRepository.findById(ID)).willReturn(Mono.just(cat));

        webTestClient.get()
                .uri("/api/v1/vendors/" + ID)
                .exchange()
                .expectBody(Vendor.class);
    }
}