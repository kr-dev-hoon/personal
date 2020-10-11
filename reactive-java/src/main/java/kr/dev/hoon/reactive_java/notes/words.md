### 리액티브 관련 단어 정리

- 리액티브 용어 및 설명
    - https://www.reactivemanifesto.org/ko
    - https://www.reactivemanifesto.org/ko/glossary    

# sync, async, blocking, non-blocking
- 흔히 말하는 async란 non blocking async를 이야기하는듯 싶다.

- 혼동 될수 있으나 Blocking -- Sycn / Non-Blocking -- Async는 다른 개념이다.

- Sync : 호출된 함수의 실행결과를 기다리거나, 작업 완료 여부를 주기적으로 확인.
- Async : 호출된 함수의 실행결과를 기다리지 않고, Callback 형태로 수행한다.
- Blocking : 호출된 함수가 제어권을 넘기지 않아, 호출한 곳에서 다른 작업을 수행하지 못함.
- Non-Blocking : 호출된 함수가 제어권을 바로 넘겨서, 호출한 곳에서 다른 작업을 수행할수 있음.

https://homoefficio.github.io/2017/02/19/Blocking-NonBlocking-Synchronous-Asynchronous/