function isMobile(a, j) {
    var e = /^13\d{5,9}$/;
    var d = /^15\d{5,9}$/;
    var c = /^18\d{5,9}$/;
    var b = /^0\d{10,11}$/;
    var h = true;
    if (a != "") {
        var f = a.split(",");
        for (var g = 0; g < f.length; g++) {
            if (!(e.test(f[g])) && !(d.test(f[g])) && !(c.test(f[g])) && !(b.test(f[g]))) {
                h = false;
                break;
            }
        }
        if (!h) {
            if (j == undefined || j == null) {
                j = "请输入正确的手机号码";
            }
            return false;
        }
    }
    return true;
}

function isTel(a, f) {
    var c = true;
    var e = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
    if (a != "") {
        var b = a.split(",");
        for (var d = 0; d < b.length; d++) {
            if (!e.test(b[d])) {
                c = false;
                break;
            }
        }
        if (!c) {
            if (f == undefined || f == null) {
                f = "电话号码格式为:国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)";
            }
            return false;
        }
    }
    return true;
}

function checkCard(b) {
    if (b != null && b != "") {
        if (b.length != 18 && b.length != 15) {
            top.layer.alert("请输入中国公民15位或18位身份证号码, 您当前输入了" + b.length + "位号码");

            return false;
        }
        for (i = 0; i < b.length - 1; i++) {
            if (isNaN(parseInt(b.charAt(i)))) {
                top.layer.alert("您输入的身份证号码前" + (b.length - 1) + "位包含有字母, 不合要求");
                return false;
            }
        }
        var a = b.charAt(b.length - 1);
        if (isNaN(parseInt(b.charAt(i))) && a.toLowerCase() != "x") {
            layer.alert("您输入的身份证号码最后一位不是数字也不是x, 不合要求");
            return false;
        }
    }
    return true;
}

function checkLxdh(e, g) {
    e = $.trim(e);
    var d = /^0?1[358]\d{9}$/;
    var f = /^(([0\+]\d{2,3}-)?(0\d{2,3})-?)?(\d{7,8})(-(\d{3,}))?$/;
    var h = true;
    if (e != "") {
        if (g != null && g == true) {
            for (var a = 0; a < e.length; a++) {
                if (e.charCodeAt(a) < 44 || e.charCodeAt(a) > 57) {
                    alert("联系电话不允许输入非法字符");
                    return false;
                }
            }
        } else {
            var c = e.split(",");
            for (var b = 0; b < c.length; b++) {
                if (!(d.test(c[b])) && !(f.test(c[b]))) {
                    h = false;
                    break;
                }
            }
            if (!h) {
                if (g == undefined || g == null || g == "") {
                    g = "请输入正确的联系电话";
                }
                alert(g);
                return false;
            }
        }
    }
    return true;
}

function checkWord(d, b) {
    var c = d.value;
    var a = 0;
    for (i = 0; i < c.length && a <= b; i++) {
        if (c.charCodeAt(i) > 0 && c.charCodeAt(i) < 128) {
            a++;
        } else {
            a += 2;
        }
        if (a > b) {
            d.value = c.substring(0, i);
        }
    }
}

function checkifenglish(a, c) {
    a = $.trim(a);
    var b = /^[a-zA-Z0-9]{1}([a-zA-Z0-9]|[-._\s])*$/;
    if (a != "") {
        if (!b.test(a)) {
            if (c == undefined || c == null) {
                c = "请输入合法的字符";
            }
            layer.alert(c);
            return false;
        }
    }
    return true;
}

function checkNumber(a, c) {
    var b = /^[0-9]+(\.)?([0-9])*$/;
    if (a != "") {
        if (!b.test(a)) {
            if (c == undefined || c == null) {
                c = "请输入合法的数字";
            }
            layer.alert(c);
            return false;
        }
    }
    return true;
}

function showSome(b, a) {
    document.write("<span title='" + b + "'><nobr>" + b + "</nobr></span>");
}

function showSomeTS(a, b) {
    var d = $(a).val().replace(/[\r\n]/g, "   ");
    if (d.length > b) {
        var c = d.substring(0, b) + "<p style='display:inline'>...</p>";
        document.write("<span title='" + d + "'>" + c + "</span>");
    } else {
        document.write("<span title='" + d + "'>" + d + "</span>");
    }
}

function limitSelect(a) {
    $("select").each(function () {
        if ("ttt" == this.className) {
            $(this).css("width", "90%");
        } else {
            $(this).css("width", a);
        }
        $(this).children("option").each(function () {
            $(this).attr("title", $(this).text());
        });
    });
}

function validateSg(b, c) {
    if (b != null && b != "") {
        for (var a = 0; a < b.length; a++) {
            if (b.charCodeAt(a) < 46 || b.charCodeAt(a) > 57) {
                layer.alert(c);
                return;
            }
        }
    }
}

function validateDhhm(c) {
    if (c != null && c != "") {
        for (var a = 0; a < c.length; a++) {
            var b = c.charCodeAt(a);
            if (b != 44 && b != 45 && !(b >= 48 && b <= 57)) {
                layer.alert("请输入正确的联系电话");
                return;
            }
        }
    }
}

function get_sex_from_idcard(a) {
    var b;
    if (a.slice(14, 17) % 2 != 0) {
        b = "男";
    } else {
        b = "女";
    }
    return b;
}

function get_Birthday_from_idcard(b) {
    var a = "";
    var c = "";
    var e = 0;
    var d = "";
    if ((b.length != 15) && (b.length != 18)) {
        d = "输入的身份证号位数错误";
        return d;
    }
    if (b.length == 15) {
        a = b.substring(6, 12);
        a = "19" + a;
        a = a.substring(0, 4) + "-" + a.substring(4, 6) + "-" + a.substring(6);
    } else {
        a = b.substring(6, 14);
        a = a.substring(0, 4) + "-" + a.substring(4, 6) + "-" + a.substring(6);
    }
    return a;
}

function clearFile(a) {
    a.after(a.clone().val(""));
    a.remove();
}

function checkDate(d, c) {
    d = d.split(/-|\/|\s/);
    c = c.split(/-|\/|\s/);
    var b = new Date(d[0], d[1], d[2]);
    var a = new Date(c[0], c[1], c[2]);
    if (b < a) {
        return true;
    }
    return false;
}

Date.prototype.format = function (b) {
    var c = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    if (/(y+)/.test(b)) {
        b = b.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var a in c) {
        if (new RegExp("(" + a + ")").test(b)) {
            b = b.replace(RegExp.$1, RegExp.$1.length == 1 ? c[a] : ("00" + c[a]).substr(("" + c[a]).length));
        }
    }
    return b;
};

function isIdCardNo(g, m) {
    g = $.trim(g);
    if (g == null || g.length == 0) {
        return true;
    }
    g = g.toUpperCase();
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(g))) {
        hideAllTooltips();
        showTooltips(m + "_input", "输入的身份证号长度不对，或者号码不符合规定15位号码应全为数字，18位号码末位可以为数字或X");
        return false;
    }
    var h, o;
    h = g.length;
    if (h == 15) {
        o = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var n = g.match(o);
        var c = new Date("19" + n[2] + "/" + n[3] + "/" + n[4]);
        var b;
        b = (c.getYear() == Number(n[2])) && ((c.getMonth() + 1) == Number(n[3])) && (c.getDate() == Number(n[4]));
        if (!b) {
            hideAllTooltips();
            showTooltips(m + "_input", "输入的身份证号里出生日期不正确");
            return false;
        } else {
            var k = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var l = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
            var j = 0, f;
            g = g.substr(0, 6) + "19" + g.substr(6, g.length - 6);
            for (f = 0; f < 17; f++) {
                j += g.substr(f, 1) * k[f];
            }
            g += l[j % 11];
        }
    } else {
        if (h == 18) {
            o = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
            var n = g.match(o);
            var c = new Date(n[2] + "/" + n[3] + "/" + n[4]);
            var b;
            b = (c.getFullYear() == Number(n[2])) && ((c.getMonth() + 1) == Number(n[3])) && (c.getDate() == Number(n[4]));
            if (!b) {
                hideAllTooltips();
                showTooltips(m + "_input", "输入的身份证号里出生日期不正确");
                return false;
            } else {
                var a;
                var k = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                var l = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
                var j = 0, f;
                for (f = 0; f < 17; f++) {
                    j += g.substr(f, 1) * k[f];
                }
                a = l[j % 11];
                if (a != g.substr(17, 1)) {
                    hideAllTooltips();
                    showTooltips(m + "_input", "18位身份证的校验码不正确！最后一位应该为：" + a);
                    $("#" + m + "").val($("#" + m + "").val().substr(0, 17) + a);
                    return false;
                }
            }
        }
    }
    var e = g.substr(6, 4) + "-" + Number(g.substr(10, 2)) + "-" + Number(g.substr(12, 2));
    var d = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    };
    if (d[parseInt(g.substr(0, 2))] == null) {
        showTooltips(m + "_input", "错误的地区码：" + g.substr(0, 2));
        return false;
    }
    if (Number(g.substr(6, 2)) < 19) {
        showTooltips(m + "_input", "该人不会是19世纪以前的出生吧？(" + e + ")");
        return false;
    }
    document.getElementById(m).value = g;
    return true;
}

function isIdCardNo2(g, m) {
    g = $.trim(g);
    if (g == null || g.length == 0) {
        return true;
    }
    g = g.toUpperCase();
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(g))) {
        //alert("输入的身份证号长度不对，或者号码不符合规定\n15位号码应全为数字，18位号码末位可以为数字或X");
        return false;
    }
    var h, o;
    h = g.length;
    if (h == 15) {
        o = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
        var n = g.match(o);
        var c = new Date("19" + n[2] + "/" + n[3] + "/" + n[4]);
        var b;
        b = (c.getYear() == Number(n[2])) && ((c.getMonth() + 1) == Number(n[3])) && (c.getDate() == Number(n[4]));
        if (!b) {
            //alert("输入的身份证号里出生日期不正确");
            return false;
        } else {
            var k = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
            var l = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
            var j = 0, f;
            g = g.substr(0, 6) + "19" + g.substr(6, g.length - 6);
            for (f = 0; f < 17; f++) {
                j += g.substr(f, 1) * k[f];
            }
            g += l[j % 11];
        }
    }
    else {
        if (h == 18) {
            o = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
            var n = g.match(o);
            var c = new Date(n[2] + "/" + n[3] + "/" + n[4]);
            var b;
            b = (c.getFullYear() == Number(n[2])) && ((c.getMonth() + 1) == Number(n[3])) && (c.getDate() == Number(n[4]));
            if (!b) {
                //alert("输入的身份证号里出生日期不正确");
                return false;
            }
            else {
                var a;
                var k = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
                var l = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
                var j = 0, f;
                for (f = 0; f < 17; f++) {
                    j += g.substr(f, 1) * k[f];
                }
                a = l[j % 11];
                if (a != g.substr(17, 1)) {
                    //alert("18位身份证的校验码不正确！最后一位应该为："+a);
                    //$("#"+m+"").val($("#"+m+"").val().substr(0,17)+a);
                    return false;
                }
            }
        }
    }
    var e = g.substr(6, 4) + "-" + Number(g.substr(10, 2)) + "-" + Number(g.substr(12, 2));
    var d = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    };
    if (d[parseInt(g.substr(0, 2))] == null) {
        //alert("错误的地区码："+g.substr(0,2));
        return false;
    }
    if (Number(g.substr(6, 2)) < 19) {
        //alert("该人不会是19世纪以前的出生吧？("+e+")");
        return false;
    }
    //document.getElementById(m).value=g;
    return true;
}

//文本框长度验证
function textareachk(obj, name) {
    var maxl = 0;
    if ($(obj).attr("readOnly")) {
        return;
    }
    var maxLength = parseInt($(obj).attr("maxlength"));
    if (!maxLength) {
        maxLength = parseInt($(obj).prop("maxlength"));
    }
    if (obj != null && maxLength > 0) {
        if (maxl === 0) maxl = maxLength; //总长
        var s = obj.value.length;
        var v = obj.value;
        var len = s;
        if (len > maxl) {
            obj.value = obj.value.substr(0, maxl);
            if ($("#" + name)[0] != undefined) {
                $("#" + name)[0].innerHTML = "<span style='color:red'>已输入:" + maxl + "/" + maxl + " 字符</spqn>";
            }
        }
        else {
            if ($("#" + name)[0] != undefined) {
                $("#" + name)[0].innerHTML = "<span style='color:#66adff'>已输入:" + len + "/" + maxl + " 字符</spqn>";
            }
        }
    }
}


/**
 * 必填验证
 * 验证的表单中的必填输入框必须 有属性 notEmpty="true"   displayName=""
 * @param $form
 */
function notEmpty($form) {
    //input select textarea 不为空验证
    var sum = $("input[type='text'][notEmpty='true'],select[notEmpty='true'],textarea[notEmpty='true']").length;
    for (var i = 0; i < sum; i++) {
        var $btText = $($("input[type='text'][notEmpty='true']")[i]);
        if (!$btText.val()) {
            var mc = $btText.attr("displayName");
            alert(mc + "不能为空");
            $btText.focus();
            return false;
        }
    }
    //checkbox radio 不为空验证
    var elem = $form.find("input[type='checkbox'][notEmpty='true'],input[type='radio'][notEmpty='true']");
    var size = elem.size();
    for (var i = 0; i < size; i++) {
        var name = elem[i].name;
        var child = $("[name='" + name + "']:checked");
        if (child.size() == 0) {
            var mc = $(elem[i]).attr("displayName");
            alert("请选择" + mc);
            $(elem).focus();
            return false;
        }
    }
    return true;
}

/**
 * textarea 长度限制
 * @param obj
 * @return
 */
function textareaLength(obj) {
    var $elem = $(obj);
    var maxlength = $elem.attr("maxlength");
    var value = $elem.text();
    if (value != "" && value.length > maxlength) {
        alert('输入的长度已超过最大输入长度' + maxlength);
        $elem.text(value.substring(0, maxlength));
        $elem.focus();
        return false;
    }
    return true;
}

/**
 * 验证数值 位数
 * @param obj   待验证的元素
 * @param intSize  整数位
 * @param decimalSize  小数位
 */
function checkFloat(obj, intSize, decimalSize) {
    var $elem = $(obj), reg = /\./;
    var m = $elem.attr("name");
    var value = $.trim($elem.val());
    value = value.replace(/[^\d\.]/g, "");
    $elem.val(value);
    var result = value.split(reg);
    if (value != null && value != "") {
        if (result.length == 1) {
            if (result[0].length > intSize) {
                showTooltips(m + "_input", "整数位数最多为" + intSize);
                obj.value = "";
                obj.focus();
                return false;
            }
        } else if (result.length == 2) {
            if (result[0].length > intSize && result[1].length > decimalSize) {
                showTooltips(m + "_input", "整数位数最多为" + intSize + ",小数位数最多为" + decimalSize);
                obj.value = "";
                obj.focus();
                return false;
            }
            if (result[0].length > intSize && result[1].length <= decimalSize) {
                showTooltips(m + "_input", "整数位数最多为" + intSize);
                obj.value = "";
                obj.focus();
                return false;
            }
            if (result[0].length <= intSize && result[1].length > decimalSize) {
                showTooltips(m + "_input", "小数位数最多为" + decimalSize);
                obj.value = "";
                obj.focus();
                return false;
            }
        } else {
            showTooltips(m + "_input", "小数位数最多为" + decimalSize);
            obj.value = "";
            obj.focus();
            return false;
        }
    }
    if (value.indexOf(".") == value.length - 1) {
        obj.value = value.split(".")[0];
        return false;
    }
    return true;
}

/**
 * 验证整数
 * @param obj 待验证的元素
 */
function checkInt(obj) {
    obj.value = obj.value.replace(/[^\d]/g, "");
    if (obj.value && obj.value.length > 1 && obj.value.indexOf("0") == 0) {
        obj.value = obj.value.substring(1, obj.value.length);
        obj.focus();
    }
}

/**
 * 验证车牌号
 */
function isCph(obj, m) {
    var value = $.trim($(obj).val());

    if (value == null || value.length == 0) {
        return true;
    }
    var reg = new RegExp("^[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,秦,甘,陇,青,台,内蒙古,桂,宁,新,藏,澳,军,海,航,警][A-Z][0-9,A-Z]{5}$", "i");
    if (!reg.test(value)) {
        hideAllTooltips();
        showTooltips(m + "_input", "请输入正确的车牌号");
        return false;
    }
    return true;
}

/**
 *验证固定电话(办公电话)
 */
function isBgdh(obj, m) {
    var value = $.trim($(obj).val());

    if (value == null || value.length == 0) {
        return true;
    }
    var bgdh = new RegExp("^((0\\d{2,3}-\\d{7,8})|(0\\d{2,3}\\d{7,8})|(0\\d{2,3} \\d{7,8})|(\\d{7,8}))$", "i");
    if (!bgdh.test(value)) {
        hideAllTooltips();
        showTooltips(m + "_input", "请输入正确的办公电话");
        return false;
    }
    return true;
}

/**
 *验证手机电话(只限手机电话)
 */
function isTelphone(obj) {
    var m = $(obj).attr("name");
    var value = $.trim($(obj).val());
    if (value == null || value.length == 0) {
        return true;
    }
    var mobile = new RegExp("^(13|14|15|17|18)[0-9]{9}$", "i");
    if (!mobile.test(value)) {
        hideAllTooltips();
        showTooltips(m + "_input", "请输入正确的手机电话");
        return false;
    }
    return true;
}

/**
 *验证联系电话(包含固定电话和手机号)
 */
function isLxdh(obj, m) {
    var value = $.trim($(obj).val());
    if (value == null || value.length == 0) {
        return true;
    }
    var result1 = value.match(/^((0\d{2,3}-\d{7,8})|(0\d{2,3}\d{7,8})|(0\d{2,3} \d{7,8})|(\d{7,8}))$/);
    var result2 = value.match(/^(13|14|15|17|18)[0-9]{9}$/);
    if (result1 == null && result2 == null) {
        hideAllTooltips();
        showTooltips(m + "_input", "请输入正确的联系电话");
        return false;
    }
    return true;
}

/**
 * 不为空
 */
function isNotEmpty(obj) {
    var value = $.trim($(obj).val());
    if (!(value && $.trim(value) != "")) {
        hideAllTooltips();
        showTooltips(obj.name + "_input", $(obj).attr("displayName") + "不能为空");
        $(obj).focus();
        return false;
    }
    return true;
}



