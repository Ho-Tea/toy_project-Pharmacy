package project.pharmacy.pharmacy.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import project.pharmacy.AbstractIntegrationContainerBaseTest
import project.pharmacy.pharmacy.entity.Pharmacy
import spock.lang.Specification


class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyRepository pharmacyRepository;

    def "PharmacyRepository save"(){
        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        
        // repository에서 의존성 주입을 못받는 이유를 모르겠따
        def pharmacy = Pharmacy.builder().pharmacyAddress(address).pharmacyName(name).latitude(latitude).longitude(longitude).build()

        when:
        def result = pharmacyRepository.save(pharmacy)


        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }
}
