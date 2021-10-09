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
<script>
    function fn_enable(obj){
        document.getElementById("i_title").disabled=false;
        document.getElementById("i_content").disabled=false;
        document.getElementById("i_imageFileName").disabled=false;
        document.getElementById("tr_btn_modify").style.display="block";
        document.getElementById("tr_btn").style.display="none";
    }

    function fn_modify_article(obj){
        obj.action="${contextPath}/borad/modArticle.do";
        obj.submit();
    }

    function fn_remove_article(url,articleNO){
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", url);
        var articleNOInput = document.createElement("input");
        articleNOInput.setAttribute("type","hidden");
        articleNOInput.setAttribute("name","articleNO");
        articleNOInput.setAttribute("value", articleNO);

        form.appendChild(articleNOInput);
        document.body.appendChild(form);
        form.submit();

    }
</script>

<body>
    <div class="container">
        <div class="col-md57 col-md-offset-2"> <div class="page-hearder" style="padding-bottom: 20px;">
            <h1>글 상세보기</h1>
        </div>

        <form class="form-group" name="frmArticle" method="post"  action="${contextPath}"  enctype="multipart/form-data">
            <table  class="table table-striped row" >
                <tr>
                    <td>
                        글번호
                    </td>
                    <td >
                        <input class="form-control" type="text"  value="${article.articleNO }"  disabled />
                        <input type="hidden" name="articleNO" value="${article.articleNO}"/>
                        <input type="hidden" name="parentNO" value="${article.parentNO}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        작성자 아이디
                    </td>
                    <td >
                        <input class="form-control" type=text value="${article.id }" name="writer"  disabled />
                    </td>
                </tr>
                <tr>
                    <td>
                        제목
                    </td>
                    <td>
                        <input class="form-control" type="text" value="${article.title }"  name="title"  id="i_title" disabled />
                    </td>
                </tr>
                <tr>
                    <td>
                        내용
                    </td>
                    <td>
                        <textarea class="form-control" rows="20" cols="60"  name="content"  id="i_content"  disabled />
                        ${article.content }
                        </textarea>
                    </td>
                </tr>

                <c:if test="${not empty article.imageFileName && article.imageFileName!='null' }">
                    <tr>
                        <td>
                            이미지
                        </td>
                        <td>
                            <input  type= "hidden"   name="originalFileName" value="${article.imageFileName }" />
                            <img src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview"  /><br>

                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input  type="file"  name="imageFileName " id="i_imageFileName"   disabled   onchange="readURL(this);"   />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td>
                        등록일자
                    </td>
                    <td>
                        <input class="form-control" type=text value="${article.writedate}" disabled />
                    </td>
                </tr>
                <tr   id="tr_btn_modify"  >
                    <td colspan="2"   align="center" >
                        <input type=button value="수정반영하기"   onClick="fn_modify_article(frmArticle)"  >
                        <input type=button value="취소"  onClick="backToList(frmArticle)">
                    </td>
                </tr>

                <tr  id="tr_btn"    >
                    <td colspan=2 align=center>
                        <input type=button value="수정하기" onClick="fn_enable(this.form)">
                        <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/borad/removeArticle.do', ${article.articleNO})">
                        <input type=button value="리스트로 돌아가기"  onClick="backToList(this.form)">
                        <input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO})">
                    </td>
                </tr>
            </table>
        </form>


    </div>
<%--<form name="frmArticle" method="post"  action="${contextPath}"  enctype="multipart/form-data">

    <div class="form-group row">
        <label for="articleNO" class="col-sm-2 col-form-label col-form-label">글번호</label>
        <div class="col-sm-4">
            <input class="form-control form-control" id="articleNO" type="text"  value="${article.articleNO }"  disabled />
            <input type="hidden" name="articleNO" value="${article.articleNO}"  />
        </div>
        <label for="articleID" class="col-sm-2 col-form-label col-form-label">작성자 아이디</label>
        <div class="col-sm-4">
            <input class="form-control form-control" type=text value="${article.id }" name="writer" id="articleID"  disabled />
        </div>
    </div>

    <div class="form-group row">

    </div>

    <table  border="0" align="center" >
        &lt;%&ndash;<tr>
            <td width="150" align="center" bgcolor="#FF9933">
                글번호
            </td>
            <td >
                <input type="text"  value="${article.articleNO }"  disabled />
                <input type="hidden" name="articleNO" value="${article.articleNO}"  />
            </td>
        </tr>&ndash;%&gt;
        &lt;%&ndash;<tr>
            <td width="150" align="center" bgcolor="#FF9933">
                작성자 아이디
            </td>
            <td >
                <input type=text value="${article.id }" name="writer"  disabled />
            </td>
        </tr>&ndash;%&gt;
        <tr>
            <td width="150" align="center" bgcolor="#FF9933">
                제목
            </td>
            <td>
                <input type="text" value="${article.title }"  name="title"  id="i_title" disabled />
            </td>
        </tr>
        <tr>
            <td width="150" align="center" bgcolor="#FF9933">
                내용
            </td>
            <td>
                <textarea rows="20" cols="60"  name="content"  id="i_content"  disabled />
                    ${article.content }
                </textarea>
            </td>
        </tr>

        <c:if test="${not empty article.imageFileName && article.imageFileName!='null' }">
            <tr>
                <td width="150" align="center" bgcolor="#FF9933"  rowspan="2">
                    이미지
                </td>
                <td>
                    <input  type= "hidden"   name="originalFileName" value="${article.imageFileName }" />
                    <img src="${contextPath}/download.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview"  /><br>

                </td>
            </tr>
            <tr>
                <td>
                    <input  type="file"  name="imageFileName " id="i_imageFileName"   disabled   onchange="readURL(this);"   />
                </td>
            </tr>
        </c:if>
        <tr>
            <td width="150" align="center" bgcolor="#FF9933">
                등록일자
            </td>
            <td>
                <input type=text value="${article.writedate}" disabled />
            </td>
        </tr>
        <tr   id="tr_btn_modify"  >
            <td colspan="2"   align="center" >
                <input type=button value="수정반영하기"   onClick="fn_modify_article(frmArticle)"  >
                <input type=button value="취소"  onClick="backToList(frmArticle)">
            </td>
        </tr>

        <tr  id="tr_btn"    >
            <td colspan=2 align=center>
                <input type=button value="수정하기" onClick="fn_enable(this.form)">
                <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/board/removeArticle.do', ${article.articleNO})">
                <input type=button value="리스트로 돌아가기"  onClick="backToList(this.form)">
                <input type=button value="답글쓰기"  onClick="fn_reply_form('${contextPath}/board/replyForm.do', ${article.articleNO})">
            </td>
        </tr>
    </table>
</form>--%>
</body>
<%@ include file="/Footer/Footer.jsp"%>


