<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/29
  Time: 10:23 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<%@include file="/Header/Header.jsp"%>
<body>
    <article>

        <div class="container" role="main">

            <h2>board Content</h2>



            <div class="bg-white rounded shadow-sm">

                <div class="board_title"><c:out value="${article.title}"/></div>

                <div class="board_info_box">

                    <span class="board_author">
                        <p>등록일 : <c:out value="${article.writedate}"/></p>
                    </span>

                </div>

                <div class="board_content">${article.content}</div>
                <c:if test="${not empty article.imageFileName && article.imageFileName != 'null'}">
                    <input type="hidden" name="originalFileName" value="${article.imageFileName}">
                    <img src="${contextPath}/download.do?imageFileName=${article.imageFileName}&articleNO=${article.articleNO}" id="preview"/>

                </c:if>


            </div>



            <div style="margin-top : 20px">

                <button type="button" class="btn btn-sm btn-primary" id="btnUpdate">수정</button>

                <button type="button" class="btn btn-sm btn-primary" id="btnDelete">삭제</button>

                <button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>

            </div>

        </div>



    </article>

</body>
<%@ include file="/Footer/Footer.jsp"%>


