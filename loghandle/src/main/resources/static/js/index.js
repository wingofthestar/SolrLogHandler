$(function(){
    $("#flash").click(function(){
        var mytime = new Date();
        var time = mytime.getTime();
        console.log(time);

        $.get('/initSolrAndDataBase',{'time':time},function(data){

        });
        $(".progress-bar").delay(1000).animate({width:"50%"});
        $(".progress-bar").delay(1000).animate({width:"70%"});
        $(".progress-bar").delay(1000).animate({width:"100%"});
        function modalClose() {
            $('#myModal').modal('hide');
            $(".modal-backdrop").remove();
            $("body").removeClass('modal-open');
        }
        setTimeout(modalClose, 5000);
        function back(){
            location.reload();
        }
        setTimeout(back,5000);
    });

});