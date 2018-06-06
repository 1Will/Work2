<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
    <col width="5%"/>
    <col width="25%"/>
    <col width="25%"/>
    <col width="10%"/>
    <col width="25%"/>
    <col width="10%"/>
    </colgroup>
    <tr>
        <th>序号</th>
        <th>社区名称</th>
        <th>所属机构</th>
        <th>操作用户</th>
        <th>操作时间</th>
        <th>操作</th>
    </tr>
<#if xtsqList??&&xtsqList?size gt 0>
    <#list xtsqList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!}">${(option.offset+o_index)!}</td>
            <td title="${(o.SQMC)!}">${(o.SQMC)!}</td>
            <td title="${(o.JGMC)!}">${(o.JGMC)!}</td>
            <td title="${(o.CZYH)!}">${(o.CZYH)!}</td>
            <td title="${(o.CZSJ)!}">${(o.CZSJ)!""}</td>
            <td>
                <a href="javascript:void(0)" onclick="xtsqEdit('edit',${(o.ID)!})" title="修改" class="field"><i class="iconfont">&#xe628;</i></a>
                <a href="javascript:void(0)" onclick="deleteXtsq(${(o.ID)!})" title="删除" class="field scc"><i class="iconfont">&#xe61a;</i></a>
            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="6">无记录</td>
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
</script>
</#if>

