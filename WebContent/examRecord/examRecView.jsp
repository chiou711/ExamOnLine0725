<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="erBean" class="examRecordBean.ERecBean" scope="application" />
<jsp:setProperty property="*" name="erBean" />
<jsp:useBean id="erBeanSrv" class="examRecordBean.ERecBeanService" scope="application" />
<jsp:setProperty property="*" name="erBeanSrv" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查詢考生資料</title>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="examRecordBean.ERecListBean"%>
<%@ page import="examRecordBean.ERecListBeanService"%>
<style>body {background-color:#70c0c0;}</style>
</head>
<body>
	<%
		List<ERecListBean> erList = (List<ERecListBean>) request.getAttribute("erListBeanService");
	%>
	<h3>選擇查詢方式</h3>
	<form action="ReadEr.do" method="post">
		<input type="radio" name="radioBy" value="ReadById" value="ReadById" CHECKED />
		 1.以考生ID讀取：<input type="text" name="stuExID" value="A00123"><br>
		<input type="radio" name="radioBy" value="ReadAll" /> 
		 2.讀取全部：<br>
		<div align="left">
		<input type="submit" value="讀取">
		</div>
		<br>

		<c:choose>
			<c:when test="${erBeanSrv.readBy == 'byId'}">
				<h3>考生資料內容</h3>
				<table style="text-align: left;" border="1" cellpadding="2"
					cellspacing="2">
					<tr>
						<th>考生ID</th>
						<th>考卷ID</th>
						<th>考生答題</th>
						<th>考生分數</th>
					</tr>
					<tr>
						<td>${erBean.erId}</td>
						<td>${erBean.erEpaperId}</td>
						<td>${erBean.erAnsList}</td>
						<td>${erBean.erScore}</td>
					</tr>
				</table>
			</c:when>

			<c:when test="${erBeanSrv.readBy == 'byAll'}">
				<table style="text-align: left;" border="1" cellpadding="2"
					cellspacing="2">
					<tr>
						<th>考生ID</th>
						<th>考卷ID</th>
						<th>考生答題</th>
						<th>考生分數</th>
					</tr>
					<%
						if (erList != null) {
									for (int i = 0; i < erList.size(); i++) {
					%>
					<tr>
						<td><%=erList.get(i).getErId()%></td>
						<td><%=erList.get(i).getErEpaperId()%></td>
						<td><%=erList.get(i).getErAnsList()%></td>
						<td><%=erList.get(i).getErScore()%></td>
					</tr>
					<%
						}
								}
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