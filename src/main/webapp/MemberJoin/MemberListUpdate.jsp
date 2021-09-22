<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/22
  Time: 11:36 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/Header/Header.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<body>
<form class="form-horizontal" action='${contextPath}/member/modMember.do?user_id=${memberID.user_id}' method="POST">
    <fieldset>
        <div id="legend">
            <legend class="">정보 수정</legend>
        </div>
        <div class="control-group">
            <!-- Username -->
            <label class="control-label"  for="user_id">아이디</label>
            <div class="controls">
                <input type="text" id="user_id" name="user_id" value="${memberID.user_id}" disabled class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- Password-->
            <label class="control-label" for="pwd">비밀번호</label>
            <div class="controls">
                <input type="password" id="pwd" name="pwd" value="${memberID.pwd}" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="name">이름</label>
            <div class="controls">
                <input type="text" id="name" name="name" value="${memberID.name}" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- E-mail -->
            <label class="control-label" for="email">E-mail</label>
            <div class="controls">
                <input type="text" id="email" name="email" value="${memberID.email}" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- 가입일 -->
            <label class="control-label" for="email">E-mail</label>
            <div class="controls">
                <input type="text" id="joinDate" name="joinDate" value="${memberID.email}" disabled class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- Button -->
            <div class="controls">
                <input type="submit" value="수정하기">
                <input type="reset" value="다시입력">
            </div>
        </div>
    </fieldset>
</form>

</body>
<%@ include file="/Footer/Footer.jsp"%>
