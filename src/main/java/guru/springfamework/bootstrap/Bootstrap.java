package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 9/24/17.
 */
@Component
public class Bootstrap implements CommandLineRunner{

    private CategoryRepository categoryRespository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRespository, CustomerRepository customerRepository) {
        this.categoryRespository = categoryRespository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRespository.save(fruits);
        categoryRespository.save(dried);
        categoryRespository.save(fresh);
        categoryRespository.save(exotic);
        categoryRespository.save(nuts);

        System.out.println("Fruits Loaded = " + categoryRespository.count() );

        final String CUSTOMER_URL = "/shop/customer/";

        Customer customer1 = new Customer();
        customer1.setFirstname("John");
        customer1.setLastname("Doe");
        Customer savedCustomer1 = customerRepository.save(customer1);
        savedCustomer1.setCustomer_url(CUSTOMER_URL + savedCustomer1.getId());
        customerRepository.save(savedCustomer1);

        Customer customer2 = new Customer();
        customer2.setFirstname("Jenny");
        customer2.setLastname("Dillon");
        Customer savedCustomer2 = customerRepository.save(customer2);
        savedCustomer2.setCustomer_url(CUSTOMER_URL + savedCustomer2.getId());
        customerRepository.save(savedCustomer2);

        System.out.println("Customers Loaded = " + customerRepository.count() );

    }
}
