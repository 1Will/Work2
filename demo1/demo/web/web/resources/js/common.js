$(function () {
    $("#selectAll").live("click", function () {
        if ($("#selectAll:checked").size() == 1) {
            $("input[name='selectOne']").attr("checked", true);
        } else {
            $("input[name='selectOne']").attr("checked", false);
        }
    });
    $("input[name='selectOne']").live("click", function () {
        if ($("input[name='selectOne']:checked").size() == $("input[name='selectOne']").size()) {
            $("#selectAll").attr("checked", true);
        } else {
            $("#selectAll").attr("checked", false);
        }
    });
});

//-------------设置点击以后的样式      、
function ChangeStyle(name, object,number,sum) {
    var Links = document.getElementsByName(name);
    for (var i = 0; i < Links.length; i++)
        Links[i].className = '';
    object.className = 'ClickStyle';

    //点击主菜单，显示子菜单
    if (number != '') {
        if (number != '0') {
            for (var i = 1; i <= sum; i++) {
                if (number == i) {
                    with (document.getElementById('SubSideBarNav' + number).style)
                    { display = (display == 'none' || display == '') ? 'block' : 'none'; };
                    var tempObj = document.getElementById('SubSideBarNav' + number);
                    if (tempObj.style.display == 'none')
                    //document.getElementById('SubSideSpan' + number).className = "sj";
                        document.getElementById('SubSideSpan' + number).className = "sj_white";
                    else
                        document.getElementById('SubSideSpan' + number).className = "sj_down";
                } else {
                    document.getElementById('SubSideBarNav' + i).style.display = 'none';
                    //document.getElementById('SubSideSpan' + i).className = "sj";
                }
            }
        }
    }else{
        for (var i = 1; i <= sum; i++) {
            document.getElementById('SubSideBarNav' + i).style.display = 'none';
            document.getElementById('SubSideSpan' + i).className = "sj";
        }
    }

}
//弹出窗口
function openPage(options, full) {
    var index = null;
    var defaults = {
        title: "弹出窗口",
        area: ["600px", "400px"],
        type: 2,
        maxmin: true,
        scrollbar: true,
        moveEnd: function () {
            // showOfficeFrame(index);
        },
        end: function () {
            // hideOfficeFrame(index);
        }
    };
    defaults = $.extend(false, {}, defaults, options);
    index = top.layer.open(defaults);
    if (full) {
        layer.full(index);
    }
    // showOfficeFrame(index);
}

//关闭dialog弹出层
function closeDialog(name) {
    top.dialogs[name].close();
    window.focus();
}

//打开dialog提示信息,自动关闭
function dialogAlert(content, obj, timeout, width) {
    var options = {
        title: "提示信息",
        content: content,
        width: width || 280,
        timeout: timeout || 1000,
        obj: obj || null,
        onClose: function () {
            if (obj != undefined && obj != null) {
                obj.focus();
            } else {
                window.focus();
            }
        }
    };
    $.messageBox.alert(options);
}

//打开dialog提示信息,不自动关闭
function dialogAlert2(content, obj, width) {
    var options = {
        title: "提示信息",
        content: content,
        width: width || 280,
        obj: obj || null,
        onClose: function () {
            if (obj != undefined && obj != null) {
                obj.focus();
            } else {
                window.focus();
            }
        }
    };
    $.messageBox.alert(options);
}

//打开dialog确认信息
function dialogConfirm(content, onokClose, onClose, width) {
    var options = {
        title: "确认信息",
        content: content,
        width: width || 280,
        onokClose: onokClose,
        onClose: onClose || function () {
            window.focus();
        }
    };
    $.messageBox.confirm(options);
}

function topDialogConfirm(content, onokClose, onClose, width) {
    var options = {
        title: "确认信息",
        content: content,
        width: width || 280,
        onokClose: onokClose,
        onClose: onClose || function () {
            window.focus();
        }
    };
    top.$.messageBox.confirm(options);
}

//询问框
function openConfirm(title, confirm, cancel) {
    var index = layer.confirm(title, {
            title: "确认信息",
            moveEnd: function () {
                showOfficeFrame(index);
            },
            end: function () {
                hideOfficeFrame(index);
            }
        },
        //确定
        function(inx) {
            hideOfficeFrame(inx);
            layer.close(inx);
            if(confirm && typeof confirm == "function") {
                confirm();
            }
        },
        //取消
        function(inx) {
            hideOfficeFrame(inx);
            layer.close(inx);
            if(cancel && typeof cancel == "function") {
                cancel();
            }
        });
    showOfficeFrame(index);
}

//关闭窗口
function closePage() {
    var index = parent.layer.getFrameIndex(window.name);
    // parent.hideOfficeFrame(index);
    parent.layer.close(index);
}

//获取父页面对象
function parentPage() {
    return window.parent;
}

//在有OFFICE控件页面时调用
function showOfficeFrame(index) {
    var layero = $("#layui-layer" + index);
    var width = parseInt(layero.css("width").replace("px", ""), 10) + 3;
    var height = parseInt(layero.css("height").replace("px", ""), 10) + 3;
    var left = parseInt(layero.css("left").replace("px", ""), 10);
    var top = parseInt(layero.css("top").replace("px", ""), 10);
    var officeFrame = $("#officeFrame" + index);
    if(officeFrame.length <= 0){
        var html = '<iframe id="officeFrame' + index + '" frameborder="0" scrolling="no" style="'
            + 'position:absolute;width:' + width + 'px;height:' + height + 'px;top:' + top + 'px;left:'
            + left + 'px;_filter:alpha(opacity=0);opacity=0;"></iframe>';
        $("body").append(html);
    }else{
        officeFrame.css({width: width + "px", height: height + "px", left: left + "px", top: top + "px"});
    }
}

//在有OFFICE控件页面时调用
function hideOfficeFrame(index) {
    var officeFrame = $("#officeFrame" + index);
    if(officeFrame.length > 0){
        $("#officeFrame" + index).remove();
    }
}

//执行结果
var ZXJG = {PASS: "0", REJECTED: "1", DISABLE: "2", VERIFIED: "3", INVALID: "4", ERROR: "5"};
//有效状态
var YXZT = {WX: {name: "注销", value: "0"}, YX: {name: "启用", value: "1"}, TY: {name: "停用", value: "2"}};
//执行标识
var ZXBZ = {SUCCESS: "0", ERROR: "1", FAILURE: "2"};//执行标志

