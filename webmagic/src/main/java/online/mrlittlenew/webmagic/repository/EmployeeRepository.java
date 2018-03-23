package online.mrlittlenew.webmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.mrlittlenew.webmagic.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findById(Long id);
}