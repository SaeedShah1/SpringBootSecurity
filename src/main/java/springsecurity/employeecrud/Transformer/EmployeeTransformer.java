package springsecurity.employeecrud.Transformer;

import springsecurity.employeecrud.DTO.EmployeeDTO;
import springsecurity.employeecrud.Entity.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTransformer {

    public static EmployeeEntity toEntity(EmployeeDTO  employeeDTO){
        EmployeeEntity employeeEntity = new EmployeeEntity();

        if(employeeDTO.getId()!=null){
            employeeEntity.setId(Long.parseLong(employeeDTO.getId()));
        }

        if(employeeDTO.getProfession()!=null){
            employeeEntity.setProfession(employeeDTO.getProfession());
        }

        if(employeeDTO.getName()!=null){
            employeeEntity.setName(employeeDTO.getName());
        }


        if(employeeDTO.getStatus()!=null){
            employeeEntity.setStatus(Boolean.parseBoolean(employeeDTO.getStatus()));
        }


        return employeeEntity;
    }


    public static EmployeeDTO toDTO(EmployeeEntity  employeeEntity1){
        EmployeeDTO employeeDTO = new EmployeeDTO();

        if(employeeEntity1.getId()!=null){
            employeeDTO.setId(employeeEntity1.getId().toString());
        }


        if(employeeEntity1.getName()!=null){
            employeeDTO.setName(employeeEntity1.getName());
        }
        if(employeeEntity1.getProfession()!=null){
            employeeDTO.setProfession(employeeEntity1.getProfession());
        }


        if(employeeEntity1.getStatus()!=null){
            employeeDTO.setStatus(employeeEntity1.getStatus().toString());
        }


        return employeeDTO;
    }

    public static List<EmployeeDTO> getDTOs(List<EmployeeEntity> employeeEntities) {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeEntities.forEach(employeeEntity -> {
            employeeDTOS.add(toDTO(employeeEntity));
        });
        return employeeDTOS;
    }



}
