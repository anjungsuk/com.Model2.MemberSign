<%--
  Created by IntelliJ IDEA.
  User: gangjungu
  Date: 2021/09/18
  Time: 12:36 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <script src="https://code.jquery.com/jquery-2.2.4.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
    <title>회원 조회 리스트</title>
    <c:choose>
        <c:when test='${msg == "addMember"}'>
            <script>
                $(document).ready(function(){
                    alert("정상적으로 회원 등록을 하였습니다.");
                })
            </script>
        </c:when>
        <c:when test='${msg == "modified"}'>
            <script>
                $(document).ready(function(){
                    alert("정상적으로 회원 수정을 하였습니다.");
                })
            </script>
        </c:when>
        <c:when test='${msg == "complete"}'>
            <script>
                $(document).ready(function(){
                    alert("삭제 되었습니다.");
                })
            </script>
        </c:when>

    </c:choose>
    <script>
        function readURL(input)
        {
            if(input.files && input.files[0])
            {
                var reader = new FileReader();
                reader.onload = function(e){
                    $("#preview").attr('src', e.target.result);
                }
                reader.readAsDataURL(input.files[0])
            }
        }
        function backToList(obj){
            obj.action = "${contextPath}/borad/listArticles.do";
            obj.submit();
        }
    </script>
</head>
<body>

</body>
</html>
