package pl.zajavka.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zajavka.infrastructure.database.repository.EmployeeRepository;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeesController {
    private EmployeeRepository employeeRepository;

}
