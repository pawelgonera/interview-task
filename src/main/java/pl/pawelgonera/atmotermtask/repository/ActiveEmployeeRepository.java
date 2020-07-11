package pl.pawelgonera.atmotermtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pawelgonera.atmotermtask.entity.ActiveEmployee;

@Repository
public interface ActiveEmployeeRepository extends JpaRepository<ActiveEmployee, Long> {
}
