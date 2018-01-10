$(function(){
   			$.get('/historyLogErroList',function(data){
   				$("#list1").text(data.list1);
	           	$("#list2").text(data.list2);
	           	$("#list3").text(data.list3);
   			});
   
           $.get('/historyErrorAndWarningNumber',function(data){
           	var e = data.errorNumber;
           	var t = data.timeOutWarningNumber;
           	$("#e").text(e);
           	$("#t").text(t);
           });
           
        });
		


        