package project.pharmacy

import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification{  //테스트를 위한 상속
    static final GenericContainer MY_REDIS_CONTAINER    //redis의 경우에는 따로 추가해주어야 한다

    static {
        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6").withExposedPorts(6379)

        MY_REDIS_CONTAINER.start();

        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379).toString())//redis를 스프링에게 알려주어야 한다

    }
}
