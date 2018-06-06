<#assign base=request.contextPath/><#--项目上下文路径-->
<table cellpadding="0" cellspacing="0" border="0" class="list">
    <col width="4%"/>
    <col width="30%"/>
    <col />
    <col width="30%"/>
    <col width="10%"/>
    <tr>
        <th>序号</th>
        <th>字典名称</th>
        <th>字典代码</th>
        <th>上级字典</th>
        <th>操作</th>
    </tr>
<#if xtzdList??&&xtzdList?size gt 0>
    <#list xtzdList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!}">${(option.offset+o_index)!}</td>
            <td title="${(o.ZDMC)!}">${(o.ZDMC)!}</td>
            <td title="${(o.ZDDM)!}">${(o.ZDDM)!}</td>
            <td title="${(o.SJZDMC)!}">${(o.SJZDMC)!}</td>
            <td>
                <a title="修改" href="javascript:void(0);" class="field" onclick="xtzdEdit('${(o.ID)!}')">
                    <i class="iconfont">&#xe628;</i>
                </a>
                <a title="删除" href="javascript:void(0);" class="field scc" onclick="deleteXtzd('${(o.ID)!}')">
                    <i class="iconfont">&#xe61a;</i>
                </a>
            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="5">无记录</td>
    </tr>
</#if>
</table>
<#if xtzdList??&&xtzdList?size gt 0>
<div id="pagination" style="float: right;"></div>
<div class="rx-paging simple-pagination">
    <span>
        共<b style="padding:0 3px;color:red;">${(option.total/option.pageSize)?ceiling!1}</b>
        页<b style="padding:0 3px;color:red;">${option.total!0}</b>条数据
    </span>
</div>
<script type="text/javascript" language="javascript">
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