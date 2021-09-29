<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/26
  Time: 2:34 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<%@include file="/Header/Header.jsp"%>
<body>
<div class="container">
    <h2>리스트</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>글번호</th>
            <th>작성자</th>
            <th>제목</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty articlesList}">
            <tr>
                <td>등록된 게시글이 없습니다.</td>
            </tr>
        </c:if>
        <c:if test="${not empty articlesList}">
            <c:forEach var="article" items="${articlesList}" varStatus="articleNum">
                <tr>
                    <td>${articleNum.count}</td>
                    <td>${article.id}</td>

                    <td align="left" width="35%">
                        <c:choose>
                            <c:when test="${article.level>1}">
                                <c:forEach begin="1" end="${article.level}" step="1" >
                                        <span style="padding-left: 20px;"></span>
                                </c:forEach>
                                <span style="font-size: 12px;">[답변]</span>
                                <a href="${contextPath}/borad/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}/borad/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <fmt:formatDate value="${article.writedate}"></fmt:formatDate>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
    <a href="${contextPath}/borad/articleForm.do"><p>글쓰기</p></a>
</div>

</body>
<%@ include file="/Footer/Footer.jsp"%>
