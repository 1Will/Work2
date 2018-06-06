<#assign base=request.contextPath/>
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
        <col width="4%"/>
        <col width="20%"/>
        <col width="10%"/>
        <col width="20%"/>
        <col/>
        <col width="25%"/>
        <col width="10%"/>
    </colgroup>
    <tr>
        <th>序号</th>
        <th>菜单名称</th>
        <th>所属系统</th>
        <th>上级菜单</th>
        <th>URL</th>
        <th>操作时间</th>
        <th>操作</th>
    </tr>
<#if xtcdList??&&xtcdList?size gt 0>
    <#list xtcdList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!""}">${(option.offset+o_index)!""}</td>
            <td title="${(o.CDMC)!''}">${(o.CDMC)!''}</td>
            <td title="<#if o.SSXT??&&o.SSXT=='0'>外网<#else >内网</#if>"><#if o.SSXT??&&o.SSXT=='0'>外网<#else >内网</#if></td>
            <td title="${(o.SJCDMC)!''}">${(o.SJCDMC)!''}</td>
            <td title="${(o.URL)!''}">${(o.URL)!''}</td>
            <td title="${(o.CZSJ)!''}">${(o.CZSJ)!''}</td>
            <td>
                <a href="javascript:void(0)" title="修改" class="field" onclick="edit('xg','${(o.ID?c)!''}')"><i
                        class="iconfont">&#xe628;</i></a>
                <a href="javascript:void(0)" title="删除" class="field scc" onclick="del('${(o.ID?c)!''}','${(o.SSXT)!}')"><i
                        class="iconfont">&#xe61a;</i></a>
            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="7">无记录</td>
    </tr>
</#if>
</table>

<#--分页-->
<#if xtcdList??&&xtcdList?size gt 0>
<div id="pagination" style="float: right;"></div>
<div class="rx-paging simple-pagination">
    <span>
        共<b style="padding:0 3px;color:red;">${((option.total!0)/(option.pageSize!10))?ceiling}</b>页<b
            style="padding:0 3px;color:red;">${option.total!0}</b>条数据
    </span>
</div>
<script type="text/javascript">
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
