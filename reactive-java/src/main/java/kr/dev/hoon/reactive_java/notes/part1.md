## 리액티브 스프링?

### 리액티브란?

- 가능한 한 즉각적으로 응답
    - 신속하고 일관성 있는 응답 시간은 신뢰, 서비스 품질 제공
- 유연하고 탄력성있는 서비스 제공 (Elastic : Sharding, Scale Up)
    - 시스템 성능 측정 도구를 제공하여 Scalable한 시스템 설계 가능.
    - 사용자 요청 처리에 따라 시스템 처리량 증가 및 감소
- 시스템 탄력성, 반응성
    - 시스템에 부분적으로 장애가 발생해도 다른 서비스에는 영향없이 동작해야한다.
    - 복제를 통한 고가용성 보장 가능

- 기본적으로 작은 규모의 시스템으로 구성시켜 해당 특성들에 의존하게 된다.

### 메시지 기반 통신

- 기존의 프로그래밍은 호출된 함수가 모든 작업을 마칠대까지 제어권을 넘겨주지 않고 대기하는 형태의 Blocking 방식이다.
- Blocking 방식은 작업이 마무리 될때까지 요청 Client(Thread)s는 차단되어 대기 상태에 놓이게 되고, 이는 다른 요청에 대한 응답을 받지 못하는 Thread가 되는 것을 의미한다.
- 분산 시스템에서 서비스 간에 통신할 때 자원을 효율적으로 사용하기 위해서는 메시지 기반(Message Broker)으로 구성.
- Message Queue의 대기열 관리, 모니터링을 통해 부화 관리와 탄력성을 제어할수 있다.

### Use Case
1. 사용자 요청
2. Gateway를 통해 해당 서비스 요청
    - Gateway에서는 요청받은 URL로 서비스 이름을 제공. (요청하는 측에서는 서비스에 대한 최소한의 지식을 통해 요청)
    - 제공받은 서비스 이름을 기반으로 Discovery가 시스템 내부의 서비스로 요청.
    - 서비스 가용성 정보는 Service Registry를 이용하여 구현.
3. 외부, 타 서비스 기반의 요청시
    - Kafka, Rabbit MQ와 같은 메시지 기반 통신 구성
    - 요청을 받자마자 우선 응답을 보내고, 비동기적 처리
    - 실제 외부/다른 서비스 요청은 Message Broker를 통해 진행되며, Message Broker를 통해 해당 메시지를 유실하지 않고 재시도, 모니터링.
    - 성공/실패에 대한 처리는 비동기 비즈니스 로직에서 처리, 알림(push) 형태로 제공하는 기능이 필요하다.
    
### Service Level
- 큰 아키텍쳐 뿐만이 아니라 작은 규모의 서비스의 각 구성 요소도 리액티브하게 구성한다.

```java 
// 기존의 명령형 프로그래밍
interface Service {
    OutPut process(Input value);
}

class OrdersService {
    private final Service service;

    void process() {
        Input input = ...;
        Output output - service.process(input);
    }
}
```
해당 로직은 동기적으로 호출되어 Service가 요청을 처리하는 동안 다른 작업을 실행할수 없게 된다.

```java 
// 기존의 명령형 프로그래밍
interface ShoppingCardService {
    OutPut process(Input value, Consumer<OutPut> c);
}

class OrdersService {
    private final ShoppingCardService scService;

    void process() {
        Input input = ...;
        Output output - service.process(input, output -> {
...
        });
    }
}
```

해당 로직은 Callback 형태로 파라미터를 전달하여 동기/비동기 방식의 구현이 가능해진다.

```java
class SyncService implements Service {

    public void process(Input value, Consumer<Output> c) {
    
        Output result = template.getForOjbect(...);   // I/O Block
        ...
        c.accept(result);
    }
}

class AsyncService implements Service {

    public void process(Input value, Consumer<Output> c) {
    
    new Thread(() -> {
        Output result = template.getForOjbect(...);   // 별도의 thread에서 blocking되어 요청한 thread는 다른 작업을 수행할수 있다.
        ...
        c.accept(result);
    }).start();
    }
}
```

Java Future
```java
interface FutureService {
    Future<Output> process(Input value);
}

class OrdersService {
    private final FutureService futureService;

    void process() {
        Input input = ...;
        Future<Output> output = futureService.process(input); // 비동기적 처리 수행.
        Output output = output.get();

    }
}
```

### Spring Framework

- spring mvc는 서블릿(Servlet)을 사용ㅎ며 서블릿은 각각의 요청에 별도의 스데르를 할당해서 사용하고 있다.
- 멀티 스레딩 영역에서 컨텍스트 스위치로 인한 성능 저하
- 일반적인 Java Thread는 개당 1,024KB의 오버헤드를 가지며, 이는 많은 양의 요청이 들어올 경우 메모리에 부하 가능.

#### Reactive Framework
Akka / Vert.x