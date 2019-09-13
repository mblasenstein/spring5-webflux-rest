package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors/")
    @ResponseStatus(HttpStatus.OK)
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping("/api/v1/vendors/")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {

        return vendorRepository.findById(id)
                .map(entityBeingPatched -> {
                    if (vendor.getFirstname() != null) {
                        entityBeingPatched.setFirstname(vendor.getFirstname());
                    }
                    if (vendor.getLastname() != null) {
                        entityBeingPatched.setLastname(vendor.getLastname());
                    }
                    return entityBeingPatched;
                })
                .flatMap(patchedEntity ->
                        vendorRepository.save(patchedEntity)
                                .map(savedEntity -> savedEntity));
    }
}
