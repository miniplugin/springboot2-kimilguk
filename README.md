#### 20210909(목)
- 스프링시큐리티 Config 클래스 생성. 일단 모든 request 에 대해서 퍼미션 all 처리.
- http://127.0.0.1:8080/h2-console 내장 DB툴 실행가능하게 속성추가.
- 외부라이비러리 spring-session-core 하단에 spring-session-jdbc 수동으로 추가.
- /error 처리용 util 컨트롤러 및 머스태치 뷰추가.

#### 20210908(수)
- springboot2-kimilguk 프로젝트 생성. https://start.spring.io/ (스프링 initialize 사용)
- 깃 레포지토리 생성 및 연동.
![ex_screenshot](./README/springboot2-kimilguk.jpg)
- 스프링부트프로젝트에서 사용할 디자인 파일 static 폴더에 이동 후 접속 http://127.0.0.1:8080/posts-list.html
- 접속시 스프링 시큐리티 의존성 때문에 아래와 같은 로그인 절차를 거쳐야 합. 초기 아이디 user / 암호는 아래 화면에서 블럭지정영역에서 확인
![ex_screenshot](./README/springboot2-01.jpg)