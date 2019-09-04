package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jt on 10/6/17.
 */
@Api("Api to handle Vendors")
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "This operation will return a list of Vendors.",
            notes = "In the current version views all vendor names",
            produces = "APPLICATION/JSON")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getVendorList(){
        return vendorService.getAllVendors();
    }

    @ApiOperation(value = "This operation will return a Vendor, provided its id value",
            notes = "In the current version views the vendor name",
            produces = "APPLICATION/JSON")
    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "This operation will create a Vendor, and return the created Vendor back to the Client",
            notes = "In the current version creates the vendor name",
            produces = "APPLICATION/JSON")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "This operation will update a Vendor, provided its id value and modifications," +
            "and return the created Vendor back to the Client",
            notes = "In the current version updates the vendor name",
            produces = "APPLICATION/JSON")
    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "This operation will update a Vendor, provided its id value and modifications," +
            "and return the created Vendor back to the Client. The modifications must not contain empty values.",
            notes = "In the current version patches the vendor name",
            produces = "APPLICATION/JSON")
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(id, vendorDTO);
    }

    @ApiOperation(value = "This operation will delete a Vendor, provided its id value",
            notes = "In the current version deletes the vendor")
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }
}
