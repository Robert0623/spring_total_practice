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
- 회원가입, 로그인