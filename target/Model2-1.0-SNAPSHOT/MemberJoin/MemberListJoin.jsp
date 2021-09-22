<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/21
  Time: 9:50 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/Header/Header.jsp"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<body>
<form class="form-horizontal" action='${contextPath}/member/addMember.do' method="POST">
    <fieldset>
        <div id="legend">
            <legend class="">회원가입</legend>
        </div>
        <div class="control-group">
            <!-- Username -->
            <label class="control-label"  for="user_id">아이디</label>
            <div class="controls">
                <input type="text" id="user_id" name="user_id" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- Password-->
            <label class="control-label" for="pwd">비밀번호</label>
            <div class="controls">
                <input type="password" id="pwd" name="pwd" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- E-mail -->
            <label class="control-label" for="name">이름</label>
            <div class="controls">
                <input type="text" id="name" name="name" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- E-mail -->
            <label class="control-label" for="email">E-mail</label>
            <div class="controls">
                <input type="text" id="email" name="email" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <!-- Button -->
            <div class="controls">
                <input type="submit" value="회원가입">
                <input type="reset" value="다시입력">
            </div>
        </div>
    </fieldset>
</form>

</body>
<%@ include file="/Footer/Footer.jsp"%>
