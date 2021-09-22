<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/18
  Time: 11:56 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<%@include file="/Header/Header.jsp"%>
    <body>
    <div class="container">
        <h2>회원 정보</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>아이디</th>
                <th>비밀번호</th>
                <th>이름</th>
                <th>이메일</th>
                <th>가입일</th>
                <th>수정</th>
                <th>삭제</th>

            </tr>
            </thead>
            <tbody>
            <c:if test="${empty membersList}">
                <tr>
                    <td>등록된 회원이 없습니다.</td>
                </tr>
            </c:if>
            <c:if test="${not empty membersList}">
                <c:forEach var="i" items="${membersList}">
                    <tr>
                        <td>${i.user_id}</td>
                        <td>${i.pwd}</td>
                        <td>${i.name}</td>
                        <td>${i.email}</td>
                        <td>${i.joinDate}</td>
                        <td><a href="${contextPath}/member/modMemberForm.do?user_id=${i.user_id}">수정</a></td>
                        <td><a href="${contextPath}/member/delMember.do?user_id=${i.user_id}">삭제</a></td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <a href="${contextPath}/member/memberForm.do" class="btn btn-primary pull-right">회원 가입</a>
    
    </div>
    </body>
<%@ include file="/Footer/Footer.jsp"%>