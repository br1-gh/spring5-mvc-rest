package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    private static final Long ID = 1l;
    private static final String FIRSTNAME = "BRUNO";
    private static final String LASTNAME = "COLUCCI";
    private static final String CUSTOMER_URL = "/shop/customer/" + ID;
    
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setCustomer_url(CUSTOMER_URL);

        //when
        CustomerDTO categoryDTO = customerMapper.customerToCustomerDTO(customer);

        //then
        assertEquals(categoryDTO.getId(), ID);
        assertEquals(categoryDTO.getFirstname(), FIRSTNAME);
        assertEquals(categoryDTO.getLastname(), LASTNAME);
        assertEquals(categoryDTO.getCustomer_url(), CUSTOMER_URL);
    }
}