# 약국 추천 서비스
- ### 개발환경 구성
  - JDK 17
  - Spring Boot 3.0.5
  - Gradle Wrapper
  - Lombok
  - Spring Configuration Processor
    - `application.properties`, `application.yml`의 자동완성을 지원
  - Spring Web
  - Spring Data Jpa
  - Maria DB
  - Spock
    - `Groovy` 언어를 이용하여 테스트 코드를 작성할 수 있는 프레임워크이며<br> JUnit 과 비교하여 코드를 더 간결하게 작성 가능하다.
    - `Groovy` 언어는 동적 타입 프로그래밍 언어로 JVM 위에서 동작하며 Java문법과 유사하다.
    - `Groovy`클래스로 생성하고, `Specification`클래스를 상속받는다
  - Handlebars
    - 자바 템플릿 엔진
  

  - ### 도커
    - `컨테이너`를 사용하여 응용프로그램을 더 쉽게 만들고 배포하고 실행할 수 있도록 설계된 도구
    - `일반 컨테이너 개념에서 물건을 손쉽게 운송해주는 것`처럼 <br> 어플리케이션 환경에 구애받지 않고 손쉽게 배포관리가 가능
    - 도커는 **서버마다 동일한 환경**을 구성해준다
    - 동일한 환경을 구성하기 때문에 `auto scaling`에 유리하다
      - `auto scaling` : 서버를 늘리고 줄이고를 자동으로 해준다
    - <img src="image/docker.png">
      - 도커는 하이퍼 바이저 구조를 토대로 등장
      - VM보다 훨씬 가볍게 동작 -> 성능에 유리
    - **도커 이미지**
      - 코드, 런타임, 시스템 도구, 시스템 라이브러리 및 설정과 같은 응용프로그램을<br> 실행 하는데 필요한 모든것을 포함한 패키지
      - **컨테이너란 도커 이미지를 독립된 공간에서 실행 할 수 있게 해주는 기술**

    - **도커 파일**
      - Dockerfile이란 도커 이미지를 구성하기 위해 있어야 할 패키지, 의존성, 소스코드 등을<br> 하나의 file로 기록하여 이미지화 시킬 명령 파일
      - 즉, 이미지는 컨테이너를 실행하기 위한 모든 정보를 가지고 있기 때문에 <br>더 이상 새로운 서버가 추가되면 의존성 파일을 컴파일하고 이것저것 설치할 필요가 없다

      - <img src ="image/dockerfile.png">

      - <img src ="image/docker2.png">

  - ### 도커 컴포즈
    - 멀티 컨테이너 도커 어플리케이션을 정의하고 실행하는 도구
    - Application, Database, Redis, Nginx 등 각 독립적인 컨테이너로 관리한다고 했을 때<br> **다중 컨테이너 라이프 사이클을 파일하나(`docker-compose.yml`) 작성으로 관리 가능**
    - 여러개의 도커 컨테이너로 부터 이루어진 서비스를 구축 및 네트워크 연결, 실행 순서를 자동으로 관리
    - <img src ="image/dockerfile2.png">
    - `Docker compose`에서 환경 변수 정보들을 분리하여 별도의 파일로 구성할때 <br> 간편한 방법은 Compose파일이 위치한 경로에 .env파일을 구성
    - 작성한 .env 파일은 별다른 설정 없이 Docker Compose에 바로 반영
    - 비밀정보가 코드에 노출되지 않도록 .gitignore에 .env파일 추가



- ### Spring Profile
  - 복수의 WAS를 사용하는 상황에서 같은 Spring Application을 사용하지만 **각 Server에 맞게 Context를 설정해야 하는 경우가 존재**
  - 환경 별(local, develop, production)
    - ``` yml
      spring:
        profiles:
          active: local # default
          group:
            local:
              - common
            prod:
              - common


        ---

        spring:
          config:
            activate:
              on-profile: common

        kakao:
          rest:
            api:
              key: ${KAKAO_REST_API_KEY}


        ---
        spring:
          config:
            activate:
              on-profile: local
          datasource:
            driver-class-name: org.mariadb.jdbc.Driver
            url: jdbc:mariadb://localhost:3306/pharmacy-recommendation
            username: ${SPRING_DATASOURCE_USERNAME}   # 디비 접속과 관련된 부분이니 env파일에 따로 저장하고 환경변수로 지정하여 주입받는다
            password: ${SPRING_DATASOURCE_PASSWORD}
          jpa:
            hibernate:
              ddl-auto: create
            show-sql: true
          data:
            redis:
              host: localhost
              port: 6379

          ---
          spring:
            config:
              activate:
                on-profile: prod


          ---

      ```

- ### 추천 서비스 기능 구현
  - ### Testcontainers 
    - JPA를 이용하여 CRUD **테스트 코드**를 작성할 때 어떤 DB환경이 좋을까?
      1. 운영환경과 유사한 스펙의 DB사용하기<br> (다른사용자가 CRUD하는 상황이면 Data가 바뀔 수 있다)
      2. 인메모리 DB(`H2`)사용하기
      3. Docker 이용하기<br> (DB가 담긴 Container가 올라가는 시점과 테스트가 실행되는 시점을 맞추는 작업이 추가로 필요하게 된다)
      4. **TestContainer를 이용하기**
      - TestContainer는 운영환경과 유사한 DB스펙으로 독립적인 환경에서 테스트코드를 작성하여 테스트 가능
      - 즉, 테스트 코드가 실행 될 때 자동으로 도커 컨테이너를 실행하여 테스트하고,<br> 테스트가 끝나면 자동으로 컨테이너를 종료 및 정리한다
      - 테스트컨테이너를 실행하기 위해서는 도커가 실행되어 있어야 한다.

      ``` groovy
      // groovy(Spock), Testcontainer
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
      ```
      ``` yml
      # Test (application.yml)
      # jdbc: 이후 tc:를 추가하면, host와 port, database name은 무시되며, 
      # testcontainers가 제공해주는 드라이버가 자동으로 처리한다
      
      spring:
        datasource:
          driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
          url: jdbc:tc:mariadb:10:///pharmacy-recommendation
        jpa:
          hibernate:
            ddl-auto: create
          show-sql: true

      kakao:
        rest:
          api:
            key: ${KAKAO_REST_API_KEY}
      ```
