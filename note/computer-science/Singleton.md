Singleton Pattern과 Java의 Singleton

- Singleton Pattern?
  - 소프트웨어 디자인 패턴에서 싱글턴 패턴(Singleton pattern)을 따르는 클래스는, 생성자가 여러 차례 호출되더라도 실제로 생성되는 객체는 하나이고 최초 생성 이후에 호출된 생성자는 최초의 생성자가 생성한 객체를 리턴한다. 이와 같은 디자인 유형을 싱글턴 패턴이라고 한다. 주로 공통된 객체를 여러개 생성해서 사용하는 DBCP(DataBase Connection Pool)와 같은 상황에서 많이 사용된다. - Wiki
  
  - 프로그램 전반에서 인스턴스가 오직 1개만 생성되야 하는 경우에 사용되는 패턴
  
  - stateless 객체나 설계상 유일해야 하는 시스템 컴포넌트 (Effective Java3 : p23)
  
  - 동시성 문제에 대해 고민해야함. (공유 자원에 대한 수정)
  
    

- Java의 Singleton 구현 방법

  

  - Eager initialization (이른 초기화 방식)

    - 클래스 내부의 전역변수로 instance 변수 생성 (static final)
    - 생성자 생성 (private) - 다른 클래스에서의 생성을 막기 위함.
    - 클래스 내부의 전역변수에 접근할수 있는 static method 제공
    - 특징
      - 클래스가 최초에 로딩될때 생성됨으로 Thread-Safe를 보장받음.
      - 인스턴스를 사용하지 않더라도 클래스 최초 로딩시 인스턴스가 생성되고, 메모리를 사용하고 있는 상태가 된다.

    ```java
    public class Apple {
     
        private static final Apple APPLE = new Apple(); // private static 인스턴스 생성
         
        private Apple() {} // 새로운 인스턴스 생성에 대한 제한
         
        public static Apple getInstance() { // static instance 리턴
            return APPLE;
        }
    }
    ```

    

  - Lazy initialization (Not Thread-Safe)

    - 클래스 로딩 시점이 아닌 인스턴스 사용시점시 생성하는 방법
    - Multi Thread 환경에서 동시에 getInstance를 호출시 여러 인스턴스가 생성될수 있다.
    - 특징
      - Thread-Safe하지 않다.

    ```java
    public class Apple {
        
        private static Apple apple;
    
        private Apple() {}
    
        public static Apple getInstance() {
            if (apple == null) { 
                apple = new Apple();
            }
            return apple;
        }
    }
    ```

    

  - Lazy initialization (Thread-Safe)

    - 클래스 로딩 시점이 아닌 인스턴스 사용시점시 생성하는 방법
    - Multi Thread 환경에서 동시에 getInstance를 호출시 여러 인스턴스 생성을 막기 위해 synchronized 키워드 사용
    - 특징
      - Thread-Safe하다.
      - 인스턴스 생성 여부와는 관계 없이 synchronized keyword가 동작하여 성능을 저하시킨다.

    ```java
    public class Apple {
        
        private static Apple apple;
    
        private Apple() {}
    
        public static synchronzied Apple getInstance() {
            if (apple == null) { 
                apple = new Apple();
            }
            return apple;
        }
    }
    ```

    

  - Lazy initialization DCL

    - 클래스 로딩 시점이 아닌 인스턴스 사용시점시 생성하는 방법
    - 인스턴스가 생성되지 않은 경우에만 동기화 (synchronized)가 실행되도록 구현
    - volatile를 통해 CPU Cache가 아닌 Main Memory에서 변수의 값을 가져오도록 구현, read write시에 변수 값이 일치하지 않는 문제가 발생하지 않는다.
    - 특징
      - Thread-Safe하다.

    ```java
    public class Apple {
        private volatile static Apple apple;
    
        private Apple() {}
    
        public Apple getInstance() {
          if(apple == null) {
             synchronized(Apple.class) {
                if(apple == null) {
                   apple = new Apple(); 
                }
             }
          }
          return apple;
        }
    }
    ```

  

  - Enum
    - Field가 하나인 Enum으로 생성
    - 특징
      - 리플렉션 공격에 대처할수 있음
      - 복잡한 직렬화에 대응이 가능하다.

  

  ```java
  public enum Apple {
      INSTANCE;
      
      public void bite() { ... }
  }
  ```

  

  - Lazy Holder

    - volatile, synchronized keyword 없이 thread-safe한 Singleton 생성
    - 클래스가 최초 로딩될때는 로딩되지는 않고, instance를 반환하는 메소드를 호출할때 초기화 됩니다
    - 특징
      - 대표적으로 많이 사용되는 방법
      - synchronized를 사용하지 않아 성능이 좋으며, 클래스 최초 로딩시 생성되지 않음

    ```java
    public class Apple {
    
        private Apple() {}
    
        private static class InnerInstance() {
            private static final Apple appleInstance = new Apple();
        }
    
        public static Apple getInstance() {
            return InnerInstance.appleInstance;
        }
        
    }
    ```

    

- Spring Singleton

  - Spring의 Bean은 Container 내에서 유일한 Singleton으로 생성됨.

  - Bean의 성격에 따라 별도의 Scope를 지정할수 있음.

    ![image-20210115011837790](C:\Users\ydh95\AppData\Roaming\Typora\typora-user-images\image-20210115011837790.png)

    https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html

