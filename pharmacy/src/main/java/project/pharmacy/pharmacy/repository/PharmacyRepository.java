package project.pharmacy.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.pharmacy.pharmacy.entity.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
