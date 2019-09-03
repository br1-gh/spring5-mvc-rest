package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceTest {

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    public void getAllVendors() {
        //given
        Vendor vendor1 = new Vendor();
        vendor1.setName("Test Name 1");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Test Name 2");

        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        //when
        List<VendorDTO> vendors = vendorService.getAllVendors();

        //then
        assertThat(vendors, hasSize(2));

    }

    @Test
    public void getVendorById() {
        final Long ID = 1L;
        final String VENDOR_NAME = "Test Name 1";

        //given
        Vendor vendor1 = new Vendor();
        vendor1.setId(ID);
        vendor1.setName(VENDOR_NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(vendor1));

        //when
        VendorDTO vendor = vendorService.getVendorById(ID);

        //then
        assertEquals(vendor.getName(), VENDOR_NAME);
    }

    @Test
    public void createVendor() {
        //given
        final Long ID = 1L;
        final String VENDOR_NAME = "Test Name 1";
        final String VENDOR_URL =  "/api/v1/vendors/" + ID;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        Vendor vendor1 = new Vendor();
        vendor1.setId(ID);
        vendor1.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor1);

        //when
        VendorDTO vendor = vendorService.createVendor(vendorDTO);

        //then
        assertEquals(vendor.getName(), VENDOR_NAME);
        assertEquals(vendor.getVendorUrl(), VENDOR_URL);

    }

    @Test
    public void deleteVendor() {
        //given
        Long id = 1L;

        //when
        vendorService.deleteVendor(id);

        //then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void updateVendor() {
        //given
        final Long ID = 1L;
        final String VENDOR_NAME = "Test Name 1";
        final String VENDOR_URL =  "/api/v1/vendors/" + ID;

        VendorDTO vendorDTO = new VendorDTO(VENDOR_NAME, VENDOR_URL);

        final String VENDOR_NAME_MODIFIED = "Test Name 55";

        Vendor modifiedVendor = new Vendor();
        modifiedVendor.setId(ID);
        modifiedVendor.setName(VENDOR_NAME_MODIFIED);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(modifiedVendor);

        //when
        VendorDTO updatedVendorDTO = vendorService.updateVendor(ID, vendorDTO);

        //then
        assertEquals(updatedVendorDTO.getName(), VENDOR_NAME_MODIFIED);
        assertEquals(updatedVendorDTO.getVendorUrl(), VENDOR_URL);
        verify(vendorRepository, times(1)).save(any(Vendor.class));

    }

    @Test
    public void patchVendor() {
        //given
        final Long ID = 1L;
        final String VENDOR_NAME = "Test Name 1";
        final String VENDOR_URL =  "/api/v1/vendors/" + ID;

        final String VENDOR_NAME_MODIFIED = "Test Name 55";

        VendorDTO vendorDTO = new VendorDTO(VENDOR_NAME_MODIFIED, VENDOR_URL);

        Vendor originalVendor = new Vendor();
        originalVendor.setId(ID);
        originalVendor.setName(VENDOR_NAME);

        Vendor modifiedVendor = new Vendor();
        modifiedVendor.setId(ID);
        modifiedVendor.setName(VENDOR_NAME_MODIFIED);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(originalVendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(modifiedVendor);

        //when
        VendorDTO modifiedVendorDTO = vendorService.patchVendor(ID, vendorDTO);

        //then
        verify(vendorRepository, times(1)).findById(anyLong());
        verify(vendorRepository, times(1)).save(any(Vendor.class));
        assertEquals(modifiedVendorDTO.getName(), VENDOR_NAME_MODIFIED);
        assertEquals(modifiedVendorDTO.getVendorUrl(), VENDOR_URL);


    }

}