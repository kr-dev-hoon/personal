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

### RxJava

- Java 플랫폼에서 리액티브 프로그래밍을 위한 라이브러리.
https://github.com/ReactiveX/RxJava

```java
public interface RxObserver<T> {

    void onNext(T next);
    void onCompleted();
    void onError(Throwable t);
}
```

Event Generator
```java 
Observable<String> observable = Observable.create(
    sub -> {
        sub.onNext("Hello World");
        sub.onCompleted();
    }
);
```

```java
Subscriber<String> subscriber = new Subscriber<String>() {
    
    public void onNext(String s) {
        System.out.println(s);
    }

    public void onCompleted() {
        System.out.println("done!");
    }

    public void onError(Throwable t) {
        System.err.println(t);
    }
}
```

```java
Subscriber<String> subscriber = new Subscriber<String>() {
    
    public void onNext(String s) {
        System.out.println(s);
    }

    public void onCompleted() {
        System.out.println("done!");
    }

    public void onError(Throwable t) {
        System.err.println(t);
    }
}
```

With Lambda Expression
```java
Observable<String> observable = Observable.create(
    sub -> {
        sub.onNext("Hello World");
        sub.onCompleted();
    }
).subscribe(
    System.out::println,
    System.err::println,
    () -> System.out.println("Done!")
);
```

### Marble Diagram
- 스트림의 흐름을 시각적으로 표현하기 위한 다이어 그램
- 비동기 데이터에 대한 흐름을 시각화
- https://rxmarbles.com/