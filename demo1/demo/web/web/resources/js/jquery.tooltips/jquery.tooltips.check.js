/**
 *
 * update-time 2016-04-29
 * update: 修复身份证号验证的bug
 */

var result = {response: true, obj: ""};

$.tooltips = {
    isNotNull: function (el) {
        var value = $(el).val();
        if (value && $.trim(value) != "") {
            result.response = true;
            result.obj = null;
        } else {
            result.response = false;
            if (el.tagName.toLowerCase() == "select") {
                result.obj = "请选择"
            } else {
                result.obj = "请输入";
            }
        }
        return result;
    }, checkIdCard: function checkIdCard(el) {
        var idCard = $.trim($(el).val());               // 得到身份证数组
        if (validate18IdCard(idCard)) {   //进行18位身份证的基本验证和第18位的验证
            result.response = true;
            result.obj = null;
        } else {
            result.obj = "请输入正确的身份证号";
            result.response = false;
        }
        return result;
    }, /**** 是否为数字串，length等于0不限制长度 ****/
    isNumber: function (el, length) {
        var value = $(el).val();
        var regx;
        if ((!length) || length == 0)
            regx = new RegExp("^\\d+$");
        else
            regx = new RegExp("^\\d{" + length + "}$");
        if (value.search(regx) == -1) {
            result.obj = "请输入有效的数字";
            result.response = false;
        }
        else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isNumberNotZero: function (el, length) {
        var value = $(el).val();
        var regx;
        if ((!length) || length == 0)
            regx = new RegExp("^[1-9]\\d*$");
        else
            regx = new RegExp("^\\d{" + length + "}$");
        if (value.search(regx) == -1) {
            result.obj = "请输入有效的数字";
            result.response = false;
        }
        else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isNumberFloat: function (el, length) {
        var value = $(el).val();
        var regx;
        if ((!length) || length == 0)
            regx = new RegExp("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        else
            regx = new RegExp("^\\d{" + length + "}$");
        if (value.search(regx) == -1) {
            result.obj = "请输入有效的数字";
            result.response = false;
        }
        else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isTelphone: function (el) {
        var value = $.trim($(el).val());
        var mobile = new RegExp("^(13|14|15|17|18)[0-9]{9}$", "i");
        if (value == "" || !mobile.test(value)) {
            result.obj = "请输入有效的手机号码";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isTel: function (el) {
        var value = $.trim($(el).val());
        var mobile = new RegExp("^\\d{7,8}$", "i");
        if (value == "" || !mobile.test(value)) {
            result.obj = "请输入有效的固定电话";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    /**
     * 验证类似0553-6678866;05536678866;6678866格式的固定电话
     * @param el
     * @return {{response: boolean, obj: string}}
     */
    isBgdh: function (el) {
        var value = $.trim($(el).val());
        var mobile = new RegExp("^((0\\d{2,3}-\\d{7,8})|(0\\d{2,3}\\d{7,8})|(0\\d{2,3} \\d{7,8})|(\\d{7,8}))$", "i");
        if (value == "" || !mobile.test(value)) {
            result.obj = "请输入有效的办公电话";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isFloat: function (el) {
        var value = $.trim($(el).val());
        var float = new RegExp("^[0-9]+([.]{1}[0-9]+){0,1}$", "i");
        if (value == "" || !float.test(value)) {
            result.obj = "请输入有效数字";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    isCph: function (el) {
        var value = $.trim($(el).val());
        var reg = new RegExp("^[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,秦,甘,陇,青,台,内蒙古,桂,宁,新,藏,澳,军,海,航,警][A-Z][0-9,A-Z]{5}$", "i");
        if (value == "" || !reg.test(value)) {
            result.obj = "请输入正确的车牌号";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    },
    /**
     * 联系电话(手机/电话皆可)验证 ，这儿先做了value不为空的判断
     */
    isLxdh: function (el) {
        var value = $.trim($(el).val());
        var result1 = value.match(/^((0\d{2,3}-\d{7,8})|(0\d{2,3}\d{7,8})|(0\d{2,3} \d{7,8})|(\d{7,8}))$/);
        var result2 = value.match(/^(13|14|15|17|18)[0-9]{9}$/);
        if (result1 == null && result2 == null) {
            result.obj = "请输入有效的联系电话";
            result.response = false;
        } else {
            result.response = true;
            result.obj = null;
        }
        return result;
    }
};


var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];    // 加权因子
var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];            // 身份证验证位值.10代表X

/**
 * 验证18位身份证号
 * @param idcard
 * @returns {*}
 */
function validate18IdCard(idcard) {
    var a_idCard = idcard.split("");
    if (idcard.length == 18) {
        return isTrueValidateCodeBy18IdCard(a_idCard) && isValidityBrithBy18IdCard(idcard);
    } else {
        return false;
    }
}

/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * @param a_idCard 身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作
    }
    for (var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和
    }
    valCodePosition = sum % 11;                // 得到验证码所位置
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    } else {
        return false;
    }
}

/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * @param idCard18 位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18) {
    var year = idCard18.substring(6, 10);
    var month = idCard18.substring(10, 12);
    var day = idCard18.substring(12, 14);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题
    if (temp_date.getFullYear() != parseFloat(year)
        || temp_date.getMonth() != parseFloat(month) - 1
        || temp_date.getDate() != parseFloat(day)) {
        return false;
    } else {
        return true;
    }
}
