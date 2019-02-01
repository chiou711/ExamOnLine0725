<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="qBean" class="qBankBean.QBean" scope="application" />
<jsp:setProperty property="*" name="qBean"/>
<jsp:useBean id="epBeanSrv" class="qExamPaperBean.QExPaperBeanService" scope="application" />
<jsp:setProperty property="*" name="epBeanSrv"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查詢題庫內容</title>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>  
<%@ page import="qBankBean.QListBean"%> 
<%@ page import="qBankBean.QListBeanService"%>
<style>
body {background-color:#70c0c0;}
th   {background-color:#c0c070;}
</style>
</head>
<body>
<%
	QListBeanService.DB_connect();
	QListBeanService qBServBean =new QListBeanService();	
	List<QListBean> qBean4List = qBServBean.getqContentList();
%>
<h3>選擇考卷題目</h3>
<form action="QExPaperMake.do" method="post" target="Win2" name="QExPaperMakeForm">
考卷編號<input type="text" name="EpId" value="${epBeanSrv.epMaxId}">(提示為目前考卷編號的最大值)
<input type="submit" name="EditE" value="新增考卷"><br>
<input type="reset" value="重新勾選" >
<input type="submit" name="EditE" value="清空考卷稿" >
<table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
		<tr>
		<th>勾選</th>
		<th>題目ID</th>
		<th>考卷題目</th>
		<th>選答1</th>
		<th>選答2</th>
		<th>選答3</th>
		<th>選答4</th>
		<th>解答</th>
		<th>分類</th>
		</tr>
		<%
		for(int i=0;i<qBean4List.size();i++)
		{
		%>
			<tr>
				<td><input type = "checkbox" name="EPaperArr" value= <%=qBean4List.get(i).getQues_id() %> ></td>
				<td><%=qBean4List.get(i).getQues_id() %></td>
				<td><%=qBean4List.get(i).getQues_body() %></td>
				<td><%=qBean4List.get(i).getQues_ans_1() %></td>
				<td><%=qBean4List.get(i).getQues_ans_2() %></td>
				<td><%=qBean4List.get(i).getQues_ans_3() %></td>
				<td><%=qBean4List.get(i).getQues_ans_4() %></td>
				<td><%=qBean4List.get(i).getQues_ans_is() %></td>
				<td><%=qBean4List.get(i).getQues_category() %></td>
			</tr>
		<%	
			}
		%>
</table>  
</form >
</body>
</html>