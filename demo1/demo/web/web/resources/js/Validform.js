var objPageValue = {};

//函数返回值类
function funRetVal() {
    this.eleNames = "";
}

//创建事件的通用函数
var EventUtil = function () {
};
EventUtil.addEventHandler = function (obj, EventType, Handler) {
    //如果是FF
    if (obj.addEventListener) {
        obj.addEventListener(EventType, Handler, false);
    }
    //如果是IE
    else if (obj.attachEvent) {
        obj.attachEvent('on' + EventType, Handler);
    }
    else {
        obj['on' + EventType] = Handler;
    }
}

//取消事件传入的参数值要跟绑定时完全一样才可以
EventUtil.removeEventHandler = function (obj, EventType, Handler) {
    //如果是FF
    if (obj.removeEventListener) {
        obj.removeEventListener(EventType, Handler, false);
    }
    //如果是IE
    else if (obj.detachEvent) {
        obj.detachEvent('on' + EventType, Handler);
    }
    else {
        delete obj["on" + EventType];
    }
}

EventUtil.execEvent = function (obj, eveType) {
    if (obj && (obj = "string" == typeof obj ? document.getElementById(obj) : obj)) {
        if (document.all) {
            obj.click();
        } else {
            var evt = document.createEvent("MouseEvents");
            evt.initEvent(eveType, true, true);
            obj.dispatchEvent(evt);
        }
    }
}

//页面加载时，保存页面的原始值
//传递参数为整个容器，这里我们一般都是传整个form或div的id，然后针对不同类型的html标签来获取值，最终存取到对象objPageValue里，
// 这里objPageValue的键就是元素的属性id，这个id在后面比较修改前后值就派上用场了
//这里注意一下获取几个控件的值的特殊性，如获取下拉菜单被选中的的值用的是selectedIndex
//例子参考 rkxxwh.ftl
function saveOrigValue(container) {
    container = "string" == typeof container ? document.getElementById(container) : container;
    if (!container)return;
    var sCheckedName = "";
    var eles = container.getElementsByTagName("INPUT");  //文本框类型
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].readOnly || eles[i].disabled) continue;  //对象不可编辑
        var _a = eles[i].getAttribute("type");
        if (_a == null || _a.toLowerCase() == 'text') {
            if (!eles[i].getAttribute("id")) continue;
            objPageValue[eles[i].getAttribute("id")] = eles[i].value.trim();
        }
        else if (_a.toLowerCase() == 'checkbox' || _a.toLowerCase() == 'radio') {
            var sName = eles[i].getAttribute("name"); //获取单选框，或者复选框的name属性值
            if (!sName) continue;
            if (sCheckedName.indexOf("," + sName + ",") > -1) continue; //值已经存在
            sCheckedName += "," + sName + ",";
            objPageValue[sName] = getRadioOrCheckboxVal(sName);
        }
    }
    sCheckedName = "";
    eles = container.getElementsByTagName("TEXTAREA"); //多行文本框
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].disabled) continue;  //对象不可编辑
        if (!eles[i].getAttribute("id")) continue;
        objPageValue[eles[i].getAttribute("id")] = eles[i].value.trim();
    }
    eles = container.getElementsByTagName("SELECT");  //下拉列表框
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].disabled) continue;  //对象不可编辑
        if (!eles[i].getAttribute("id")) continue;
        objPageValue[eles[i].getAttribute("id")] = eles[i].value == "" ? "" : eles[i].options[eles[i].selectedIndex].text.trim();
    }
}

//页面加载时，保存页面的原始值
//平台初始化控件以后，调用此方法
//这里主要用于页面异步加载后来获取页面控件的值 ，原来由于平台异步加载数据派基用到了，现在基本没用到。  例子参考 rkxxwh.ftl
function saveOrigValueAfter(v) {
    var container = this;
    if (!container) return;
    var sTagName = "undefined" == typeof  container.tagName ? "" : container.tagName.toLowerCase();
    switch (sTagName) {
        case "input": {  //单行文本框
            if (!container.getAttribute("id")) return;
            var _a = container.getAttribute("type");
            if (_a == null || _a.toLowerCase() == 'text') {
                objPageValue[container.getAttribute("id")] = container.value.trim();
            }
        }
            break;
        case "span": {   //单选框、复选框
            var eles = container.getElementsByTagName("INPUT");
            if (eles.length == 0)return;
            var sName = eles[0].getAttribute("name"); //获取单选框，或者复选框的name属性值
            if (!sName) return;
            objPageValue[sName] = getRadioOrCheckboxVal(sName);
            if (window.setElementDisable) {
                setCheckBoxAndRadioDisable(eles);
            }
        }
            break;
        case "textarea": {   //多行文本框
            if (!container.getAttribute("id")) return;
            objPageValue[container.getAttribute("id")] = container.value.trim();
        }
            break;
        case "select": {   //下拉列表框
            if (!container.getAttribute("id")) return;
            objPageValue[container.getAttribute("id")] = container.value == "" ? "" : container.options[container.selectedIndex].text.trim();
        }
            break;
    }
    if (window.regEleEvent && container.id && window.regEleEvent[container.id]) {
        var _e = window.regEleEvent[container.id];
        _e.method(_e.para);
    }
}

//根据表单内容设置容器是否需要滚动条
//这里主要用于加载点击tab页面的时候滚动条的出现
// 第一个参数为真个tab页所在的div的id，第二个参数为要设置的某个tab页面的div的id，第3个参数是否设置，默认true
// 这里就是比较网页的实际高度scrollHeight和整个网页的高度offsetHeight（边框和滚动条）之间的关系，          例子参考rkQuery.ftl
function setContainerScroll(container, itab, flag) {
    container = "string" == typeof container ? document.getElementById(container) : container;
    if (!container)return;
    if (flag) {
        container.style.overflowY = container.scrollHeight > container.offsetHeight ? "scroll" : "auto";
        itab.imillis = 1;
    }
    else {
        itab = document.getElementById(itab);
        if (itab && itab.imillis) {
            var _a = container.scrollHeight > container.offsetHeight ? "scroll" : "auto";
            if (_a != container.style.overflowY) {
                container.style.overflowY = _a;
            }
        }
        else {
            container.style.overflowY = "auto";
            setTimeout(function () {
                setContainerScroll(container, itab, true);
            }, 1000);
        }
    }
}

//保存时比较哪些项目被修改，并记录修改前后的值
//container：需要获取变动内容的容器
//retObj：   不为空时，返回被修改元素的name属性值，多个用英文逗号分隔
//注意：这里的objPageValue就是前面的函数saveOrigValue所存取的值，在这里就派上用场了，
// 这里通过compareValue这个方法比较修改前后的值有没有改变来获取描述日志
//还是针对不同控件获取值的方式不一样      例子参考 rkxxwh.ftl
function getEditLog(container, retObj) {
    var sLogInfo = "", sCheckedName = "";
    var eles = container.getElementsByTagName("INPUT");  //文本框类型
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].readOnly || eles[i].disabled) continue;  //对象不可编辑
        var _a = eles[i].getAttribute("type");
        if (_a == null || _a.toLowerCase() == 'text') {
            var _id = eles[i].getAttribute("id");
            if (!_id) continue;
            if ("undefined" == typeof (objPageValue[_id])) continue;  //原始值没有被保存
            sLogInfo += compareValue(objPageValue[_id], eles[i].value.trim(), eles[i], retObj);
        }
        else if (_a.toLowerCase() == 'checkbox' || _a.toLowerCase() == 'radio') {
            var sName = eles[i].getAttribute("name"); //获取单选框，或者复选框的name属性值
            if (!sName) continue;
            if ("undefined" == typeof (objPageValue[sName])) continue;  //原始值没有被保存
            if (sCheckedName.indexOf("," + sName + ",") > -1) continue; //已经比较
            sCheckedName += "," + sName + ",";
            sLogInfo += compareValue(objPageValue[sName], getRadioOrCheckboxVal(sName), eles[i], retObj);
        }
    }
    sCheckedName = "";
    eles = container.getElementsByTagName("TEXTAREA");  //多行文本框
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].disabled) continue;  //对象不可编辑
        var _id = eles[i].getAttribute("id");
        if (!_id) continue;
        if ("undefined" == typeof (objPageValue[_id])) continue;  //原始值没有被保存
        sLogInfo += compareValue(objPageValue[_id], eles[i].value.trim(), eles[i], retObj);
    }
    eles = container.getElementsByTagName("SELECT");  //下拉列表框
    for (var i = 0, len = eles.length; i < len; i++) {
        //if (eles[i].disabled) continue;  //对象不可编辑
        var _id = eles[i].getAttribute("id");
        if (!_id) continue;
        if ("undefined" == typeof (objPageValue[_id])) continue;  //原始值没有被保存
        var sNewVal = eles[i].value == "" ? "" : eles[i].options[eles[i].selectedIndex].text.trim();
        sLogInfo += compareValue(objPageValue[_id], sNewVal, eles[i], retObj);
    }
    return sLogInfo;
}

//设置控件不可编辑   参数还是传递整个div的id         例子参考 rkxxwh.ftl
function setControlDisable(container) {
    window.setElementDisable = true; //因为checkbox和radio等都是异步生成的，通过此标记来设置这些元素不可用
    container = "string" == typeof container ? document.getElementById(container) : container;
    var eles = container.getElementsByTagName("INPUT"); //文本框类型
    for (var i = 0, len = eles.length; i < len; i++) {
        var _a = eles[i].getAttribute("type");
        if (_a == null || _a.toLowerCase() == 'text') {
            eles[i].readOnly = true;
            if (eles[i].className.indexOf("TextBoxchoose") > -1) {  //有辅助选择窗口的text
                var p = eles[i];
                while (p.parentNode && p.parentNode.tagName != "DIV") {
                    p = p.parentNode;
                }
                p = p.parentNode;
                if (p && "tdAddClearImg" == p.className) {
                    while (p.parentNode && p.parentNode.tagName != "TD") {
                        p = p.parentNode;
                    }
                    if (p = p.parentNode) {
                        p.innerHTML = eles[i].parentNode.innerHTML;
                    }
                }
                $(eles[i]).unbind("click");   //jquery方法，取消绑定的click事件
            }
            else if (eles[i].className.indexOf("Wdate") > -1) {  //日期控件
                $(eles[i]).unbind("click"); //jquery方法，取消绑定的click事件
            }
        }
        else if ((_a = _a.toLowerCase()) == 'checkbox' || _a == 'radio' || _a == 'file') {
            eles[i].disabled = "disabled";
        }
    }
    eles = container.getElementsByTagName("TEXTAREA"); //多行文本框
    for (var i = 0, len = eles.length; i < len; i++) {
        eles[i].readOnly = true;
    }
    eles = container.getElementsByTagName("SELECT"); //下拉列表框
    for (var i = 0, len = eles.length; i < len; i++) {
        for (var j = 0, l = eles[i].options.length; j < l; j++) {
            if (j != eles[i].selectedIndex) {
                eles[i].options[j].disabled = true;
            }
        }
    }
}

//设置异步加载的checkbox和radio不可用
function setCheckBoxAndRadioDisable(eles) {
    for (var i = 0, len = eles.length; i < len; i++) {
        eles[i].disabled = "disabled";
    }
}

//获取obj对应的项目名称，即其对应<th>标签中的文本内容
function getItemName(obj) {
    var p = obj;
    while (p.parentNode && p.parentNode.tagName != "TD") {
        p = p.parentNode;
    }
    if (p.parentNode == null) return "";
    var oTd = (p = p.parentNode);
    while (p.previousSibling && p.previousSibling.tagName != "TH") {
        p = p.previousSibling;
    }
    if (p.previousSibling == null) { //td之前没有th标签，可能是checkbox列表（checkbox列表通常会有table显示）
        p = oTd;
        while (p.parentNode && p.parentNode.tagName != "TD") {
            p = p.parentNode;
        }
        if (p.parentNode == null) return "";
        p = p.parentNode;
        while (p.previousSibling && p.previousSibling.tagName != "TH") {
            p = p.previousSibling;
        }
        if (p.previousSibling == null) return "";
    }
    return p.previousSibling.innerHTML.replace(/<\/?[^>]+>/ig, "").replace("*", "").trim();
}

//获取单选框，或者复选框对应的文字描述，即其对应label标签中的文本
function getLabelText(obj) {
    var p = obj;
    while (p.parentNode && p.parentNode.tagName != "LABEL") {
        p = p.parentNode;
    }
    if (p.parentNode != null) {
        p = p.parentNode;
    }
    else {
        p = obj;
        while (p.nextSibling && p.nextSibling.tagName != "LABEL") {
            p = p.nextSibling;
        }
        if ((p = p.nextSibling) == null) return "";
    }
    return p.innerHTML.replace(/<\/?[^>]+>/ig, "").trim();
}

//获取单选框或者复选框列表中被选择的项目值
function getRadioOrCheckboxVal(sName) {
    var oArr = document.getElementsByName(sName); //获取同名单选框，或者复选框的list
    var sVal = '';
    for (var j = 0; j < oArr.length; j++) {
        if (oArr[j].checked) {
            if (sVal != '') sVal += "，";
            sVal += getLabelText(oArr[j]);
        }
    }
    return sVal;
}

//比较前后值的修改情况
//sOldVal：     原始值
//sNewVal：     修改后的值
//obj：         表单元素对象
//retObj        不为空时，返回被修改元素的name属性值，多个用英文逗号分隔
function compareValue(sOldVal, sNewVal, obj, retObj) {
    var sLog = "";
    if (sOldVal == sNewVal) return sLog;
    var sItemName = getItemName(obj);
    //if (sItemName == "") return sLog;
    if (sOldVal == "") {
        sLog = String.format("【{0}】从“空值”改成“{1}”", sItemName, sNewVal);
    }
    else if (sNewVal == "") {
        sLog = String.format("【{0}】从“{1}”改成“空值”", sItemName, sOldVal);
    }
    else {
        sLog = String.format("【{0}】从“{1}”改成“{2}”", sItemName, sOldVal, sNewVal);
    }
    if (null != retObj && typeof retObj == "object") {
        retObj.eleNames += obj.getAttribute("name") + ",";
    }
    return sLog + "；";
}

//显示模态窗口
function showDialogWindow(sURL, vArguments, vFeatures) {
    if (!sURL) return '';
    if (!vArguments) vArguments = null;
    if (!vFeatures) vFeatures = {};
    var sFeatures = String.format("dialogWidth={0}px;dialogHeight={1}px;center={2};help={3};resizable={4};status={5};scroll={6}",
        vFeatures.height ? vFeatures.height : 400, vFeatures.width ? vFeatures.width : 350,
        vFeatures.center ? vFeatures.center : 1, vFeatures.help ? vFeatures.help : 0,
        vFeatures.resizable ? vFeatures.resizable : 0, vFeatures.status ? vFeatures.status : 0, vFeatures.scroll ? vFeatures.scroll : 0);
    return window.showModalDialog(sURL, vArguments, sFeatures);
}

//验证表单输入
//containers：   需要执行验证的容器，json格式为：[{tab:"tab页对应的id或对象",container："容器对应的id或对象"}]
//rules：        默认执行非空验证，如有其它验证要求，可通过此参数指定，json格式为：{'待验证的元素ID':[条件1,'条件1验证不通过的提示信息',条件2,'条件2验证不通过的提示信息']}
//retObj：       函数返回值，预留接口
//返回值         true=验证通过，false=验证不通过
function validateForm(containers, rules, retObj) {

    var bTemp = bResult = true;
    if (null == containers || typeof containers == "undefined") return bResult;
    if (null == rules || typeof rules == "undefined") rules = {};
    var oInput = null, oTab;
    for (var i in containers) {
        var ctr = containers[i].container;
        if (!ctr) continue;
        ctr = "string" == typeof ctr ? document.getElementById(ctr) : ctr;
        if (!ctr) continue;
        var arrEles = new Array();
        var eles = ctr.getElementsByTagName("INPUT");  //文本框类型
        for (var j = 0, len = eles.length; j < len; j++) {
            var _a = eles[j].getAttribute("type");
            if (_a == null || _a.toLowerCase() == 'text' || _a.toLowerCase() == 'password')
                arrEles.push(eles[j]);
        }
        eles = ctr.getElementsByTagName("TEXTAREA");  //多行文本框类型
        for (var j = 0, len = eles.length; j < len; j++) {
            arrEles.push(eles[j]);
        }
        eles = ctr.getElementsByTagName("SELECT");  //下拉列表框
        for (var j = 0, len = eles.length; j < len; j++) {
            arrEles.push(eles[j]);
        }
        var item = (eles = null);
        while (item = arrEles.shift()) {
            if (!item.isValidate) { //第一次验证
                var rule = null;
                if (item.getAttribute("id") && typeof (rules[item.getAttribute("id")]) != "undefined")
                    rule = rules[item.getAttribute("id")];
                bTemp = validateItem(item, rule);
            }
            else { //已经验证过的表单域
                bTemp = item.isValidate == "1" ? true : false;
            }
            if (null == oInput && !bTemp) {  //保存第一个验证不通过的表单域，以及其所属的tab
                bResult = false;  //函数返回值
                oInput = item;
                oTab = containers[i].tab;
            }
        }
    } //for end
    if (null != oInput) {  //有验证不通过的域
        //自动定位到相应的tab
        EventUtil.execEvent(oTab, "click");
        setTimeout(function () {
            try {
                if (oInput.getAttribute("control") && "Date" == oInput.getAttribute("control")) //日期控件
                    EventUtil.execEvent(oInput, "click");
                else if (oInput.className.indexOf("TextBoxchoose") > -1) {  //有辅助选择窗口的text
                    //EventUtil.execEvent(oInput, "click");
                }
                else
                    oInput.focus();
            } catch (ex) {
            }
        }, 100);
    }
    return bResult;
}

//验证表单域输入是否合法
//item          验证的表单域对象
//rule          验证规则，格式是：[条件1,'条件1验证不通过的提示信息',条件2,'条件2验证不通过的提示信息']，条件必须是正则表达式
//返回值        true=验证通过，false=验证不通过
var isWinCloseed = false;
function validateItem(item, rule) {
    if (isWinCloseed) return false;
    var bRetVal = true;
    if ((item = "string" == typeof item ? document.getElementById(item) : item) == null) return bRetVal;
    if (item.disabled) return bRetVal;
    var bFlag = false; //判断元素是否需要验证，如果不需要，不注册事件
    var s = item.tagName.toLowerCase();
    rule = typeof rule != "object" ? null : rule;
    if ("true" == item.getAttribute("is-required")) { //必须输入值
        bFlag = true;
        var sMsg = item.getAttribute("required-msg") == null ? "请输入内容" : item.getAttribute("required-msg");
        switch (s) {
            //单行文本框、多行文本框、下拉列表框                                                                                                                           
            case "input":
            case "textarea":
            case "select": {
                bRetVal = ("" != item.value.trim());
                //日期控件、有辅助选择窗口的text
                if ((item.getAttribute("control") && "Date" == item.getAttribute("control")) || item.className.indexOf("TextBoxchoose") > -1) {
                    if (!item.intervalName) {
                        item.intervalName = window.setInterval(function () {
                            validateItem(item, rule);
                        }, 1000);
                        if (!window.intervalNames) window.intervalNames = [];
                        window.intervalNames[window.intervalNames.length] = item.intervalName;
                    }
                }
            }
                break;
            //单选框、复选框                                                                                                
            case "radio":
            case "checkbox":
                break;
        } //switch end
    }
    if (null != rule && bRetVal) {  //根据传入的规则判断有效性
        var bTemp = true;
        if ("" != item.value.trim()) {
            var j = 0;
            for (; j < rule.length; j += 2) {
                if (!rule[j] || "object" != typeof rule[j]) continue;
                var reg = new RegExp(rule[j]);
                bTemp = reg.test(item.value.trim());
                if (!bTemp) break;
            }
        }
        setValidateResult(item, !(rule[j + 1]) ? "输入错误" : rule[j + 1], rule, (bRetVal = bTemp), s);
    }
    else if (bFlag) {
        setValidateResult(item, bRetVal ? "" : sMsg, rule, bRetVal, s);
    }
    return bRetVal;
}

//设置对象的验证结果
//这里就设置对象的验证结果是通过还是不通过，通过标识isValidate=1
function setValidateResult(item, sMsg, rule, b, tag) {
    item.rule = rule;
    if (!item.isValidate) { //第一次验证
        var bTem = (item.getAttribute("control") && "Date" == item.getAttribute("control")) || item.className.indexOf("TextBoxchoose") > -1;
        if (!bTem) {
            EventUtil.addEventHandler(item, "select" == tag ? "change" : "keyup", bindValidateEvent);
            if ("input" == tag) {   //文本框，注册change事件
                EventUtil.addEventHandler(item, "change", bindValidateEvent);
            }
        }
    }
    var _c = ("select" == tag ? " SelectErr" : " TextBoxErr");
    if (!b) {  //验证未通过
        item.title = sMsg;
        var _n = setSelectFormat(item, tag, b);
        _n.className += (_n.className.indexOf(_c) > -1 ? "" : _c);
        item.isValidate = "0";
    }
    else {  //验证通过
        item.title = "";
        var _n = setSelectFormat(item, tag, b);
        _n.className = _n.className.replace(new RegExp("( ?|^)" + _c + "\\b"), "");
        item.isValidate = "1";
    }
}

//设置表单元素的状态：必填不通过状态和通过的状态
function setElementFormat(item, bState, sMsg, bFocus) {
    item = (item = "string" == typeof item ? document.getElementById(item) : item);
    if (null == item)return;
    var _c = ("select" == item.tagName.toLowerCase() ? " SelectErr" : " TextBoxErr");
    if (!bState) {  //验证未通过
        item.title = sMsg;
        var _n = setSelectFormat(item, item.tagName.toLowerCase(), bState);
        _n.className += (_n.className.indexOf(_c) > -1 ? "" : _c);
        item.isValidate = "0";
        if (bFocus) {
            try {
                item.focus();
            } catch (ex) {
            }
        }
    }
    else {  //验证通过
        item.title = "";
        var _n = setSelectFormat(item, item.tagName.toLowerCase(), bState);
        _n.className = _n.className.replace(new RegExp("( ?|^)" + _c + "\\b"), "");
        item.isValidate = "1";
    }
}

function setSelectFormat(item, tag, b) {
    var _n = item;
    if ("select" == tag) {
        _n = getParentDiv(_n);
        if (_n) {
            var _b = item.style.width == '' || item.style.width == "100%";
            if (item.className == "select" || item.className == "select SelectErr")
                b = _b;
            item.style.width = b ? "100%" : item.offsetWidth > 0 ? ((item.offsetWidth - (_b ? 22 : 0)) + "px") : "100%";
        }
        else {
            _n = item;
        }
    }
    return _n;
}

//给表单域绑定的验证方法
//可以用于动态的给表单加验证 ，当满足某某某条件时，给表单加验证
function bindValidateEvent(e) {
    var ev = window.event || e;
    var targ = ev.srcElement || ev.target;
    if (typeof ev.cancelBubble != "undefined") {
        ev.cancelBubble = true;
    } else if (ev.stopPropagation) {
        ev.preventDefault();
        ev.stopPropagation();
    }
    validateItem(targ, targ.rule);
}

//获取表单中select元素所在的 div
function getParentDiv(obj) {
    return obj.parentNode && obj.parentNode.tagName == "DIV" ? obj.parentNode : obj;
}

function getElementWidth(item) {
    var _w = item.offsetWidth;
    if (0 >= _w) {
        var _i = item.cloneNode();
        _i.cssText = "display:block;position:absolute;top:-999;left:-999;";
        item.parentNode.appendChild(_i);
        _w = _i.offsetWidth;
        item.parentNode.removeChild(_i);
    }
    return _w;
}

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.format = function () {
    if (arguments.length == 0) return "";
    if (arguments.length == 1) return arguments[0];
    var reg = /{(\d+)?}/g;
    var args = arguments;
    return arguments[0].replace(reg, function ($0, $1) {
        return args[parseInt($1) + 1];
    });
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


/**
 * 根据不同地市截取标准地址
 * @param pjss
 * @param dzmc
 */
function subjzdd(pjss, dzmc) {
    switch (pjss) {
        case  'aq':
            if (dzmc.substr(0, 6) == '安徽省安庆市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'wh':
            if (dzmc.substr(0, 6) == '安徽省芜湖市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'hn':
            if (dzmc.substr(0, 6) == '安徽省淮南市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'hb':
            if (dzmc.substr(0, 6) == '安徽省淮北市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'chiz':
            if (dzmc.substr(0, 6) == '安徽省池州市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'xc':
            if (dzmc.substr(0, 6) == '安徽省宣城市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'hs':
            if (dzmc.substr(0, 6) == '安徽省黄山市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'mas':
            if (dzmc.substr(0, 7) == '安徽省马鞍山市')
                return dzmc.substr(7);
            else
                return dzmc;
            break;
        case  'bb':
            if (dzmc.substr(0, 6) == '安徽省蚌埠市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'tl':
            if (dzmc.substr(0, 3) == '铜陵市')
                return dzmc.substr(3);
            else
                return dzmc;
            break;
        case  'la':
            if (dzmc.substr(0, 6) == '安徽省六安市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'fy':
            if (dzmc.substr(0, 6) == '安徽省阜阳市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'chuz':
            if (dzmc.substr(0, 6) == '安徽省滁州市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        case  'bz':
            if (dzmc.substr(0, 6) == '安徽省亳州市')
                return dzmc.substr(6);
            else
                return dzmc;
            break;
        default:
            return dzmc;
    }
}

EventUtil.addEventHandler(window, "unload", function () {
    isWinCloseed = true;
    if (!window.intervalNames) return;
    var _i = 0;
    while (_i = window.intervalNames.shift())
        window.clearInterval(_i);
    window.intervalNames = null;
    window.regEleEvent = null;
});
/**
 * 百度地图接口
 *@param opt
 mapData://单点{},多点[{}]//数据
 enableDrawingTool: true,//是否可编辑（默认不可编辑）
 callBack: //返回函数
 */
window.openBaiduMap = function (opt) {
    var area = ['900px', '600px'];
    top.layer.open({
        //zIndex:1,
        type: 2,
        anim: 3,
        title: '地图',
        area: area,
        maxmin: true,
        content: '/jfyth/main/baiduMap',
        success: function (layero, index) {
            var iframeWin = top.frames[layero.find('iframe')[0]['name']];
            if (opt) {
                iframeWin.data = opt;
            } else iframeWin.data = {};
        }
    })
};
/**
 * 内网地图接口
 *@param opt
 mapData://单点{},多点[{}]//数据
 enableDrawingTool: true,//是否可编辑（默认不可编辑）
 callBack: //返回函数
 */
window.openNwMap = function (opt) {
    var area = ['900px', '600px'];
    top.layer.open({
        //zIndex:1,
        type: 2,
        anim: 3,
        title: '地图',
        area: area,
        maxmin: true,
        content: '/main/nwMap',
        success: function (layero, index) {
            var iframeWin = top.frames[layero.find('iframe')[0]['name']];
            if (opt) {
                iframeWin.data = opt;
            } else iframeWin.data = {};
        }
    })
};
