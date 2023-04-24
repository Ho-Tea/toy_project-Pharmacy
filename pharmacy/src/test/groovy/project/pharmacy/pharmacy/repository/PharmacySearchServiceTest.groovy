package project.pharmacy.pharmacy.repository

import org.testcontainers.shaded.com.google.common.collect.Lists
import project.pharmacy.pharmacy.cache.PharmacyRedisTemplateService
import project.pharmacy.pharmacy.dto.PharmacyDto
import project.pharmacy.pharmacy.entity.Pharmacy
import project.pharmacy.pharmacy.service.PharmacySearchService
import project.pharmacy.pharmacy.service.PharmacyService
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification{
    private PharmacySearchService pharmacySearchService;

    private PharmacyService pharmacyService = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<Pharmacy> pharmacyList

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyService, pharmacyRedisTemplateService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

//    def "searchPharmacyDtoList convert pharmacyList to pharmacyDtoList"() {
//        when:
//        pharmacyService.findAll() >> pharmacyList
//        def result = pharmacySearchService.searchPharmacyDtoList()
//
//        then:
//        result.size() == 2
//        result.get(0).getId() == 1
//        result.get(0).getPharmacyName() == "호수온누리약국"
//        result.get(1).getId() == 2
//        result.get(1).getPharmacyName() == "돌곶이온누리약국"
//    }
//
//    def "searchPharmacyDtoList return empty list if pharmacyList is empty"() {
//        when:
//        pharmacyService.findAll() >> []
//        def result = pharmacySearchService.searchPharmacyDtoList()
//
//        then:
//        result.size() == 0
//        result.empty
//    }

    def "레디스 장애시 DB를 이용 하여 약국 데이터 조회"() {

        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyService.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
    }
}
