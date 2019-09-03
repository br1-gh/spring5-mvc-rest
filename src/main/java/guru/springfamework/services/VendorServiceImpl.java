package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VendorServiceImpl implements VendorService {

    VendorRepository vendorRepository;

    VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());

    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {

        Vendor savedVendor =  vendorRepository.save(vendorMapper.vendorDTOToVendor(vendorDTO));
        vendorDTO.setVendorUrl(VendorController.BASE_URL + "/" + savedVendor.getId());
        return vendorDTO;

    }

    @Override
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDTO updateVendor(final Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        VendorDTO updatedVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        updatedVendorDTO.setVendorUrl(getVendorUrl(id));

        return updatedVendorDTO;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            Vendor savedVendor = vendorRepository.save(vendor);

            VendorDTO convertedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);

            convertedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

            return convertedVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private String getVendorUrl(Long id) {
        return VendorController.BASE_URL + "/" + id;
    }
}
