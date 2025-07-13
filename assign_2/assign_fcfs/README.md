# 선착순 이벤트 시스템 (FCFS - First Come First Served)

## 📋 프로젝트 개요
선착순 이벤트 참여를 처리하는 REST API 서버입니다. 
1만명 한정으로 포인트를 지급하는 선착순 이벤트를 관리하며, 동시성과 중복 참여를 방지합니다.

## 🛠 기술 스택
- **Language**: Kotlin
- **Framework**: Spring Boot 3.5.3
- **Database**: H2 Database
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle
- **Java Version**: 21

## 🚀 설치 및 실행 방법

### 빌드 및 실행
```bash
# Gradle Wrapper를 사용한 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

## 🧪 테스트
```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests EventFCFSControllerTest
```

### 3. 서버 확인
- 애플리케이션: http://localhost:8080
- H2 콘솔: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:~/os-assign;AUTO_SERVER=TRUE`
  - Username: `sa`
  - Password: (공백)

## 📖 API 명세

### 이벤트 참여 API

**Endpoint**: `POST /api/event/join`

**Request Header**:
```
Content-Type: application/json
```

**Request Body**:
```json
{
    "userId": "user123"
}
```

**Response Format**:
```json
{
    "code": 200,
    "message": "이벤트 참여 성공",
    "data": {
        "userId": "user123",
        "rank": 1,
        "point": 100000,
        "createdAt": "2025-07-14T10:30:45.123"
    }
}
```

### 응답 예시

#### 성공 (1-100등)
```json
{
    "code": 200,
    "message": "이벤트 참여 성공",
    "data": {
        "userId": "user001",
        "rank": 50,
        "point": 100000,
        "createdAt": "2025-07-14T10:30:45.123"
    }
}
```

#### 성공 (101-2000등)
```json
{
    "code": 200,
    "message": "이벤트 참여 성공",
    "data": {
        "userId": "user500",
        "rank": 500,
        "point": 50000,
        "createdAt": "2025-07-14T10:30:45.456"
    }
}
```

#### 실패 - 중복 참여
```json
{
    "code": 409,
    "message": "이미 이벤트에 참여하셨습니다. 중복 참여는 불가능합니다."
}
```

#### 실패 - 인원 초과
```json
{
    "code": 410,
    "message": "이벤트 참여 인원이 초과되었습니다."
}
```

## 🎯 포인트 지급 체계

| 순위 | 지급 포인트 |
|------|------------|
| 1 ~ 100등 | 100,000 포인트 |
| 101 ~ 2,000등 | 50,000 포인트 |
| 2,001 ~ 5,000등 | 20,000 포인트 |
| 5,001 ~ 10,000등 | 10,000 포인트 |
| 10,001등 이후 | 참여 불가 |

## ⚙️ 처리 로직 설명

### 선착순 판별 방식
1. **자동 증가 ID 활용**: 데이터베이스의 `IDENTITY` 전략을 사용하여 참여 순서를 자동으로 결정
2. **순번 = ID**: 엔티티의 `id` 필드가 곧 참여 순번이 되어 1번부터 순차적으로 할당
3. **원자적 처리**: 데이터베이스 수준에서 자동 증가 ID를 통해 원자적으로 순번 결정

### 동시성 처리 방식

#### 1. 트랜잭션 격리
- `@Transactional` 어노테이션으로 각 참여 요청을 독립적인 트랜잭션으로 처리
- 데이터베이스 레벨에서 동시성 제어 보장

#### 2. 낙관적 락 (Optimistic Locking)
```kotlin
@Version
@Column(name = "version", nullable = false)
val version: Int = 0
```
- JPA의 `@Version` 어노테이션으로 낙관적 락 구현
- 동시 수정 시도 시 `OptimisticLockException` 발생으로 데이터 무결성 보장

### 중복 참여 방지
1. **사전 검증**: 사용자 ID로 기존 참여 여부 확인
```kotlin
if (eventFCFSRepository.existsByUserId(userId)) {
    throw DuplicateParticipationException("이미 이벤트에 참여하셨습니다.")
}
```

2. **데이터베이스 제약**: `user_id` 컬럼에 유니크 제약 조건 적용 가능

### 인원 제한 처리
```kotlin
companion object {
    private const val MAX_PARTICIPANTS = 10000L
}

if (rank == null || rank > MAX_PARTICIPANTS) {
    throw EventClosedException("이벤트 참여 인원이 초과되었습니다.")
}
```

## 결론
저는 이 API 의 핵심은 PK 값을 이용한 순차적인 참여 순번 부여라고 생각합니다. 

선착순 이벤트 시스템과 관련된 자료들을 찾아볼 때 Redis, Kafka 등을 사용해서 동시성을 처리하는 경우가 많습니다. <br> 
레디스, 카프카등이 동시성을 처리할 때 은탄환처럼 사용할 수는 있겠지만,  이 프로젝트에서는 안내해준 요구사항에 인메모리 or SQLite/로컬DB 으로 되어있어 최대한 스프링의 기능을 활용하여 동시성을 처리하고자 했습니다. <br>

해당 과제를 진행하는데 실제로는 유저 10,000명 이상에 대한 데이터가 필요한 전제가 있어야하지만, <br>
실제 10,000명을 먼저 만드는것은 매우 공수가 많이 드는 일이라 직접 만들지는 못하고 유저ID 를 받아서 참여하는 방식으로 구현했습니다. <br>

해당 과제의 결과를 떠나서, 실무와 같은 과제를 진행하게 해준 **우리의 이야기** 담당님께 감사드립니다. <br>