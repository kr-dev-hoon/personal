# Eureka Server, Client

## 목차

1. Eureka란?
2. Eureka API
3. Flow
5. Replication

## 개요
- 지난 몇 주동안 2번의 서버가 다운되는 문제가 발생하였고, 해당 서버의 서비스들로 요청이 정상적으로 이루어지지 않아 서비스 상에서 장애가 발생하였다. 
- 현재 API 서버들은 Spring Cloud 환경을 사용하고 있으며, 각 서비스의 Discovery를 위해 Spring Cloud Discovery를 사용하고 있다.
- 장애가 발생하였을때 사용할수 없는 서비스들이 Eureka Instance 목록에 남아있는 것으로 확인되었고 이로 인해 서비스의 장애가 발생하였다.
- 장애 상황시 하트비트가 오지 않는 서비스들이 왜 서비스 목록에서 제거 되지 않았는지를 확인하며 Eureka의 개요 및 등록, 해제 및 Replication에 대해 알게 된 사실에 대해 정리하였다.
## Eureka 란?

1. Discovery Pattern
   1. MSA환경에서 Service의 IP, Port등을 동적으로 등록, 발견하는 패턴
   2. 서비스 간의 통신시 동적으로 등록된 Instance 정보들을 참조하여 호출하게 된다.
   3. 서비스의 물리적인 주소를 몰라도 이름을 기반으로 호출할수 있다.
   4. IP, Port등이 바뀌어도 호출하는 대상은 알필요가 없음.
   5. Scale Out등 동일 Service의 대수가 늘어나도 일일이 등록시킬 필요가 없으며, 로드밸런서가 알아서 분배 호출
2. Spring Cloud Netflix Discovery
   1. Spring Cloud 환경에서 Discovery 서비스 구현
   2. Server와 Client로 나뉘어 각 서비스들은 최초 로딩시 Client로써 Eureka Server에 등록(Registry)하게 된다.
   3. Ribbon(Load Balencer)는 Eureka Server의 Registry된 Service 정보들을 가져와 기반으로 로드 밸런싱.
      (매 호출마다 Eureka Server의 정보를 가져오진 않고, Ribbon 내부의 Caching된 정보를 기반으로 함 / 일정 주기별로 갱신)
3. Discovery Server
   1. Eureka Instance를 등록, 관리하는 서비스
   2. 로드밸런서를 해당 서비스의 정보를 가져와 로드 밸런싱을 한다.
   3. Client들의 주기적인 HeartBeat를 기반으로 서비스 목록을 갱신한다. Heart Beat가 들어오지 않는 Service는 일정 시간 이후 서비스 목록에서 제거된다.
4. Discovery Client
   1. Eureka Server에 의해 등록될 Service들
   2. 최초 로딩시 .yml에 지정된 Eureka Server에 Registry 요청.
      등록 API 추가!
   3. 주기별로 Heart Beat를 Eureka Server에 전달



## Eureka API

* Service 조회, 등록, 해제, 상태 관리, 갱신 등은 Eureka Server의 API를 통해 자동으로 이루어지며, 사용자가 수동으로 API를 통해 해당 작업을 진행할수도 있다.

* https://github.com/Netflix/eureka/wiki/Eureka-REST-operations

  

- GET /eureka/{version}/apps
  - Eureka Apps(Instances)들의 상태를 조회
- POST /eureka/{version}/apps/appID
  - Eureka Instance 조회
- PUT /eureka/{version}/apps/apps/appID/instanceID
  - Eureka Instance 갱신
- DELETE /eureka/{version}/apps/apps/appID/instanceID
- Eureka Instance를 목록에서 제거
- POST /eureka/app/delta
  - 변경된 Instance의 상태만을 조회
- POST /eureka/peerreplication/batch
  - replication eureka server에 instance 목록 갱신 



## Flow

1. Eureka Server Running
2. 서비스들은 등록된 Eureka Server정보들을 참조하여 Registry 요청
3. Eureka Server에 Service 목록 갱신
4. 등록된 서비스들은 일정 주기별로( eureka.instance.lease-renewal-interval-in-seconds=30 ) Heart Beat 전달
5. Eureka Server는 heart beat가 들어오지 않는 Service는 비 정상적으로 내려갔음을 간주하고, 해당 서비스를 instance 목록에서 제거하게 된다.
   1. eureka.instance.lease-expiration-duration-in-seconds 의 시간이상 heart beat가 오지 않으면 서비스 제거 (default 90 seconds)
   2. Eureka Server는 자체 보존 모드 (Preservation Mode)의 경우 서비스가 일정 시간이 지나도 제거되지 않으며, 일정 임계치가 될때까지 제거되지 않는다.
      https://www.baeldung.com/eureka-self-preservation-renewal
6. Eureka Client Service가 정상적으로 재시작, 종료시에 Server에 DELETE요청을 날려 종료된 서비스쪽으로 요청이 들어오지 않도록 한다.

- eureka server registry

```
eureka:
  client:
    serviceUrl:
      defaultZone: http://peer1/eureka/,http://peer2/eureka/,http://peer3/eureka/
```




## Replication

- 이중화된 서버의 Eureka Server들의 로그를 확인하며 특이사항 하나를 찾을수 있었다.
- 한쪽은 모든 서비스들로부터 heart beat를 받지 않고, 다른 Eureka Server로 부터 replication 요청만을 받고 있었다.
- 현재 개발 서버에서 로그를 확인하였을때 다음과 같이 동작하고 있음을 확인할수 있었다.

![image-20210118232451855](C:\Users\ydh95\AppData\Roaming\Typora\typora-user-images\image-20210118232451855.png)

- api server에서 eureka server가 존재하는 server (replication을 요청하는)를 별도로 지정하지 않았음에도 한곳으로 요청이 가고 있었다.
- Server 2에서는 Server 1의 Eureka Server가 POST해주는 instance 목록을 기반으로 Service 목록을 갱신하게 된다.



- 상용 서버에서는 다음과 같이 동작하지 않고 있었고, 

![image-20210118233152406](C:\Users\ydh95\AppData\Roaming\Typora\typora-user-images\image-20210118233152406.png)

- 개발 서버와 다른 호출 구조를 가지고 있었으며 상용 서버에서 발생한 문제에 대해 아직까지 정확한 원인을 찾고 있다.
- 로컬과 개발 서버에서는 서비스가 강제 종료되어도 일정 시간동안 보존 모드에서 대기한후 삭제되는것으로 확인하였다.
- 개발 서버와 상용 서버의 설정들과 물리적인 서버들의 구성에서 놓친 부분이 없나 확인하고 있다. 
  - .yml configuration ?  
  - vip?



* 첫번째 장애시에는 부랴부랴 서비스 목록을 수동으로 DELETE 요청하여 그 뒤의 요청은 정상적인 서버로의 요청만 요청하게끔 하였다.