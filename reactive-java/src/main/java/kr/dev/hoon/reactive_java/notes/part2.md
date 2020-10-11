## Spring Reactive Programming - Basic

### Observer Pattern
- 일종의 구독 패턴이라고 보면 된다.
    - 어떤 서비스를 구독하고 있는 사람이 있고, 서비스를 구독하고 있는 사람에게 content을 제공하는 사람이 있다.
    - PUB -- SUB
- 주로 분산 이벤트 핸들링에 사용한다.

- Subject : 이벤트를 발생시키는 역할
- Observer : 이벤트를 수신하는 역할

- 해당 패턴을 사용하여 런타임에 객체 사이에 일대다 의존성 등록 가능.
- 이벤트와 관찰자 사이의 결합도를 낮출수 있다.

https://ko.wikipedia.org/wiki/%EC%98%B5%EC%84%9C%EB%B2%84_%ED%8C%A8%ED%84%B4

### Pub-Sub 구현

### RxJava

### Marble Diagram

### Use Case

### Cureent