package pl.zajavka.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zajavka.infrastructure.database.entity.EmployeeEntity;
import pl.zajavka.infrastructure.database.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeesController {
    private EmployeeRepository employeeRepository;

    @PostMapping("/add")
    public String addEmployee(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "salary") String salary
    ) {
        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .name(name)
                .surname(surname)
                .salary(new BigDecimal(salary))
                .build();
        employeeRepository.save(newEmployee);
        return "redirect:/employees";
    }

    @GetMapping
    public String employeesList(Model model) {
        List<EmployeeEntity> allEmployees = employeeRepository.findAll();
        model.addAttribute("employees", allEmployees);
        model.addAttribute("updateEmployeeDTO", new UpdateEmployeeDTO());
        return "employees";
    }

    @GetMapping("/show/{employeeId}")
    public String showEmployeeDetails(@PathVariable Integer employeeId, Model model) {
        EmployeeEntity employeeDetails = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]", employeeId)));
        model.addAttribute("employee", employeeDetails);
        return "employeeDetails";
    }

    @PutMapping("/update")
    public String updateEmployee(
            @ModelAttribute("updateEmployeeDTO") UpdateEmployeeDTO updateEmployeeDTO
    ) {
        EmployeeEntity existingEmployee
                = employeeRepository.findById(updateEmployeeDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("EmployeeEntity not found, employeeId: [%s]",
                                updateEmployeeDTO.getEmployeeId())));

        existingEmployee.setName(updateEmployeeDTO.getName());
        existingEmployee.setSurname(updateEmployeeDTO.getSurname());
        existingEmployee.setSalary(updateEmployeeDTO.getSalary());
        employeeRepository.save(existingEmployee);
        return "redirect:/employees";
    }

    @DeleteMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId) {
        employeeRepository.deleteById(employeeId);
        return "redirect:/employees";
    }


}
