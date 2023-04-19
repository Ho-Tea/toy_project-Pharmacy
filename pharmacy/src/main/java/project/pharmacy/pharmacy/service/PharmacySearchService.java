package project.pharmacy.pharmacy.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.pharmacy.pharmacy.dto.PharmacyDto;
import project.pharmacy.pharmacy.entity.Pharmacy;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyService pharmacyService;

    public List<PharmacyDto> searchPharmacyDtoList() {

        //redis

        //db
        return pharmacyService.findAll()
                .stream()
                .map(entity -> convertToPharmacyDto(entity))
                .collect(Collectors.toList());
    }

    private PharmacyDto convertToPharmacyDto(Pharmacy pharmacy){
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
