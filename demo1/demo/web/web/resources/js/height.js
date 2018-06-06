//页面初始化，页面全部dom节点载入后执行的方法
$(function () {
    mainWidthHeightLoad();
    formWidthHeightLoad();
});

//页面大小改变时，触发jquery的resize方法，自适应拖拽
$(window).resize(function () {
    mainWidthHeightLoad();
    formWidthHeightLoad();
});

//初始化内容区域宽高度
function mainWidthHeightLoad() {
    //初始化内容区域宽高度
    var height = 8;
    var o = $(".OperationBG");
    if (o && o != undefined && o != null && o.length > 0 && o.css("display") != "none") {
        height += o.outerHeight();
    }
    o = $(".conditions");
    if (o && o != undefined && o != null && o.length > 0 && o.css("display") != "none") {
        height += o.outerHeight();
    }
    o = $(".TableHeader");
    if (o && o != undefined && o != null && o.length > 0 && o.css("display") != "none") {
        height += o.outerHeight();
    }
    $(".BoxGenerallist").height($(window).height() - height);
}

//初始化内容区域宽高度
function formWidthHeightLoad() {
    //初始化内容区域宽高度
    var height = 10;
    var o = $(".WindowMenu");
    if (o && o != undefined && o != null && o.length > 0 && o.css("display") != "none") {
        height += o.outerHeight();
    }
    $(".Boxform").height($(window).height() - height);
}