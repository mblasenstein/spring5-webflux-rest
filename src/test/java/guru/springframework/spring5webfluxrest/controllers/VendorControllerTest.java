package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    WebTestClient webTestClient;

    @Mock
    VendorRepository vendorRepository;

    @InjectMocks
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        Vendor vendor1 = new Vendor();
        vendor1.setFirstname("Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstname("Vendor 2");

        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(vendor1, vendor2));

        webTestClient.get()
                .uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {

        Vendor vendor3 = new Vendor();
        vendor3.setId("3");
        vendor3.setFirstname("Vendor 3");

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(vendor3));

        webTestClient.get()
                .uri("/api/v1/vendors/abc")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void create() {
        Vendor vendor4 = new Vendor();
        vendor4.setFirstname("Vendor 4");

        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(new Vendor()));

        Mono<Vendor> vendorToSaveMono = Mono.just(vendor4);

        webTestClient.post()
                .uri("/api/v1/vendors/")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        Vendor vendor5 = new Vendor();
        vendor5.setFirstname("Vendor 5");

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(vendor5);

        webTestClient.put()
                .uri("/api/v1/vendors/abc")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void patch() {
        Vendor vendor6 = new Vendor();
        vendor6.setFirstname("Vendor 6");

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(new Vendor()));

        Mono<Vendor> vendorToPatchMono = Mono.just(vendor6);

        webTestClient.patch()
                .uri("/api/v1/vendors/abc")
                .body(vendorToPatchMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}