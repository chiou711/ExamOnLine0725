<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="stuBn" class="stuRecordBean.StuRecBean" scope="application" />
<jsp:setProperty property="*" name="stuBn" />
<jsp:useBean id="stuBnSrv" class="stuRecordBean.StuRecBeanService" scope="application" />
<jsp:setProperty property="*" name="stuBnSrv" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理考生資料</title>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<style>body {background-color:#70c0c0;}</style>
</head>
<body>
	<form action="ReadStuRec.do" method="post">
		<h3>以考生身分證號碼讀取：</h3>
		<input type="text" name="stuID" value=${stuBn.stud_id}>
		<input type="submit" name="varReadOne" value="確定">	
		<h3>刪除以上考生個人資料：
		<input type="submit" name="varDeleteOne" value="確定"></h3>	
		<h3>讀取全部考生資料
		<input type="submit" name="varReadAll" value="確定"></h3>
	</form>
	<hr size=1 color=blue width=100%>
		<table style="text-align: left;" border="1" cellpadding="2"
			cellspacing="2">
			<tr>
				<th>身分證號碼</th>
				<th>姓名</th>
				<th>性別</th>
				<th>生日</th>
				<th>地址</th>
				<th>電話</th>
				<th>准考證號碼</th>
			</tr>
	<c:choose>
    <c:when test="${stuBn.readBy == 'byId'}">
			<tr>
				<td>${stuBn.stud_id}</td>
				<td>${stuBn.stud_name}</td>
				<td>${stuBn.stud_gender}</td>
				<td>${stuBn.stud_birthday}</td>
				<td>${stuBn.stud_address}</td>
				<td>${stuBn.stud_phone_num}</td>
				<td>${stuBn.stud_examinee_id}</td>
			</tr>
	</c:when>
    <c:when test="${stuBn.readBy == 'byAll'}">
			<c:forEach var="stuBn" items="${stuBnSrv.stuRecBnList}">
			<tr>
				<td>${stuBn.stud_id}</td>
				<td>${stuBn.stud_name}</td>
				<td>${stuBn.stud_gender}</td>
				<td>${stuBn.stud_birthday}</td>
				<td>${stuBn.stud_address}</td>
				<td>${stuBn.stud_phone_num}</td>
				<td>${stuBn.stud_examinee_id}</td>
			</tr>
			</c:forEach>
    </c:when>
    <c:otherwise>
    <div>未選取讀取方式</div>
    </c:otherwise>
    </c:choose>
	</table>
	

</body>
</html>