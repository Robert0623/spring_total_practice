# 스프링의 정석 총 정리 및 복습
## 07.11
### 초기 세팅
- pom.xml - log4j(gbee), jackson-databind 제외하고 전부 추가
- web.xml - 한글 변환 필터 추가
- root-context.xml  
1. MySQL Jdbc 드라이버, TxManager, sqlSessionFactory, sqlSession, component-scan bean으로 추가
2. sqlSessionFactory의 mapperLocations 주석처리
- servlet-context.xml - view-controller로 루트와 index.jsp 맵핑
- mybatis-config.xml - mybatis 설정파일, typeAliases는 주석처리
- mapper폴더 - src/main/resource에 생성
- home.jsp, HomeController - 삭제
- index.jsp, menu.css - 추가

## 07.12
### 에러가 발생해서 Repo를 옮김
어제 톰캣에서 Artifact에 web:war, ch4:war, web:war exploded, ch4:war exploded 4개가 보였는데,
무시하고 진행했고 문제 없었음. 여기서부터 무언가 문제가 있지 않았을까 생각함...
오늘 JSP 페이지를 추가하고 RegisterController로 간단한 맵핑을 하고 실행시켰는데, registerForm.jsp의 뷰 이름은 리턴은 되는데, 
연결된 페이지가 없다고 브라우저에 나옴.
오타 확인 후 InternalResourceViewResolver 확인 - 문제 없음.
어제 만들어 놓은 loginForm.jsp를 RegisterController에서 return하니 페이지를 잘 보여주는데, 
loginForm.jsp를 복사해서 이름을 바꾸거나, 새로 JSP를 만들어도 뷰이름만 리턴되고 브라우저에 보여주지를 못함.
검색해서 몇 가지 확인을 해 보았지만 실패하고, 빠른 진행을 위해 경험대로 진행. 
1. 톰캣 세팅 제거 후 다시 세팅 - 해결 X
2. Invalidate Caches로 시스템 캐시와 로컬 히스토리를 제거 - 해결 X
3. 진행하던 프로젝트 폴더를 백업하고, 새 프로젝트를 만들어서 세팅 후 이전 파일 복사 - 해결 O
새로 프로젝트를 만드니 Artifact도 web:war, web:war exploded 2개만 보이고 정상 작동 함.

### 구현
- 회원가입, 로그인 구현
- RegisterController
1. @GetMapping 작성
2. @PostMapping - Validator를 사용해서 아이디 길이가 5~12가 아니거나, id또는 pwd가 공백이면 다시 회원가입으로 이동.

- BoardController
1. @GetMapping - 로그인체크 - session을 얻어서, 세션에 id가 있는지 확인.
2. 로그인 체크를 통과하면(세션에 id가 있으면), 게시판으로 이동하게 하고,
3. 통과 못하면(세션에 id가 없으면), URL재작성으로 URL 정보인 toURL을 가지고 로그인 화면으로 이동하도록 작성.

- LoginController
1. @GetMapping - 로그인 - 로그인 화면으로 이동
2. @GetMapping - 로그아웃 - 세션을 종료하고, 홈으로 이동
3. @PostMapping - 로그인체크로 id, pwd를 확인해서,
4. 일치하지 않으면 일치하지않는다는 메세지를 인코딩해서 URL재작성으로 뷰로 보내고,
5. 일치하면 세션에 id를 저장하고, 쿠키를 작업.
6. 로그인화면에 아이디 기억(rememberId)가 체크 됐으면(true면) 쿠키를 생성해서 응답에 추가.
7. 아이디 기억(rememberId)이 false면 쿠키를 삭제해서 응답에 추가.
8. 그리고 게시판에서 로그인화면으로 진입한 경우를 위해 toURL을 확인 후, toURL 또는 홈으로 리다이렉트.

- registerForm.jsp
1. prefix="form"을 임포트 및 form:form태그를 사용해서 에러메세지를 출력하도록 수정
2. 뷰에서도 자바스크립트로 id, pwd 길이를 검증.

- registerInfo.jsp - 회원가입 후 정보 확인
- loginForm.jsp  
1. 세션을 시작하지 않도록 임포트 하고,
2. 링크가 붙은 곳에 c:url태그를 사용해서 링크를 작성.
3. board에서 전달받은 toURL을 input의 hidden타입으로 받아서 post로 LoginController로 전송.
4. 아이디 기억이 체크되어 있으면 checked를 input타입 checkbox에 넣고, 없으면 빈 문자열을 넣도록 작업.
5. 자바스크립트로 id 또는 pwd의 길이가 0이면, 입력 해달라는 메세지 출력. 

- index.jsp
1. 세션을 시작하지 않도록 임포트 하고,
2. 세션을 시작하지않고 세션에 id가 있는지 확인해서, 있으면 id를 얻어서,
3. navibar에 로그인 상태일 때는 id가 나오도록 하고, 로그아웃 상태일 때는 Login이 나오도록 작업.
4. 해당 링크 또한 로그인 또는 로그아웃되도록 작업.

- board.jsp - 모든 링크는 c:url태그로 작성.

## 07.13
### 로그인, User(dto) 1차 수정
- LoginController - userDao를 주입받도록 하고, loginCheck를 수정
- loginForm.jsp - HTML, CSS 수정
- User - 주석 추가
- UserDao, UserDaoImpl - CRUD 작업 쿼리문을 jdbc로 작성

### Board - DB테이블, DTO, Mapper, DAO 작성 
- root-context.xml - mapperLocations 주석 해제
- mybatis-config.xml - typeAlias 주석 해제
- DB에 board 테이블 생성
- BoardDto 작성 - up_date 제외, 생성자는 title, content, writer, equals&hashCode는 bno, title, content, writer
- boardMapper.xml - SQL 작성
- BoardDaoImpl작성 후 BoardDao 인터페이스 생성
- BoardDaoImplTest - select 테스트 코드 작성

### 페이징
- PageHandler - 작성
- PageHandlerTest - 테스트 코드 작성

## Board - 페이지 추가, Service 작성
- boardMapper.xml - selectPage 추가 - 페이지 사용
- BoardDao, BoardDaoImpl, BoardDaoImplTest - selectPage 추가 및 CRUD 테스트
- BoardService, BoardServiceImpl - Business Logic 작성
- BoardController - 페이지 작업 추가 - list(offset, pageSize), ph(페이지 정보)를 Model로 boardList로 보냄. 
- boardList.jsp - 표를 추가해서 리스트를 표시. 하단에 페이지 추가.

## Board - 읽기, 삭제 구현
- BoardController 
1. list메서드로 boardList.jsp로 갈 때 page, pageSize를 받도록 추가 - board에서 목록버튼, 삭제버튼 누를 시 원래 페이지로 가도록
2. read메서드로 title의 a태그를 클릭해서 board.jsp로 갈 때 pgae, pageSize를 가지고 가도록 작성 - 가지고 가야 다시 돌아올 때 해당 페이지로 올 수 있음
3. remove메서드로 board.jsp에서 삭제버튼을 누르면 원래 페이지로 오도록 - rattr사용시 오류 발생. 수정 예정.
- boardList.jsp - 자바스크립트로 Controller의 msg를 받아서 alert로 띄우게 작성
- board.jsp - jQuery로 목록, 삭제버튼 누를 시 원래 페이지로 이동하도록 작성

## 07.14
### Board - 쓰기, 수정 구현
- boardMapper.xml - update의 조건에 writer 추가
- BoardServiceImpl - 예외를 발생시켜서 write 테스트
- BoardController - write(GET, POST), modify 추가
- boardList.jsp - 글쓰기 버튼을 만들고, 버튼을 누르면 GET으로 요청하도록 작성, 글쓰기 등록 후 메세지 출력되도록 작성
- board.jsp 
1. "mode"="new"를 전달받으면 게시물 글쓰기로 바뀌고 title, content를 수정할 수 있도록 수정.
2. 글쓰기 버튼을 누르면 글이 등록되도록 작성
3. 수정버튼을 누르면 버튼 이름을 등록으로, 게시물 수정으로 바뀌도록 하고, readonly를 태그에서 없애도록 작성.

### Board - 검색 기능 구현
- board.jsp, boardList.jsp - HTML, CSS 수정, ```<c:out>태그로 수정```
- SearchCondition - 검색 조건, 쿼리스트링 작업 - page, pageSize, offset, keyword, option
- PageHandler - page, pageSize, offset, keyword, option을 SearchCondition에 넣고, SearchCondition을 사용하도록 수정
- PageHandlerTest - 주석 처리
- mybatis-config.xml - typeAlias 추가
- boardMapper.xml - keyword, option으로 searchSelectPage, searchResultCnt 추가
- BoardDao, BoardDaoImpl, BoardDaoImplTest - searchSelectPage, searchResultCnt 작성 및 테스트
- BoardService, BoardServiceImpl - getSearchResultPage, getSearchResultCnt 추가
- BoardController - list메서드에 SearchCondition을 쓰도록 수정

### Html, Css, error_message 수정
### Validator, RegisterController 수정
### 수정 및 추가 할 것
- User를 계층별로 나눠서 MyBatis를 사용하도록 수정
- GlobalValidator말고 UserValidator를 사용하도록 수정
- 댓글 기능 추가
