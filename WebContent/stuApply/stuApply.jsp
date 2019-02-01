<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% request.setCharacterEncoding("utf-8");%>
<% response.setContentType("text/html;charset=utf-8"); %>
<jsp:useBean id="stuRecBn" class="stuRecordBean.StuRecBean" scope="request"></jsp:useBean>
<jsp:setProperty property="*" name="stuRecBn"/>
<jsp:useBean id="stuRecBnSrv" class="stuRecordBean.StuRecBeanService" scope="request"></jsp:useBean>
<jsp:setProperty property="*" name="stuRecBnSrv"/>

<%@ page import="java.sql.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考生輸入資訊</title>
<style>body {background-color:#70c0c0;}</style>
<script type="text/javascript" src="../jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../jquery-blink.js"></script>
<script>
$(document).ready(function()
{
        $('.blink').blink(); // default is 500ms blink interval.
        //$('.blink').blink({delay:100}); // causes a 100ms blink interval.
});
</script>
</head>
<body>
		<h1>考生輸入的個人資訊</h1>
		<b>名字:</b> ${stuRecBn.stud_name}<br>
		<b>身分證號碼:</b>${stuRecBn.stud_id}<br>
		<b>性別:</b> ${(stuRecBn.stud_gender)}<br>
		<b>生日:</b>${stuRecBn.stud_birthday}<br>
		<b>地址:</b>${stuRecBn.stud_address}<br>
		<b>電話:</b>${stuRecBn.stud_phone_num}<br>
		<!--assign examinee ID -->
		<% stuRecBn.setStud_examinee_id("A00123");%>
			<hr size=2 color=blue width=100%>
		<%
		stuRecBnSrv.DbConnect();
		try {
		stuRecBnSrv.insertStuRec(
				stuRecBn.getStud_id(),
				stuRecBn.getStud_name(),
				stuRecBn.getStud_gender(),
				stuRecBn.getStud_birthday(),
				stuRecBn.getStud_address(),
				stuRecBn.getStud_phone_num(),
				stuRecBn.getStud_examinee_id());
				stuRecBnSrv.DbClose();
		%>
				<font color='blue'><b>准考證號碼:</b>${stuRecBn.stud_examinee_id}</font><br>
		<%
		} catch (SQLException e1) {
		%>
			<h3 class="blink" style="color:red;"> 請注意:考生已經重複報名_jsp!</h3>
		<%e1.printStackTrace();}%>
		<hr size=2 color=blue width=100%>
		<b><a href='stuApply.html'>回首頁</a></b>
		</body>
</html>