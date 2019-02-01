$(document).ready(function() {
	$(".blink").blink(); // default is 500ms blink interval.
	$("h2:last").addClass("blink");
	
	$("#applyForm").submit(function(e) {
		e.preventDefault();
		});
	$("#finishButton").click(function(e) {
		
		formCheck(appForm);
		function formCheck(inFm){
			str="";
			if(inFm.stud_name.value.length<2)
			{
				str+="- 姓名長度必須需大於1位\n";
			}
			
			if(!idCheck())
			{
				str+="- 身分證號碼有錯誤\n";
			}
			
			if(!(inFm.stud_gender[0].checked ||  inFm.stud_gender[1].checked))
			{
				str+="- 性別未選擇\n";
			}
			
			if((inFm.varYear.options[inFm.varYear.selectedIndex].value == "1960年") &&
			   (inFm.varMonth.options[inFm.varMonth.selectedIndex].value == "1月") &&	
			   (inFm.varDate.options[inFm.varDate.selectedIndex].value == "1日")   )
			{
				str+="- 生日未選擇\n";
			}
			
			if(inFm.stud_address.value.length<9)
			{
				str+="- 聯絡地址不完整\n";
			}
			
			if(inFm.stud_phone_num.value.length<7)
			{
				str+="- 聯絡電話不完整\n";
			}
			
			if(str.length>0)
			{
				alert(str);
				inFm.stud_name.focus();
				return false;
			}
			else
			{
				doAjax();
			}
		}
	
	function idCheck(){
		var city = new Array(  
				         1,10,19,28,37,46,55,64,39,73,82, 2,11,  
				        20,48,29,38,47,56,65,74,83,21, 3,12,30  
				    );
				   id = applyForm.stud_id.value.toUpperCase();  
				    // 使用「正規表達式」檢驗格式  
				    if (id.search(/^[A-Z](1|2)\d{8}$/i) == -1)
				    {  
				        alert('身分證號碼基本格式錯誤');  
				        return false;			        
				    }
				    else
				    {			    
				    	//將字串分割為陣列(IE必需這麼做才不會出錯)  
				        id = id.split('');  
				        //計算總分  
				        var total = city[id[0].charCodeAt(0)-65];  
				        for(var i=1; i<=8; i++)
				        {  
				            total += eval(id[i]) * (9 - i);  
				        }  
				        //補上檢查碼(最後一碼)  
				        total += eval(id[9]);  
				        
				        //檢查比對碼(餘數應為0);  
					     if(total%10 != 0)
					     {			        
					        alert("身分證號碼有錯誤");
					     }
					     else
					     {
					    	 //appForm.submit();
					    	 return true;
					     }
				    }
	}		

	function doAjax(){
		dataString = $("#applyForm").serialize();
		var stud_id = $("input#stud_id").val();
		stud_id_data = "stud_id="	+ stud_id;		
		var stud_name = $("input#stud_name").val();
		stud_name_data = "stud_name="	+ stud_name;
		$.ajax({
			type : "POST",
			url : "StudApply", 
			data : dataString,
			dataType : "json",
			success : function(showData,textStatus, jqXHR) {
				if (showData.success)
				{
					$("#ajaxStuApplyInfo").html("");
					$("#ajaxStuApplyInfo").append("<b>身分證號碼:</b> "
							+ showData.stuInfo.stud_id	+ "<br/>");
					$("#ajaxStuApplyInfo").append("<b>姓名:</b> "
							+ showData.stuInfo.stud_name	+ "<br/>");
					$("#ajaxStuApplyInfo").append("<b>性別:</b> "
							+ showData.stuInfo.stud_gender + "<br/>");
					$("#ajaxStuApplyInfo").append("<b>生日:</b> "	
							+ showData.stuInfo.stud_birthday + "<br/>");
					$("#ajaxStuApplyInfo").append("<b>地址:</b> "
							+ showData.stuInfo.stud_address + "<br/>");
					$("#ajaxStuApplyInfo").append("<b>電話:</b> "
							+ showData.stuInfo.stud_phone_num + "<br/>");
					$("#ajaxStuApplyInfo").append("<font color='blue'><b>准考證號碼:</b> "
							+ showData.stuInfo.stud_examinee_id + "</font><br/>");
					if(showData.stuInfo.stud_apply_again == "Yes")
					{
						$("#feedback").html("");
						$("#feedback").append("<h2 style='color:blue;'>請注意:考生已經重複報名!</h2>");
					}
				}
				else
				{
					$("#ajaxStuApplyInfo").html("<div><b>Input is Invalid!</b></div>");
				}
			},
			error : function(jqXHR,textStatus,errorThrown)
			{
				console.log("Something really bad happened "
						+ textStatus);
				$("#ajaxStuApplyInfo").html(jqXHR.responseText);
			},
			beforeSend : function(jqXHR, settings) {
				settings.data += "&dummyData=whatever";
				$('#finishButton').attr("disabled",true); //avoid 報名 2 次
			},
			complete : function(jqXHR,textStatus) {
				$('#finishButton').attr("disabled",false); //可按button
			}
			});
	}});
	
	});