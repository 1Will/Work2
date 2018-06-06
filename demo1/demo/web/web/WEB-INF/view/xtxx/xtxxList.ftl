<#assign base=request.contextPath/>
<table cellpadding="0" cellspacing="0" border="0" class="list" id="tbColor">
    <colgroup>
        <col width="5%"/>
        <col width="20%"/>
        <col width="15%"/>
        <col width="10%"/>
        <col/>
        <col width="15%"/>
        <col width="5%"/>
        <col width="15%"/>
    </colgroup>
    <tr>
        <th>序号</th>
        <th>信息标题</th>
        <th>关键字</th>
        <th>发布人</th>
        <th>发布单位</th>
        <th>发布时间</th>
        <th>发布状态</th>
        <th>操作</th>
    </tr>
<#if xtxxList??&&xtxxList?size gt 0>
    <#list xtxxList as o>
        <tr class="<#if o_index%2==0>trhover<#else>se trhover</#if>">
            <td title="${(option.offset+o_index)!""}">${(option.offset+o_index)!""}</td>
            <td title="${(o.XXBT)!''}">${(o.XXBT)!''}</td>
            <td title="${(o.GJZ)!''}">${(o.GJZ)!''}</td>
            <td title="${(o.FBR)!''}">${(o.FBR)!''}</td>
            <td title="${(o.FBDW)!''}">${(o.FBDW)!''}</td>
            <td title="${(o.FBSJ)!''}">${(o.FBSJ)!''}</td>
            <#if o.FBZT?? && o.FBZT == '1'>
                <td title="已发布">已发布</td>
            <#else>
                <td title="草稿">草稿</td>
            </#if>
            <td>
                <#if o.FBZT?? && o.FBZT == '1'>
                    <#if o.TOP?? && o.TOP == '1'>
                        <a href="javascript:void(0)" title="取消置顶" class="field" onclick="toTop('${(o.ID?c)!''}',0)"><i
                                class="iconfont">&#58914;</i></a>
                    <#elseif o.TOP?? &&  o.TOP == '0'>
                        <a href="javascript:void(0)" title="置顶" class="field" onclick="toTop('${(o.ID?c)!''}',1)"><i
                                class="iconfont">&#59005;</i></a>
                    </#if>
                <#else >
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                </#if>

                <a href="javascript:void(0)" title="修改" class="field" onclick="edit('edit','${(o.ID?c)!''}')"><i
                        class="iconfont">&#xe628;</i></a>
                <a href="javascript:void(0)" title="删除" class="field scc"
                   onclick="del('${(o.ID?c)!''}','${(o.SSXT)!}')"><i
                        class="iconfont">&#xe61a;</i></a>
            </td>
        </tr>
    </#list>
<#else>
    <tr class="trhover">
        <td class="none" colspan="8">无记录</td>
    </tr>
</#if>
</table>

<#--分页-->
<#if xtxxList??&&xtxxList?size gt 0>
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
