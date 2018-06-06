/*Fts
 手风琴菜单+树形菜单
 一般情况下每个页面只有一个默认选中项，默认为第一个菜单项，该菜单项中a标签的Class应设为SelectedItem
 手风琴菜单的每两个Tab之间有一个空的DIV，是为了消除IE6中的某些BUG
 */
$(function () {
//  //树形菜单
    var $flag = $(".selectedItem");
    $(".nav li a").click(function () {
        $(this).siblings("ul").toggle().end().children(".toggleIcon").toggleClass("ZhanKai", "WeiZhanKai");
        if ($flag) {
            $flag.removeClass("selectedItem");
        }
        $(this).addClass("selectedItem");
        $flag = $(this);
        //解决IE6下菜单项文字过长出现横向滚动条时会产生的BUG
        var $u = $(this).closest("ul");
        $u.find("a").width($u.width());
        return false;
    });
    var len = $(".nav li").length;
    $(".nav li").each(function (i) {
        //计算并设置菜单项的左边距，产生层级效果
        var x = ($(this).parents("li").length + 1) * 20;
        $(this).css("text-indent", x + "px");
        $(this).find(".toggleIcon").addClass("WeiZhanKai").css("left", x - 10 + "px")
        //判断each循环执行到最后一次的时候，隐藏所有子菜单，这样做的原因是IE6中必须先设置好text-indent再隐藏才能产生好的效果，hide方法中的参数0也是为了兼容IE6
        if (i == (len - 1)) {
            $(".nav li").has(".toggleIcon").children("ul").hide(0);
        }
    });
    //手风琴菜单
    $(".leftBox").height($(".leftSide").height() - $(".leftTop").height() - 33.5 * $(".leftTop").length);
    $(window).resize(function () {
        $(".leftBox").height($(".leftSide").height() - $(".leftTop").height() - 33.5 * $(".leftTop").length);
    });
    $(".leftBox:gt(0)").hide(0);
    $(".leftTop").click(function () {
        //在手风琴菜单中，点击当前展开菜单下方的父级菜单栏时，该菜单栏会向上滑动，但不能及时取消鼠标悬停效果，该判断是为了正确取消hover效果，是个细节处理
        if ($(this).index() > $(".leftBox:visible").index()) {
            $(this).children("h2").trigger("mouseleave");
        }
        $(this).next(".leftBox").show("fast",function () {
            //show的动画结束后重新设置滚动条，这也是为了兼容IE6
            $(this).css("overflow", "auto");
        }).siblings(".leftBox").hide("fast");
    })
    //IE6的css只支持a标签的hover，所以其他标签的鼠标悬停效果都要用js写
    $(".leftTop h4").hover(function () {
        $(this).addClass("lefttophovering");
    }, function () {
        $(this).removeClass("lefttophovering");
    })
})