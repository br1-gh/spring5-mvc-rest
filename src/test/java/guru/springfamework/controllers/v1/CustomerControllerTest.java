package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private final Long ID1 = 1l;
    private final Long ID2 = 2l;
    private final String NAME1 = "John";
    private final String LAST_NAME1 = "Doe";
    private final String NAME2 = "Jenny";
    private final String LAST_NAME2 = "Dillon";
    private final String CUSTOMER_URL = "/shop/customer/";

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID1);
        customer1.setFirstname(NAME1);
        customer1.setLastname(LAST_NAME1);
        customer1.setCustomer_url(CUSTOMER_URL + ID1);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        //when
        mockMvc.perform(get("/api/v1/customers/" + ID1)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()) //then
                    .andExpect(jsonPath("$.firstname", is(NAME1)))
                    .andExpect(jsonPath("$.lastname", is(LAST_NAME1)))
                    .andExpect(jsonPath("$.customer_url", is(CUSTOMER_URL + ID1)));

    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID1);
        customer1.setFirstname(NAME1);
        customer1.setLastname(LAST_NAME1);
        customer1.setCustomer_url(CUSTOMER_URL + ID1);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(ID2);
        customer2.setLastname(LAST_NAME2);
        customer2.setFirstname(NAME2);
        customer2.setCustomer_url(CUSTOMER_URL + ID2);

        when(customerService.getCustomers()).thenReturn(Arrays.asList(customer1, customer2));

        //when
        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //then
                .andExpect(jsonPath("$.customers", hasSize(2)))
                .andExpect(jsonPath("$.customers[0].firstname", is(NAME1)))
                .andExpect(jsonPath("$.customers[0].lastname", is(LAST_NAME1)))
                .andExpect(jsonPath("$.customers[0].customer_url", is(CUSTOMER_URL + ID1)))
                .andExpect(jsonPath("$.customers[1].firstname", is(NAME2)))
                .andExpect(jsonPath("$.customers[1].lastname", is(LAST_NAME2)))
                .andExpect(jsonPath("$.customers[1].customer_url", is(CUSTOMER_URL + ID2)));

    }
}