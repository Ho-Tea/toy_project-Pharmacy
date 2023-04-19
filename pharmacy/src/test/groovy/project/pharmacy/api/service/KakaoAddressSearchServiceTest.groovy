package project.pharmacy.api.service

import org.springframework.beans.factory.annotation.Autowired
import project.pharmacy.AbstractIntegrationContainerBaseTest
import spock.lang.Specification

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파리미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다"(){
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }
}
