//js中的字符串正常显示在HTML页面中
function escape2Html(str) {
    if (str == null || str == "") {
        return "";
    }
    //将字符串转换成数组
    var strArr = str.split("");
    //HTML页面特殊字符显示，空格本质不是，但多个空格时浏览器默认只显示一个，所以替换
    var htmlChar = "&<>";
    for (var i = 0; i < str.length; i++) {
        //查找是否含有特殊的HTML字符
        if (htmlChar.indexOf(str.charAt(i)) != -1) {
            //如果存在，则将它们转换成对应的HTML实体
            switch (str.charAt(i)) {
                case "<":
                    strArr.splice(i, 1, "&#60;");
                    break;
                case ">":
                    strArr.splice(i, 1, "&#62;");
                    break;
                case "&":
                    strArr.splice(i, 1, "&#38;");
            }
        }
    }
    return strArr.join("");
}