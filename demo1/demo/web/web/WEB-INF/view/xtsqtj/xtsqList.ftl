<#assign base=request.contextPath/><#--项目上下文路径-->
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
        <col width="5%"/>
        <col width="25%"/>
        <col width="25%"/>
        <col/>
        <col width="25%"/>
    </colgroup>
    <tr>
        <th>序号</th>
        <th>社区名称</th>
        <th>所属机构</th>
        <th>操作用户</th>
        <th>操作时间</th>
    </tr>
    <input type="hidden" id="sjjg" name="sjjg" value="${(sjjg)!}"/>
<#if xtsqList??&&xtsqList?size gt 0>
    <#list xtsqList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!}">${(option.offset+o_index)!}</td>

            <td title="${(o.SQMC)!}"> <a href="javascript:void(0)" onclick="xtsqDetail(${(o.ID)!})">${(o.SQMC)!}</a></td>

            <td title="${(o.JGMC)!}">${(o.JGMC)!}</td>
            <td title="${(o.CZYH)!}">${(o.CZYH)!}</td>
            <td title="${(o.CZSJ)!}">${(o.CZSJ)!""}</td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="5">无记录</td>
    </tr>
</#if>
</table>
<#if xtsqList??&&xtsqList?size gt 0>
<div id="pagination" style="float: right;"></div>
<div class="rx-paging simple-pagination">
    <span>
        共<b style="padding:0 3px;color:red;">${(option.total/option.pageSize)?ceiling!1}</b>页<b
            style="padding:0 3px;color:red;">${option.total!0}</b>条数据
    </span>
</div>
<script language="JavaScript">
    //分页初始化
    $("#pagination").pagination({
        items:${option.total!0},
        currentPage:${option.pageNo!1},
        itemsOnPage:${option.pageSize!10},
        displayedPages: 5,
        edges: 1,
        prevText: '<',
        nextText: '>',
        cssStyle: 'classic-theme'
    });


    //新增和编辑社区
    function xtsqDetail(id) {
        var url = "${base}/xtsqtj/xtsqDetail?id="+ id;
        window.location.href = url;
    }
</script>
</#if>