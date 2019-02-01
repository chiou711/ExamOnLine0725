<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="qBean" class="qBankBean.QBean" scope="application" />
<jsp:setProperty property="*" name="qBean"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考題內容</title>
<style>body {background-color:#70c0c0;}</style>
</head>
<body>
<!-- 
<input type="radio" name="varQ_Manage" value ="Q_review">瀏覽
<input type="radio" name="varQ_Manage" value ="Q_insert">新增考題
<input type="radio" name="varQ_Manage" value ="Q_modify">既有考題內容變更
<h3>編輯考題內容:</h3>
 -->
<form action="EditQ.do" method="post" >
<h2>編輯</h2>
<div align="left" >輸入題庫ID
<input type="text" name="q_id" value='${qBean.ques_id}'>
<input  type="submit" name="QEdit" value="列出內容"><br>
建立新考題 ID<input  type="submit" name="QEdit" value="建立">
</div>
<hr size=2 color=blue width=99%>
<div align="left">
問題 <input type="text" size = "50" name="varQ" value ="${qBean.ques_body}"><br>
選擇 1<input type="text" size = "30" name="varA1" value ='${qBean.ques_ans_1}'><br>
選擇 2<input type="text" size = "30" name="varA2" value ='${qBean.ques_ans_2}'><br>
選擇 3<input type="text" size = "30" name="varA3" value ='${qBean.ques_ans_3}'><br>
選擇 4<input type="text" size = "30" name="varA4" value ='${qBean.ques_ans_4}'><br>
答案 <input type="text" size = "1" name="varCA" value ="${qBean.ques_ans_is}"><br>
分類 <input type="text" size = "30" name="varCat" value ="${qBean.ques_category}">
</div>
<hr size=2 color=blue width=99%>
<div align="center">
<c:choose>
<c:when  test="${
qBean.ques_body == null && 
qBean.ques_ans_1 == null && 
qBean.ques_ans_2 == null &&
qBean.ques_ans_3 == null &&
qBean.ques_ans_4 == null &&
qBean.ques_ans_is == null &&
qBean.ques_category == null
}">
</c:when>
<c:otherwise>
原來的內容:<input type = "reset" value = "引用">
<table style="text-align: left;" border="1" cellpadding="1" cellspacing="1" bordercolor="green">
			<tr>
			<th>原始內容</th><td>${qBean.ques_whole}</td>
			</tr>
</table>
</c:otherwise>
</c:choose>
</div>
<hr size=2 color=blue width=99%>
<h2>管理題庫:</h2>
<div>
依據目前編輯的考題內容<br>
<c:choose>
<c:when test="${
qBean.ques_body == null && 
qBean.ques_ans_1 == null && 
qBean.ques_ans_2 == null &&
qBean.ques_ans_3 == null &&
qBean.ques_ans_4 == null &&
qBean.ques_ans_is == null &&
qBean.ques_category == null
}">
.新增考題
<input type = "submit" name="QEdit" value = "新增"><br>
</c:when>
<c:otherwise>
.修改考題
<input type = "submit" name="QEdit" value = "修改"><br>
.刪除目前所選的考題
<input type = "submit" name="QEdit" value = "刪除"><br>
</c:otherwise>
</c:choose>
</div>
&nbsp;&nbsp;
</form>
		

<!-- 
困難度
<input type="radio" name="varQ_diff" value ="Q_diff_hi">難
<input type="radio" name="varQ_diff" value ="Q_diff_nor">中
<input type="radio" name="varQ_diff" value ="Q_diff_lo">易
<br>


類別
<select name ="varCat" >
<option> 第1類 </option>
<option> 第2類 </option>
<option> 第3類 </option>
<option> 第4類 </option>
<option> 第5類 </option>
<option> 第6類 </option></select><br>


題號設定
<input type="radio" name="varQ_ID_set" value ="Q_ID_auto_inc">流水號 00125
<input type="radio" name="varQ_ID_set" value ="Q_ID_manual">指定題號
<input type="text" size = "6" name="varQ_ID" value ="000124">
<p>
-->


</body>
</html>