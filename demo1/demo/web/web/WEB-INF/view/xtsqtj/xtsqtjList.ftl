<#assign base=request.contextPath/><#--项目上下文路径-->
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
    <col width="10%"/>
    <col/>
    <col width="20%"/>
    <col width="20%"/>
    </colgroup>
    <tr>
        <th>序号</th>
        <th>机构名称</th>
        <th>社区数量</th>
        <th>详情</th>
    </tr>
<#if xtsqtjList??&&xtsqtjList?size gt 0>
<input id="data" type="hidden" value="${(json)!}">
    <#list xtsqtjList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!}">${(option.offset+o_index)!}</td>
            <#if o.NEXT==0>
                <td title="${(o.JGMC)!}">${(o.JGMC)!}</td>
            <#else>
                <td title="${(o.JGMC)!}"> <a href="javascript:void(0)" onclick="query(1,${(o.ID)!})">${(o.JGMC)!}</a></td>
            </#if>

            <td title="${(o.SQNUM)!}">${(o.SQNUM)!}</td>
            <td><a href="javascript:void(0)" onclick="openDetail(${(o.ID)!})">社区详情</a></td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="4">无记录</td>
    </tr>
</#if>
</table>
<#if xtsqtjList??&&xtsqtjList?size gt 0>
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

    function openDetail(id) {
        var url = "${base}/xtsqtj/xtsqIndex?jgId="+id;
        openPage({
            area: ['900px', '600px'],
            type: 2,
            title: "社区详情",
            shade: 0.5,
            content: url
        });
    }
</script>
</#if>

