<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <#assign base=request.contextPath/>
    <title>技防一体化系统</title>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Global.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Main.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/iconfont.css"/>
    <script type="text/javascript">window.base = "${base}";</script>
    <script src="${base}/resources/js/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${base}/resources/js/common.js" type="text/javascript"></script>
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>

<div class="left_side" id="leftContent" width="190px">
    <div class="toggle_collapse">
    </div>
    <div class="nav_container">
    <ul class="nav">
    <#--循环顶级栏目-->
    <#if yhcdList??&&yhcdList?size gt 0>
        <#assign xh=0/><#--具有子级栏目的顶级栏目序号-->
        <#list yhcdList as djlm>
            <#if (djlm.XJGS)??&&(djlm.XJGS) gt 0><#assign xh=xh+1/></#if><#--具有子级栏目则加1-->
            <li class="djlm">
            <a href="<#if (djlm.URL)??&&(djlm.URL)!="/">${base}${(djlm.URL)!}<#else>javascript:void(0);</#if>"
               onclick="ChangeStyle('menu',this,'<#if (djlm.XJGS)??&&(djlm.XJGS) gt 0>${xh}<#else>0</#if>',${(count)!});"
               id="${(djlm.ID)!}" name="menu" target="MainIframeR"><i class="iconfont">${(djlm.XSTP)!}</i>${(djlm.CDMC)!}
                <#if (djlm.XJGS) gt 0><#--具有子级栏目时循环子级栏目-->
                    <span id="SubSideSpan${xh}" class="sj"></span></a>
                    <ul id="SubSideBarNav${xh}" class="Boxconter" style="display:none;">
                            <#list (djlm.XJCD) as zjlm>
                                    <li class="zjlm">
                                        <a id="${(zjlm.ID)!}" href="<#if (zjlm.URL)??&&(zjlm.URL)!="/">${base}${(zjlm.URL)!}<#else>javascript:void(0);</#if>"
                                           onclick="ChangeStyle('menu',this,'0',${zjlm?size});" name="menu" target="MainIframeR"><i class="iconfont">${(zjlm.XSTP)!}</i>${(zjlm.CDMC)!}</a>
                                    </li>
                            </#list>
                    </ul>
                <#else>
                    </a>
                </#if>
            </li>
        </#list>
    </#if>
    </ul>
    </div>
</div>

<div class="right_content">
    <iframe name="MainIframeR" id="test" frameborder="0" width="100%" height="100%" scrolling="auto" src="remind.ftl"></iframe>
</div>
<div class="shrink" id="leftClose" title="隐藏主菜单"></div>
<script type="text/javascript" language="javascript">
    $(function() {
        //初始化内容区域高度
        $("#test").width($(window).width() - $(".left_side").outerWidth());
        $(".nav_container").height($(window).height() - $(".toggle_collapse").outerHeight());
    })
    //页面大小改变时，触发jquery的resize方法，自适应拖拽
    $(window).resize(function() {
        $("#test").width($(window).width() - $(".left_side").outerWidth());
        $(".nav_container").height($(window).height() - $(".toggle_collapse").outerHeight());
    });
</script>
<script type="text/javascript" language="javascript">
    //初始化侧边栏
    var $oConIndex = $("#test");
    $("#leftClose").toggle(
            function() { //切换
                $("#leftContent").animate({
                    marginLeft: -200
                }, 500);
                $("#leftClose").animate({
                    marginLeft: -205
                }, 500).html("").attr("title", "展开菜单栏");
                $("#test").animate({
                    width: $oConIndex.width() + 200
                }, 500);
                $("#leftClose").addClass("launched");
            },
            function() {
                $("#leftContent").animate({
                    marginLeft: 0
                }, 500);
                $("#leftClose").animate({
                    marginLeft: 0
                }, 500).html("").attr("title", "隐藏菜单栏");
                $("#test").animate({
                    width: $oConIndex.width() - 200
                }, 500);
                $("#leftClose").removeClass("launched");
            });
</script>
<link rel="stylesheet" type="text/css" href="${base}/resources/css/iconfont.css"/>
</body>

</html>