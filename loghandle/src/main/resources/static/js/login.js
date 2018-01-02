$(document).ready(function(){
    $("#submit").click(function(){
        var userName = $("#userName").val();
        var password = $("#password").val();
        if (userName=='') {
            alert("用户名不能为空");
        } else if(password=='') {
            alert("密码不能为空");
        }else {
            // alert("提交表单");
            $("form").submit();
        }
    });
});// JavaScript Document