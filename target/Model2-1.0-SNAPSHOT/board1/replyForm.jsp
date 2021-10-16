<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/10/12
  Time: 9:44 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>

<%@include file="/Header/Header.jsp"%>
<body>
<h1><strong>답글 작성</strong></h1>
<form name="frmReply" method="post" action="${contextPath}/borad/addReply.do" enctype="multipart/form-data">

  <div class="mb-3">

    <label for="user_id">글쓴이</label>

    <input type="text" class="form-control" name="user_id" id="user_id" value="lee" disabled/>

  </div>

  <div class="mb-3">

    <label for="title">글 제목</label>

    <input type="text" class="form-control" name="title" id="title" placeholder="글 제목을 입력해 주세요">

  </div>

  <div class="mb-3">

    <label for="content">내용</label>

    <textarea class="form-control" rows="5" name="content" id="content" placeholder="내용을 입력해 주세요" ></textarea>

  </div>



  <div class="mb-3">

    <label for="imageFileName">파일 업로드</label>
    <input type="file" name="imageFileName" id="imageFileName" onchange="readURL(this);"/>
    <img name="preview" id="preview" src="#" width = 200 height="200">

  </div>
  <div class="mb-3">
    <input type="submit" value="답글 작성">
    <input type="button" value="글목록" onclick="backToList(this.form)"/>
  </div>
</form>
</body>
<%@ include file="/Footer/Footer.jsp"%>
