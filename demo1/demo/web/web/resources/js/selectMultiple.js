//存储对象json
var selectMultipleDatas = {};

/**
 * 创建selectMultiple,带复选框
 * @param options
 */
(function ($, window) {
    $.fn.selectMultiple = function (options) {
        //初始化参数
        options = options || {};
        var $this = $(this)[0];
        var id = $this.id;
        var name = options.name ? options.name : $this.name;
        options.data = options.data ? options.data : [];//选项数据JSON
        options.nameKey = options.nameKey ? options.nameKey : "MC";//选项数据JSON中名称的key
        options.valueKey = options.valueKey ? options.valueKey : "ID";//选项数据JSON中主键的key
        options.valueId = options.valueId ? options.valueId : name + "Id";//存储选项数据JSON中主键值的INPUT元素ID
        options.delimiter = options.delimiter ? options.delimiter : ",";//分隔符
        options.width = options.width ? parseInt((options.width + "").replace("px", ""), 10) : $this.offsetWidth;
        options.width = (options.width < 155) ? 155 : options.width;//控件宽度，不能小于155
        //初始化控件
        this._init = function () {
            //样式设置
            var _cssDiv1 = "style='width:" + options.width + "px;border:1px solid #96C2F1;background:#ffffff;"
                + "font:normal 12px arial;z-index:999;position:absolute;display:none;'";
            var _cssDiv2 = "style='width:" + options.width + "px;max-height:250px;text-align:left;word-break:keep-all;white-space:nowrap;overflow:auto;'";
            var _cssDiv3 = "style='width:" + options.width + "px;height:24px;text-align:right;margin-top:2px;background-color:#E2EAF8;'";
            var _cssButton = "style='width:30px;height:20px;color:black;border:#2C59AA 1px solid;padding:1px;margin-top:2px;font-size:12px;cursor:pointer;'";
            //添加元素
            var html = "<div id='d1" + name + "' " + _cssDiv1 + " class='selectMultiple" + name + "'>"//selectDiv
                    + "<div id='d2" + name + "' " + _cssDiv2 + " class='selectMultiple" + name + "'></div>"//选项div
                    + "<div id='d3" + name + "' " + _cssDiv3 + " class='selectMultiple" + name + "'>"//按钮div
                    + "<input " + _cssButton + " type='button' id='butAll" + name + "' value='全选' class='noline selectMultiple" + name + "'/>&nbsp;"
                    + "<input " + _cssButton + " type='button' id='butClear" + name + "' value='清空' class='noline selectMultiple" + name + "'/>&nbsp;"
                    + "</div></div>";
            //放置页面中
            $("body").append(html);
        };
        //初始化控件
        this._init();
        //点击事件
        $(this).click(function () {
            //加载选项数据
            var d1 = $("#d1" + name);
            var d2 = $("#d2" + name);
            //设置绝对位置
            d1.css({
                left: $(this).offset().left + "px",
                top: ($(this).offset().top + $(this).outerHeight()) + "px"
            });
            //加载选项数据
            if (!selectMultipleDatas[name]) {
                if (options.data && options.data.length > 0) {
                    var html = "", line = "", check = "";
                    for (var i = 0; i < options.data.length; i++) {
                        line = (i == 0 ? "border-top:1px dashed #8EBAE6;" : "");
                        check = ((options.delimiter + $("#" + options.valueId).val() + options.delimiter)
                            .indexOf(options.data[i][options.valueKey]) >= 0 ? "checked='checked'" : "");
                        html = "<div class='d4 selectMultiple" + name + "' onmouseover='this.style.backgroundColor=\"#EEF3F7\"' onmouseout='this.style.backgroundColor=\"#ffffff\"' "
                            + "onclick='setValue(this,\"" + options.delimiter + "\",\"" + options.valueId + "\",\"" + id + "\");' style='padding-left:2px;width:" + options.width
                            + "px;cursor:pointer;word-break:keep-all;white-space:nowrap;border-bottom:1px dashed #8EBAE6;line-height:20px;" + line + ";padding-top:3px;'>"
                            + "<input type='checkbox' onclick='this.checked=!this.checked' " + check + " value='" + options.data[i][options.valueKey] + "' class='noline selectMultiple" + name + "'>"
                            + "&nbsp;<span class='d4span selectMultiple" + name + "'>" + options.data[i][options.nameKey] + "</span><div>";
                        d2.append(html);
                    }
                }
                //是否存在标志储存
                selectMultipleDatas[name] = name;
                //绑定按钮事件
                var vo = $("#" + options.valueId);
                var no = $(this);
                $("#butAll" + name).live("click", function () {//全选
                    var chs = d2.find("input[type='checkbox']");
                    chs.attr("checked", "checked");
                    var values = "", names = "";
                    chs.each(function () {
                        values += $(this).val() + options.delimiter;
                        names += $(this).next("span").text() + options.delimiter;
                    });
                    vo.val(values.substr(0, values.length - 1));
                    no.val(names.substr(0, names.length - 1));
                    no.attr("title", no.val());
                });
                $("#butClear" + name).live("click", function () {//清空
                    d2.find("input[type='checkbox']").removeAttr("checked");
                    vo.val(""); no.val(""); no.attr("title", "");
                });
                //选项宽度设置
                var allHeight = 0;
                var maxWidth = d2.width();
                d2.find(".d4span").each(function () {
                    if (maxWidth < this.offsetWidth) {
                        maxWidth = this.offsetWidth + 37;
                    }
                    allHeight += this.offsetHeight;
                });
                if (allHeight > d2.height()) {
                    maxWidth = maxWidth - 19;
                }
                d2.find(".d4").css("width", maxWidth + "px");
                if (maxWidth <= d2.width()) {
                    d2.css("overflow-x", "hidden");
                }
            }
            //显示选项
            if (isIe6()) {//解决IE6下max-height的样式失效的问题
                d2.css("height", "250px");
            }
            d1.show();
            showSelectFrame(name);
            //绑定事件
            $("body").bind("mouseover", function (event) {
                onSelectOver(event, $this.id, name);
            });
        });
    }
})(jQuery, window);

//对象判断
function onSelectOver(event, objectId, name) {
    if (!(event.target.id == objectId || event.target.id == ("selectFrame" + name)
        || (event.target.className && event.target.className.indexOf("selectMultiple" + name) >= 0))) {
        hideSelectFrame(name);
        $("#d1" + name).hide();
        $("body").unbind("mouseover", function (event) {
            onSelectOver(event, objectId, name);
        });
    }
}

//设值
function setValue(obj, delimiter, valueId, nameId) {
    var inx = 0;//位移变量
    var checked = !obj.firstChild.checked;
    obj.firstChild.checked = checked;
    var vo = $("#" + valueId), no = $("#" + nameId);
    var values = vo.val(), names = no.val();
    var v = delimiter + obj.firstChild.value + delimiter;
    var n = delimiter + $(obj.firstChild).next().text() + delimiter;
    if ((delimiter + values + delimiter).indexOf(v) < 0) {
        if (values == "") {
            inx = 1;
        }
        values = values + v;
        names = names + n;
    } else {
        inx = 1;
        values = (delimiter + values + delimiter).replace(v, ",");
        names = (delimiter + names + delimiter).replace(n, ",");
    }
    vo.val(values.substr(inx, values.length - 1 - inx));
    no.val(names.substr(inx, names.length - 1 - inx));
    no.attr("title", no.val());
}

//在有OFFICE控件页面时调用
function showSelectFrame(name) {
    var select = $("#d1" + name);
    var width = parseInt(select.css("width").replace("px", ""), 10) + 3;
    var height = parseInt(select.css("height").replace("px", ""), 10) + 3;
    var left = parseInt(select.css("left").replace("px", ""), 10);
    var top = parseInt(select.css("top").replace("px", ""), 10);
    var selectFrame = $("#selectFrame" + name);
    if(selectFrame.length <= 0){
        var html = '<iframe id="selectFrame' + name + '" frameborder="0" scrolling="no" style="' +
            'position:absolute;width:' + width + 'px;height:' + height + 'px;top:' + top + 'px;left:' +
            left + 'px;_filter:alpha(opacity=0);opacity=0;"></iframe>';
        $("body").append(html);
    }else{
        selectFrame.css({width: width + "px", height: height + "px", left: left + "px", top: top + "px"});
    }
}

//在有OFFICE控件页面时调用
function hideSelectFrame(name) {
    var selectFrame = $("#selectFrame" + name);
    if(selectFrame.length > 0){
        $("#selectFrame" + name).remove();
    }
}

//通用单位下拉多选
function dwdx(dwmcid, dwidid, data) {
    $("#" + dwmcid).selectMultiple({
        width: 250,
        data: data,
        nameKey: "jgjc",
        valueKey: "id",
        valueId: dwidid
    });
}