<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page session="false"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <style>
        * { box-sizing:border-box; }

        form {
            width:400px;
            height:600px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(89,117,196);
            border-radius: 10px;
        }

        .input-field {
            width: 300px;
            height: 40px;
            border : 1px solid rgb(89,117,196);
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 15px;
        }
        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }

        button {
            background-color: rgb(89,117,196);
            color : white;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }

        .title {
            font-size : 50px;
            margin: 40px 0 30px 0;
        }

        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 20px;
        }
        .msg2 {
            height: 10px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 10px;
        }
        .sns-chk {
            margin-top : 5px;
        }
    </style>
    <title>SpringProject</title>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">SpringProject</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">SignUp</a></li>
        <li><a href=""><i class="fa fa-search"></i></a></li>
    </ul>
</div>
<!-- onsubmit은 이벤트 등록.
    메서드는 POST고, submit을 누르면 formCheck(this)로 this(이 폼)를 체크하라는 뜻.
    formCheck(this)가 true이면 form 전송, false면 전송X.
    c:url태그는 context root를 자동으로 생성해줌. -->
<!--<form action="<c:url value="/register/add"/>" method="post" onsubmit="return formCheck(this)">-->
    <form:form modelAttribute="user">
    <div class="title">Register</div>
    <!-- Controller에서 인코딩 해서 보낸 msg를 뷰에서 디코딩 해줘야한다. -->
    <!-- URLDecoder는 java.net을 임포트 해야 사용 가능하다 -->
    <div id="msg" class="msg"><form:errors path="id"/></div>
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <div id="msg" class="msg2"><form:errors path="pwd"/></div>
    <label for="">비밀번호</label>
    <input class="input-field" type="password" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    <label for="">이메일</label>
    <input class="input-field" type="text" name="email" placeholder="example@fastcampus.co.kr">
    <label for="">생일</label>
    <input class="input-field" type="text" name="birth" placeholder="2020/12/31">
    <div class="sns-chk">
        <label><input type="checkbox" name="sns" value="facebook"/>페이스북</label>
        <label><input type="checkbox" name="sns" value="kakaotalk"/>카카오톡</label>
        <label><input type="checkbox" name="sns" value="instagram"/>인스타그램</label>
    </div>
    <button>회원 가입</button>
</form:form>
<script>
    <!-- 폼을 전송하기 전에 체크하는 자바스크립트 함수 -->
    function formCheck(frm) {
        var msg ='';

        <!-- id의 value의 length가 3미만이면 -->
        if(frm.id.value.length<5) {
            setMessage('id의 길이는 5이상이어야 합니다.', frm.id);
            return false;
        }

        //검증은 자바스크립트, Validation 둘 다 해야 함!
        // if(frm.pwd.value.length<3) {
        //   setMessage('pwd의 길이는 3이상이어야 합니다.', frm.pwd);
        //   return false;
        // }

        return true;
    }

    function setMessage(msg, element){
        document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;

        if(element) {
            element.select();
        }
    }
</script>
</body>
</html>
