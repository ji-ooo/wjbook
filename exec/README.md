# 배포 문서

## 버전 정보
|             | version                |
|-------------|------------------------|
| Jdk         | Eclipse Temurin 21.0.4 |
| Spring Boot | 3.3.1                  |
| React       | 18.3.1                 |
| MySQL       | 8.0.38                 |

## 환경 변수

### 백엔드 서버

- `SPRING_PROFILES_ACTIVE`

  Spring의 Profile을 설정하는 환경 변수로 배포 시에는 prod로 설정합니다.

## Properties

### 백엔드 서버

application-auth.properties

```
jwt.secret.key={JWT secret key}
jwt.expiration.time={JWT 만료시간}

spring.mail.host={smtp 서버 주소}
spring.mail.port={smtp 서버 포트}
spring.mail.username={메일 전송용 계정 이름}
spring.mail.password={메일 전송용 계정 패스워드}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true

naver-client-key={Naver API 키}
naver-client-secret={Naver API secret 키}
```

application-prod.properties

```
# [jpa property]
spring.jpa.properties.hibernate.show_sql=false
spring.jpa.properties.hibernate.generate-ddl=false
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.properties.hibernate.physical_naming_strategy=org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# [datasource property]
database.url={master DB url}
database.username={master DB username}
database.password={master DB password
database.slave.slave1.name=slave
database.slave.slave1.url={replica1 DB url}
database.slave.slave1.username={replica1 DB username}
database.slave.slave1.password={replica1 DB password}

# [redis property]
spring.data.redis.host={redis url}
spring.data.redis.port={redis port}
```

## 배포 특이사항

### 백엔드 서버

- 스프링 Profile을 prod로 설정하면 RabbitMQ, MySQL master slave, Redis가 구성되어야 합니다.

### 프론트엔드

- React를 빌드하여 Nginx에 배포합니다.