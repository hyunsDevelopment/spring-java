# spring-java
spring + java repository

1. batch-sample<br>
  Spring Batch의 기본 구조와 실행 흐름을 이해하기 위한 샘플 프로젝트입니다.
  Job → Step → Tasklet 구조를 바탕으로 배치 처리 과정을 구현했습니다.
  간단한 CSV 파일 읽기, 데이터 가공, 콘솔 출력 또는 DB 저장 등의 흐름을 포함합니다.
  JobLauncher를 이용한 수동 실행 및 @Scheduled 기반의 정기 실행 방식도 포함되어 있어 실무 배치 기초를 테스트하기에 적합합니다.
  Spring Batch 메타데이터 테이블 자동 생성 및 H2/MySQL 연동 예제도 함께 제공합니다.

3. kiwoom-app

  전통적인 Spring MVC 구조를 기반으로 하는 모놀리식 서버 애플리케이션의 초기 세팅 프로젝트입니다.
  Controller → Service → Repository 구조를 따르는 전형적인 MVC 설계를 기반으로 합니다.
  전역 예외 처리(@ControllerAdvice), 공통 응답 처리(@ControllerAdvice), JWT, Jasypt 등을 포함하여 기초적인 백엔드 서비스 구성을 완료해 놓은 상태입니다.
  Spring Boot를 기반으로 초기 설정을 최대한 단순화했으며, 이후의 도메인 개발이나 기능 확장에 바로 활용 가능한 템플릿 구조로 구성되어 있습니다.
  REST API 및 JSP 화면을 동시에 구성할 수 있어, 하이브리드한 환경 테스트에도 활용 가능합니다.

5. msa-base

  공통 모듈을 별도 라이브러리로 분리한 Spring Boot 기반의 MSA 초기 구조 프로젝트입니다.
  전체 시스템은 여러 개의 독립적인 서비스 애플리케이션으로 구성되어 있으며, core라는 공통 모듈을 다른 서비스들이 의존성으로 가져가는 방식으로 설계되어 있습니다.
  core 모듈에는 DTO, 예외 처리, 공통 응답 모델 등 재사용 가능한 로직을 포함합니다.
  서비스 간 통신은 REST 기반을 기본으로 구성되어 있으며, Kafka를 통한 비동기 이벤트 기반 통신도 적용되어 있어 확장성과 decoupling 구조를 고려한 아키텍처를 구현하고 있습니다.
  이 프로젝트는 Spring Boot 기반 MSA 환경을 구성할 때 필요한 서비스 분리, 공통 모듈 추출, 의존성 관리 등의 구조적 패턴을 제시합니다.

7. sequence

  Redis와 Spring WebFlux를 활용한 고성능 논블로킹 시퀀스 서버입니다.
  Reactive Redis (Lettuce)를 기반으로 INCR, EXPIRE 명령어를 활용하여 시퀀스를 일 단위로 자동 초기화하며 생성합니다.
  REST API로 시퀀스를 발급하며, 전체 처리 흐름은 완전한 논블로킹 구조 (Mono, ReactiveRedisTemplate)로 구현되어 있어 이벤트 루프 차단 없이 수만 TPS 처리가 가능합니다.
  TTL 설정 및 날짜 기반 키 구성을 통해 시퀀스 키를 자동 만료 처리합니다.
  실시간 TPS 측정, 응답 시간 분석 등 성능 테스트 환경(JMeter 등)과 함께 활용하기 좋습니다.

9. webflux-study

  Spring WebFlux 기반의 WebSocket 서버 구성 예제 프로젝트입니다.
  WebSocketHandler를 사용해 WebSocket 메시지를 처리하며, Flux를 통해 스트리밍 데이터를 클라이언트에 지속적으로 전송합니다.
  단순한 에코 서버부터 시작하여 클라이언트별 구독 구조, 채팅 메시지 브로드캐스트 등도 확장할 수 있도록 구성되어 있습니다.
  WebFlux의 논블로킹 특성과 Netty 기반의 효율적인 I/O 처리 구조를 학습하기에 적합합니다.
  SSE(Server-Sent Events)나 실시간 알림 서버 구성 전 WebSocket 기반 통신의 구조를 실습할 수 있습니다.
