function trim(str){
return str.replace(/(^\s*)|(\s*$)/g, "");
}

/*Error message Tooltips*/
$(document).ready(function(){
	/*点击隐藏错误提示,如果不想让提示点击消失,需要加上class='not_click_hide'*/
	$('.control-group input').not('.not_click_hide').focus(function(){
		hideTooltips($(this).parent().parent().attr('id'));
	});
});
/*
 *msgid:想让tooltips出现的地方的id
 *msg:提示的内容
 *time:自动消失的时间，如果不想让提示自动消失，则此参数不写
 */
function showTooltips(msgid,msg,time){
	if (msgid==''){ return; }
	if (msg==''){ msg='Error!'; }
	$('#'+msgid).prepend("<div class='for_fix_ie6_bug' style='position:relative;'><div class='tooltips_main'><div class='tooltips_box'><div class='tooltips'><div class='msg'>"+msg+"</div></div><div class='ov'></div></div></div></div>");
	$('#'+msgid+' .tooltips_main').fadeIn("slow").animate({ marginTop: "-23px"}, {queue:true, duration:400});
	setTimeout(function () {
		$(".for_fix_ie6_bug").hide();
	}, 1000);
	try{
		if(typeof time != "undefined"){
			setTimeout('hideTooltips("'+msgid+'")',time);
		}
	}catch(err){}

}
function hideTooltips(msgid){
	try{
		$('#'+msgid).find('.tooltips_main').fadeOut("slow");
		$('#'+msgid).find('.tooltips_main').remove();
	}catch(e){}
}
function hideAllTooltips(){
	$('.tooltips_main').fadeOut("slow");
	$('.tooltips_main').remove();
}

/*
 *msgid:想让tooltips出现的地方的id，是id对象内部追加的方式
 *msg:提示的内容
 *time:自动消失的时间，如果不想让提示自动消失，则此参数不写
 */
function showTooltips2(msgid,msg,time){
    if (msgid==''){ return; }
    if (msg==''){ msg='Error!'; }
    $('#'+msgid).append("<div class='for_fix_ie6_bug' style='position:relative;'><div class='tooltips_main'><div class='tooltips_box'><div class='tooltips'><div class='msg'>"+msg+"</div></div><div class='ov'></div></div></div></div>");
    $('#'+msgid+' .tooltips_main').fadeIn("slow").animate({ marginTop: "-23px"}, {queue:true, duration:400});
    try{
        if(typeof time != "undefined"){
            setTimeout('hideTooltips("'+msgid+'")',time);
        }
    }catch(err){}

}
/*End error message tooltips*/
