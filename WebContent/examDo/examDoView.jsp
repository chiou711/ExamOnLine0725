<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="qBean" class="qBankBean.QBean" scope="application" />
<jsp:setProperty property="*" name="qBean" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>進行線上測驗</title>
<%@ page import="java.util.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="qBankBean.QListBean"%>
<%@ page import="qBankBean.QListBeanService"%>
<%@ page import="qExamPaperBean.QExPaperBean"%>
<%@ page import="qExamPaperBean.QExPaperBeanService"%>
<script type="text/javascript" src="../jquery-1.10.1.js"></script>
<script>
function ansIs(sel,i){
	var prefix = "ans";
	var Id;
	Id = prefix + i;
    document.getElementById(Id).value = sel;
    $("td").closest('input:radio').addClass("checked");;
}  
//for countdown
var countDownMin=40; //minutes
var countDownSec=0;
var countDownId;

function initial(){
  countDownId=window.setInterval (countdownfunc,1000);
}

function countdownfunc(){
	var xMin=document.getElementById("countDownMin");
    var xSec=document.getElementById("countDownSec");

	if ((countDownMin==0) && (countDownSec==0)){
	alert("考試時間結束");
	clearInterval(countDownId);
    //launch submit
    document.examForm.stuExScore.click();
  }
  
	if(countDownSec>0)
  	countDownSec--;

	xMin.innerHTML=countDownMin;
	xSec.innerHTML=countDownSec;
  
  if((countDownMin>0) && (countDownSec==0)) {
		countDownSec=60;//60;
		countDownMin--;
	}
}
</script>
<style>
tr:hover {background-color:#ccffaa;}   /* mouse over link */
.Q:hover {background-color:#00ffaa;}   /* mouse over link */
/*input[type="radio"]:checked+label{ font-weight: italic; }*/ 
input[type="radio"]:checked+label{ font-weight: bold; background-color: #ffcc00;} 
.toCenter { 
    text-align: center;
}
body {background-color:#70c0c0;}

</style>  
</head>
<body  onload="initial();">
	<%
		request.setCharacterEncoding("utf-8");
		    	response.setContentType("text/html;charset=utf-8");

		String stuExID = request.getParameter("stuExID");
		String epId = request.getParameter("epId");
			
		//QBean4List qBean4L = new QBean4List();
		QListBeanService.DB_connect();
		QListBeanService qBServBean = new QListBeanService();
		List<QListBean> qBean4List = qBServBean.getqContentList();
		QExPaperBean epBean = new QExPaperBean();
		QExPaperBeanService epBeanSrv = new QExPaperBeanService();
		epBeanSrv.DbConnect();
		//Select exam paper
		epBeanSrv.selectEP(epId);
		
		// score
		List<String> rightAnsList =new ArrayList<String>();
	%>
	<form name="examForm" action="ExamScore.do" method="post" target="_blank" >
		<table
			style="border: 5px solid rgb(0, 0, 120); background-color: rgb(240, 255, 255); font-size:18px;margin: auto;"
			border="1" bordercolor="rgb(0,0,30)" cellpadding="2" cellspacing="2">
			<caption style="background-color: #bbcc00; border: 1px solid rgb(0, 0, 120);font-size:20px; text-align:left;">考生准考證號碼:<%=stuExID%></caption>
			<caption style="background-color: #bbcc99; border: 1px solid rgb(0, 0, 120);font-size:20px; text-align:right">考卷編號:  <%=epId%></caption>
			<tr>
				<th>作答</th>
				<th>題號</th>
				<th>問題</th>
				<th>答 ①</th>
				<th>答 ②</th>
				<th>答 ③</th>
				<th>答 ④</th>
				<!-- <th>答案</th> -->
			</tr>

			<%
				// ID list: split to array
				String[] EPaperArr = epBean.getEPaper().split("[;]");
				String[] examAnsArr = new String[EPaperArr.length];
				QListBean qBean4L = new QListBean();
				QListBeanService qBean4ListSrv = new QListBeanService();
				for (int i = 0; i < EPaperArr.length; i++) {
					qBean4L = qBean4ListSrv.read_qBank_by_ID(EPaperArr[i]);
					rightAnsList.add(qBean4L.getQues_ans_is());
					String idStr = String.format("ans%s",  String.valueOf(i));
			%>
			<tr>
				<td ><input id=<%=idStr.toString()%> class="toCenter"  type="text" name="examAns" size="1" value="" 
				            style="font-size:18px;background-color: #bbcccc;" onfocus="this.blur()" readonly></td>
				<td class="toCenter"><%=i+1%></td>
				<td  ><%=qBean4L.getQues_body()%></td>
				<td class="Q" >
			    <input id="a1<%=i%>" type="radio" name="varAns<%=i%>" onClick="ansIs(1,<%=i%>);" ><label for="a1<%=i%>">①<%=qBean4L.getQues_ans_1()%></label><!-- 1: `+u+2460 -->
			    </td>
			    <td class="Q" >
			    <input id="a2<%=i%>" type="radio" name="varAns<%=i%>"  onClick="ansIs(2,<%=i%>);" ><label for="a2<%=i%>">②<%=qBean4L.getQues_ans_2()%></label><!-- 2: `+u+2461 -->
			    </td>
			    <td class="Q" >
			    <input id="a3<%=i%>" type="radio" name="varAns<%=i%>"  onClick="ansIs(3,<%=i%>);" ><label for="a3<%=i%>">③<%=qBean4L.getQues_ans_3()%></label><!-- 3: `+u+2462 -->
			    </td>
			    <td class="Q" >
			    <input id="a4<%=i%>" type="radio" name="varAns<%=i%>"  onClick="ansIs(4,<%=i%>);" ><label for="a4<%=i%>">④<%=qBean4L.getQues_ans_4()%></label><!-- 4: `+u+2463 -->
			    </td>            
				<!-- <td class="toCenter"><%=qBean4L.getQues_ans_is()%></td> -->
			</tr>
        		<%}%>
		</table>
		<br>
	<div class="toCenter">
	<input   type="submit" name="stuExScore" value="測驗成績" 
	         style="font-size:18px;background-color: #bbcc99;" 
	         onclick =
	"<% session.setAttribute("rightAnsList", rightAnsList);
		session.setAttribute("stuExID", stuExID);
		session.setAttribute("epId", epId);
	%>;window.clearInterval(countDownId)">
	</div>
	</form>
	<br>
	<input type="button" value="離開" style="font-size:18px;background-color: #bbcc99;" onclick="window.open('','_parent','');window.close()"  /> 
	<div><p>考試時間倒數:</p></div>
   <label id="countDownMin"></label>分
   <label id="countDownSec"></label>秒
</body>
</html>