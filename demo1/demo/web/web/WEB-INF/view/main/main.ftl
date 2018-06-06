<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <#assign base=request.contextPath/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Global.css" media="all">
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Main.css" media="all">
    <script src="${base}/resources/js/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${base}/resources/js/common.js" type="text/javascript"></script>
    <style>
        html, body {
            width:100%;
            background: #fff;
            overflow:hidden;
        }
    </style>
</head>
<body>
<div class="leftSide" >
    <div class="sidenav">
        <ul class="nav">
            <#if dataList??&&dataList?size gt 0>
                <#assign i = 1/>
                <#list dataList as menuList>
                   <li>
                       <a href="javascript:void(0);" target="MainIframeR" name="menu" onClick="ChangeStyle('menu',this,'${i}',3);">
                           <img src="${menuList.parentURL}"/>${menuList.parentXtcd!''}<span id="SubSideSpan${i}" class="sj"></span></a>
                       <ul id="SubSideBarNav${i}" style="display: none">
                           <#if menuList.childXtcdList??&&menuList?size gt 0>
                               <#list menuList.childXtcdList as menu>
                                    <li>
                                        <a href="${menu.ZDURL!''}" target="MainIframeR" name="menu" onClick="ChangeStyle('menu',this,'0',2);">${menu.ZDMC}</a>
                                    </li>
                               </#list>
                           </#if>
                       </ul>
                   </li>
                <#assign i = i + 1/>
                </#list>
            </#if>
        </ul>
    </div>
</div>

<div class="rightContent">
    <iframe name="MainIframeR" id="test" frameborder="0" width="100%" height="100%" scrolling="auto" src="${base}/sjtjfx/ztxxqk" ></iframe>
</div>
<script type="text/javascript" language="javascript">
    $(function(){
        //初始化内容区域高度
        $("#test").width($(window).width() -191);
        $(".content").height($(window).height() -1);
    });
    //页面大小改变时，触发jquery的resize方法，自适应拖拽
    $(window).resize(function(){
        $("#test").width($(window).width() -191);
        $(".content").height($(window).height() -1);
    });
</script>
</body>
</html>
