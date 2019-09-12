package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadCategories();
        loadVendors();
    }
    
    private void loadCategories() {
        categoryRepository.deleteAll().subscribe();

        Category category1 = new Category();
        category1.setDescription("Time travel");

        Category category2 = new Category();
        category2.setDescription("Dystopias");

        Category category3 = new Category();
        category3.setDescription("Time travel dystoptias");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3)).subscribe();

        categoryRepository.count()
                .subscribe(num -> log.debug("Loaded " + num + " categories"));
    }

    private void loadVendors() {
        vendorRepository.deleteAll().subscribe();

        Vendor vendor1 = new Vendor();
        vendor1.setFirstname("Marty");
        vendor1.setLastname("McFly");

        Vendor vendor2 = new Vendor();
        vendor2.setFirstname("Michael");
        vendor2.setLastname("Burnham");

        Vendor vendor3 = new Vendor();
        vendor3.setFirstname("Philippa");
        vendor3.setLastname("Georgiou");

        vendorRepository.saveAll(Arrays.asList(vendor1, vendor2, vendor3)).subscribe();

        vendorRepository.count()
                .subscribe(num -> log.debug("Loaded " + num + " vendors"));
    }
}
