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
body {background-color:#FFFFCC;}
.highlight
{
background-color:yellow;
}
th
{
background-color:#00FFCC;
}
</style>
<script>
function highLight(text)
{
	var t = document.getElementById("tbl");
	for (var r = 0; r < t.rows.length; r++)
	{
	    for (var c = 0; c < t.rows(r).cells.length; c++) 
	    {
        	var srchCellText =  t.rows(r).cells(c).innerHTML;
		    var index = srchCellText.indexOf(text);
		    if ( index >= 0 )
		    { 
            	srchCellText = srchCellText.substring(0,index) + 
		                                  "<span class='highlight'>" + srchCellText.substring(index,index+text.length) + "</span>" + 
		                                  srchCellText.substring(index + text.length);
		       t.rows(r).cells(c).innerHTML = srchCellText;
		    }
	    }
	}
}
</script>
</head>
<body onload="highLight('<%=epBeanSrv.getEPaperKeyword()%>')">
<%
	QListBeanService.DB_connect();
	QListBeanService qBServBean =new QListBeanService();	
	List<QListBean> qBean4List = qBServBean.getqContentList();
%>
<h3>考題搜尋</h3>

<form action="QExPaperMake.do" method="post" target="Win1Btm" name="QExPaperMakeForm2">
輸入關鍵字:<input type="text" name="EpKeyword" value="<%=epBeanSrv.getEPaperKeyword()%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
列出內含此關鍵字的考題:<input type="submit" name="EditSearch"   value="列出" ><br>
<table id="tbl" style="text-align: left;" border="1" cellpadding="2" cellspacing="2">
		<tr>
		<!-- <th>勾選</th> -->
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
			if( (qBean4List.get(i).getQues_body().contains(epBeanSrv.getEPaperKeyword()) ) ||
				 (qBean4List.get(i).getQues_ans_1() .contains(epBeanSrv.getEPaperKeyword()) ) ||
				 (qBean4List.get(i).getQues_ans_2() .contains(epBeanSrv.getEPaperKeyword()) ) ||
				 (qBean4List.get(i).getQues_ans_3() .contains(epBeanSrv.getEPaperKeyword()) ) ||
				 (qBean4List.get(i).getQues_ans_4() .contains(epBeanSrv.getEPaperKeyword()) )       	)
			{
		%>
			<tr>
				<!-- <td><input type = "checkbox" name="EPaperArr" value= <%=qBean4List.get(i).getQues_id() %> ></td> -->
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
</form>
</body>
</html>