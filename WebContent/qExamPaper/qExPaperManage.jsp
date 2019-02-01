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
<style>body {background-color:#70c0c0;}</style>
</head>
<body>
<%
	QListBeanService.DB_connect();
	QListBeanService qBServBean =new QListBeanService();	
	List<QListBean> qBean4List = qBServBean.getqContentList();
//	List<QBean4List> qBean4List = //(List<QBean4List>) request.getAttribute("qBeanListService");
%>
<h3>管理考卷</h3>
<form action="QExPaperManage.do" method="post" target="Win2">
考卷編號<input type="text" name="EpId" value="${epBeanSrv.epMaxId }">(提示為目前最大值)<br>
<input type="submit" name="ManageEp" value="查詢考卷"><br>
<input type="submit" name="ManageEp" value="刪除考卷" >
</form>
</body>
</html>