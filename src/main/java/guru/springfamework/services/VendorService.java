package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorService {


    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    VendorDTO createVendor(VendorDTO vendorDTO);
    void deleteVendor(Long id);
    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

}

