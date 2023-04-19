package project.pharmacy.pharmacy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.pharmacy.pharmacy.entity.Pharmacy;
import project.pharmacy.pharmacy.repository.PharmacyRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PharmacyService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address){
        Pharmacy entity = pharmacyRepository.findById(id).orElse(null);

        if(Objects.isNull(entity)){
            log.error("[PharmacyRepository not found id: {}", id);
            return;
        }

        entity.changeAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll(){
        return pharmacyRepository.findAll();
    }
}
