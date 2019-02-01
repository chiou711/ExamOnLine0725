<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="qBean" class="qBankBean.QBean" scope="application" />
<jsp:setProperty property="*" name="qBean"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查詢題庫內容</title>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>  
<%@ page import="qBankBean.QListBean"%> 
<%@ page import="qBankBean.QListBeanService"%> 
</head>
<style>body {background-color:#70c0c0;}</style>
<body>
<%
	List<QListBean> qBean4List = (List<QListBean>) request.getAttribute("qBeanListService");
%>
<h3>選擇查詢方式</h3>
<form action="ReadQ.do" method="post" >
 	<input type="radio" name="radioBy" value="ReadById" value="ReadById" CHECKED/>
 	1.以題庫ID讀取：<input type="text" name="q_id" value='${qBean.ques_id}'><br>
 	
 	<input type="radio" name="radioBy" value="ReadAll"/>
 	2.讀取全部：<br> 
	<div align="left" ><input  type="submit" value="讀取"></div><br>
	
	<c:choose>
      <c:when test="${qBean.readBy == 'byId'}">
		<table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
			<tr>
				<th>題目內容</th>
				<td>${qBean.ques_id}</td>
				<td>${qBean.ques_body}</td>
				<td>${qBean.ques_ans_1}</td>
				<td>${qBean.ques_ans_2}</td>
				<td>${qBean.ques_ans_3}</td>
				<td>${qBean.ques_ans_4}</td>
				<td>${qBean.ques_ans_is}</td>
				<td>${qBean.ques_category}</td>
				</tr>
		</table>
      </c:when>
    
      <c:when test="${qBean.readBy == 'byAll'}">
		<table style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
		<tr>
		<th>題目ID</th>
		<th>考卷題目</th>
		<th>選擇1</th>
		<th>選擇2</th>
		<th>選擇3</th>
		<th>選擇4</th>
		<th>答案</th>
		<th>分類</th>
		</tr>
		<%
		if(qBean4List != null)
		{
			for(int i=0;i<qBean4List.size();i++)
		{%>
			<tr>
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
		}}
		%>

		</table>  
       </c:when>
    
       <c:otherwise>
       <div>未選取讀取方式</div>
       </c:otherwise>
       </c:choose>
       
</form>

</body>
</html>