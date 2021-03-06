function showBg(b, d, g) {
    var a = $("body").height();
    var f = $("body").width() + 16;
    var c = getObjWh(b);
    $("#fullbg").css({width: f, height: a, display: "block"});
    var h = c.split("|")[0] + "px";
    var e = c.split("|")[1] + "px";
    $("#" + b).css({top: h, left: e, display: "block"});
    $("#" + d).html("<div id='innerContent' style='text-align:center;'>" + g + "</div>");
    $(window).scroll(function () {
        resetBg();
    });
    $(window).resize(function () {
        resetBg();
    });
}
function getObjWh(f) {
    var i = document.documentElement.scrollTop;
    var c = document.documentElement.scrollLeft;
    var a = document.documentElement.clientHeight;
    var e = document.documentElement.clientWidth;
    var d = $("#" + f).height();
    var g = $("#" + f).width();
    var h = Number(i) + (Number(a) - Number(d)) / 2;
    var b = Number(c) + (Number(e) - Number(g)) / 2;
    return h + "|" + b;
}
function resetBg() {
    var a = $("#fullbg").css("display");
    if (a == "block") {
        var e = $("body").height();
        var c = $("body").width();
        $("#fullbg").css({width: c, height: e});
        var b = getObjWh("dialog");
        var f = b.split("|")[0] + "px";
        var d = b.split("|")[1] + "px";
        $("#dialog").css({top: f, left: d});
    }
}
function closeBg() {
    $("#fullbg").remove();
    $("#dialog").remove();
}
function closeBgLater(a) {
    var b = setTimeout("closeBg()", a);
}
function include_css(a) {
    var b = document.getElementsByTagName("head")[0];
    var c = document.createElement("link");
    c.setAttribute("rel", "stylesheet");
    c.setAttribute("type", "text/css");
    c.setAttribute("href", a);
    b.appendChild(c);
    return false;
}
function MaskLayer(h) {
    var a = document.body;
    var g = '<div id="fullbg"><iframe style="position:absolute;width:100%;height:100%;top:0;left:0;_filter:alpha(opacity=0);opacity=0;"></iframe></div>';
    var e = $(g).appendTo(a);
    var b = '<div id="dialog"><div>';
    var d = $(b).appendTo(a);
    var c = '<div id="dialog_content"></div>';
    var f = $(c).appendTo(d);
    if (h == null || h.length == 0) {
        showBg("dialog", "dialog_content", "正在加载,请稍候...");
    } else {
        showBg("dialog", "dialog_content", h);
    }
}
var rcom = {
    UUID: function (c, b) {
        var d = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
        var a = parseInt(c / b);
        if (c < b) {
            return d[c];
        } else {
            return (rcom.UUID(a, b) + d[c % b]);
        }
    }, getUuid: function () {
        var a = new Date().getTime(), c = parseInt(Math.random() * 10000), d = a * c + "" + c, b = 62;
        return rcom.UUID(parseInt(d), b);
    }
};