<#assign base=request.contextPath/><#--项目上下文路径-->
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
        <col width="4%">
        <col/>
        <col width="10%">
        <col width="10%">
    </colgroup>
    <tr>
        <th>序号</th>
        <th>角色名称</th>
        <th>角色代码</th>
        <th>操作</th>
    </tr>
<#if xtjsList??&&xtjsList?size gt 0>
    <#list xtjsList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!''}">${(option.offset+o_index)!}</td>
            <td title="${(o.JSMC)!}">${(o.JSMC)!''}</td>
            <td title="${(o.JSDM)!}">${(o.JSDM)!''}</td>
            <td>
                <a href="javascript:void(0)" title="修改" class="field" onclick="editXtjs('edit',${(o.ID)?c!''})"><i
                        class="iconfont">&#xe628;</i></a>
                    <a href="javascript:void(0)" title="删除" class="field scc" onclick="delXtjs(${(o.ID)?c!''})"><i
                            class="iconfont">&#xe61a;</i></a>

            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="" colspan="4">无记录</td>
    </tr>
</#if>
</table>
<#if xtjsList??&&xtjsList?size gt 0>
<div id="pagination" style="float: right;"></div>
<div class="rx-paging simple-pagination">
    <span>
        共<b style="padding:0 3px;color:red;">${((option.total!0)/(option.pageSize!10))?ceiling}</b>页<b
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