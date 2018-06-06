var defaultSize = {width:'50%',height:'150px'};
var def_text_align = "center";
var margin = "0 auto";
//id是用于预览图片的标签的id属性名
function getPic(id,file){
    if(!validatePic(file)){
        top.layer.alert("你选择的不是图片,请选择图片",{icon:2});
        return false;
    }
    var preCss = {'text-align': def_text_align,
        'cursor': 'pointer',
        'margin':margin,
        'border-radius': '1%',
        "background":"none",
        // 'filter':'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
        'width':defaultSize.width,
        'height': defaultSize.height};
    if((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)){
        $("#"+id).html("<div id='preview' onclick='uploadPic()'></div>");
        $("#preview").css(preCss);
        $("#preview")[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + file.value + "')";

        // document.getElementById("preview").filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = file.value;
    }else {
        var url = getObjectURL(file.files[0]);
        if (url) {
            $("#"+id+" img").attr("src", url);
        }
    }
}
function renderPic(id,size,text_align,dxlx) {
    if(size && size!=undefined){
        defaultSize.width = size[0];
        defaultSize.height = size[1];
    }
    if(text_align && text_align!=undefined){
        def_text_align = text_align;
        margin = "";
    }
    // $("#"+id).after('<input id="upload" name="tp" accept="image/*;" type="file" onchange="changePic(this,'+dxlx+')" style="visibility: hidden" />');
    // $("#tpForm").append('<input id="upload" name="tp" accept="image/*;" type="file" onchange="changePic(this,'+dxlx+')" style="visibility: hidden" />');
}
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
function validatePic(file) {
    var response = true;
    var pic= file.value;
    var strs = pic.split('.');
    var suffix = strs [strs .length - 1];
    if (suffix != 'jpg' && suffix != 'gif' && suffix != 'jpeg' && suffix != 'png') {
        file.outerHTML = file.outerHTML;
        response = false;
    }
    return response;
}
/*function changePic(file,dxlx) {
    alert(2)
    $("#tpForm").ajaxSubmit({
        url:"/ggfb/getHtml?dxlx="+dxlx,
        success: function (ar) {
            if (ar) {
               $("#pic").replaceWith(ar);
            }else {
                top.layer.alert("保存失败", {icon: 2});
            }
        }
    });
    // getPic("tdPic", file);
}*/
function uploadPic() {
    $("#upload").click();
}
function getFileValue() {
    var file = $("#upload");
    if (window.navigator.userAgent.indexOf("MSIE")>=1){
        return file[0].value;
    }
    return file.val();
}