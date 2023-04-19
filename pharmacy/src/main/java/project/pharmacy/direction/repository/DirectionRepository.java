package project.pharmacy.direction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.pharmacy.direction.entity.Direction;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
