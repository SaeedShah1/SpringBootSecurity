package springsecurity.employeecrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsecurity.employeecrud.DTO.EmployeeDTO;
import springsecurity.employeecrud.DTO.StatusDTO;
import springsecurity.employeecrud.Entity.EmployeeEntity;
import springsecurity.employeecrud.Service.EmployeeService;
import springsecurity.employeecrud.Transformer.EmployeeTransformer;

import java.util.List;
/**
 *
 * @author Saeed Shah
 */


@RestController
@RequestMapping(path = "/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping(value = "/create")
    public ResponseEntity<StatusDTO> create(@ModelAttribute EmployeeDTO employeeDTO) {
        try {

            EmployeeEntity employeeEntity = EmployeeTransformer.toEntity(employeeDTO);
            employeeEntity.setStatus(true);
            employeeService.create(employeeEntity);


            return new ResponseEntity(new StatusDTO(1, " Added Successfully", EmployeeTransformer.toDTO(employeeEntity)), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception occurred! " + e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/update")
    public ResponseEntity<StatusDTO> update(@ModelAttribute EmployeeDTO employeeDTO) {

        try {

            EmployeeEntity employeeEntity = employeeService.findById(Long.parseLong(employeeDTO.getId()));
            if (employeeEntity == null) {
                return new ResponseEntity(new StatusDTO(0, "Employee not found"), HttpStatus.NOT_FOUND);
            }
            EmployeeEntity employeeEntity1 = EmployeeTransformer.toEntity(employeeDTO);

            employeeEntity1.setStatus(true);
            employeeService.create(employeeEntity1);

            return new ResponseEntity(new StatusDTO(1, "Updated"), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/delete")
    public ResponseEntity<StatusDTO> delete(@PathVariable Long id) {

        try {
            EmployeeEntity employeeEntity = employeeService.findById(id);
            if (employeeEntity != null) {
                employeeService.delete(employeeEntity);
                return new ResponseEntity(new StatusDTO(1, "Deleted"), HttpStatus.OK);
            } else {
                return new ResponseEntity<StatusDTO>(new StatusDTO(0, " Details not found!"), HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<StatusDTO> getById(@PathVariable Long id) {
        try {
            EmployeeEntity employeeEntity = employeeService.findById(id);

            if (employeeEntity != null) {
                EmployeeDTO employeeDTO = EmployeeTransformer.toDTO(employeeEntity);
                return new ResponseEntity(employeeDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity(" Employee not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StatusDTO(0, "Exception Occured"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/getAll")
    public List<EmployeeDTO> getAll() {
        List<EmployeeEntity> employeeEntities = employeeService.findAll();
        return EmployeeTransformer.getDTOs(employeeEntities);
    }


}
