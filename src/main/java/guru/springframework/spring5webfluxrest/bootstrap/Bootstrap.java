package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final VendorRepository vendorRepository;

    public Bootstrap(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        vendorRepository.deleteAll();

        Vendor vendor1 = new Vendor();
        vendor1.setFirstname("Marty");
        vendor1.setLastname("McFly");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstname("Michael");
        vendor2.setLastname("Burnham");

        Vendor vendor3 = new Vendor();
        vendor3.setFirstname("Philippa");
        vendor3.setLastname("Georgiou");

        Integer saveCount = 0;

        vendorRepository.saveAll(Arrays.asList(vendor1, vendor2, vendor3)).subscribe();

        log.debug("Loaded " + (vendorRepository.count().block() - 1) + " vendors");
    }
}
