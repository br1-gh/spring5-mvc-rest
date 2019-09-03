package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {

    MockMvc mockMvc;

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    private final Long ID1 = 1L;
    private final String VENDOR_NAME1 = "Test Name 1";
    private final String VENDOR_URL1 =  VendorController.BASE_URL + "/" + ID1;

    private final Long ID2 = 2L;
    private final String VENDOR_NAME2 = "Test Name 2";
    private final String VENDOR_URL2 =  VendorController.BASE_URL + "/" + ID2;

    private VendorDTO vendor1;
    private VendorDTO vendor2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        //given
        vendor1 = new VendorDTO(VENDOR_NAME1, VENDOR_URL1);
        vendor2 = new VendorDTO(VENDOR_NAME2, VENDOR_URL2);
    }

    @Test
    public void getAllVendors() throws Exception {

        when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendor1, vendor2));

        //when
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

    }

    @Test
    public void getVendorById() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor2);

        //when
        mockMvc.perform(get(VendorController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(VENDOR_NAME2)))
                .andExpect(jsonPath("$.vendor_url", is(VENDOR_URL2)));

    }

    @Test
    public void createVendor() throws Exception {

        when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(vendor2);

        //when
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(VENDOR_NAME2)))
                .andExpect(jsonPath("$.vendor_url", is(VENDOR_URL2)));

    }

    @Test
    public void updateVendor() throws Exception {

        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendor1);

        //when
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(VENDOR_NAME1)))
                .andExpect(jsonPath("$.vendor_url", is(VENDOR_URL1)));

    }

    @Test
    public void patchVendor() throws Exception {

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendor1);

        //when
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(VENDOR_NAME1)))
                .andExpect(jsonPath("$.vendor_url", is(VENDOR_URL1)));

    }

    @Test
    public void deleteVendor() throws Exception {

        //when
        mockMvc.perform(delete(VendorController.BASE_URL + "/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendor(anyLong());

    }
}