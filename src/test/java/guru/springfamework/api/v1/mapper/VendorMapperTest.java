package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class VendorMapperTest {

    private final String NAME = "TestName";
    private final VendorMapper VENDOR_MAPPER = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = VENDOR_MAPPER.vendorToVendorDTO(vendor);

        //then
        assertThat(vendorDTO.getName(), is(NAME));

    }


    @Test
    public void vendorDTOToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = VENDOR_MAPPER.vendorDTOToVendor(vendorDTO);

        //then
        assertEquals(vendor.getName(), NAME);
    }
}