package com.javamsdt.actuator.repos;

import com.javamsdt.actuator.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository  extends CrudRepository<Student, Long> {
}
