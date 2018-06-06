<#assign base=request.contextPath/>
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
        <col width="20%"/>
        <col/>
        <col width="20%"/>
    </colgroup>
    <tr>
        <th>附件序号</th>
        <th>附件名称</th>
        <th>操作</th>
    </tr>
<#if xtfjList??&&xtfjList?size gt 0>
    <#list xtfjList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!""}">${(option.offset+o_index)!""}</td>
            <td title="${(o.FJMC)!""}">${(o.FJMC)!''}</td>
            <td>

                <a href="/xtfj/downloadXtfj?id=${(o.ID?c)!''}" title="下载" class="field scc""><i
                    class="iconfont">&#58928;</i></a>
                <a href="javascript:void(0)" title="删除" class="field scc"
                   onclick="delFj('${(o.ID?c)!''}')"><i
                        class="iconfont">&#xe61a;</i></a>
            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover" id="nofj">
        <td class="none" colspan="3">无附件</td>
    </tr>
</#if>
</table>
<#if xtfjList??&&xtfjList?size gt 0>
<div id="pagination" style="float: right;"></div>
<div class="rx-paging simple-pagination">
        <span>
            共<b style="padding:0 3px;color:red;">${((option.total!0)/(option.pageSize!3))?ceiling}</b>页<b
                style="padding:0 3px;color:red;">${option.total!0}</b>条数据
        </span>
</div>
<script type="text/javascript">
    $("#pagination").pagination({
        items:${option.total!0},
        currentPage:${option.pageNo!1},
        itemsOnPage:${option.pageSize!3},
        displayedPages: 5,
        edges: 1,
        prevText: '<',
        nextText: '>',
        cssStyle: 'classic-theme'
    });
</script>
</#if>