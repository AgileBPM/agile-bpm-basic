<#setting number_format="0">
<div class="panel-page">
    <div class="l-panel-bbar-inner">
		<#if (showPageSize)>
		<div class="l-bar-group l-bar-selectpagesize">
            每页记录&nbsp;<select id="pageSize" name="pageSize" onchange="changePageSize(this,'${tableIdCode}');"
                              class="select_short">
            <option value="5" selected="selected">5</option>
            <option value="10" selected="selected">10</option>
        </select>
        </div>
        </#if>
        <div class="l-bar-separator"></div>
        <div class="l-bar-group">
            <div class="l-bar-button l-bar-btnload">
                <a href="javascript:window.location.reload()">
                    <span class=""></span>
                </a>
            </div>
        </div>
		<#if (showExplain) >
			<div class="l-bar-separator"></div>
			<div class="l-bar-group">
                <span class="l-bar-text">显示记录从1到10，总数 20条</span>
            </div>

        </#if>
        <div class="l-bar-group-right">
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <div class="l-bar-button l-bar-btnfirst">
                    <a href="#" onclick="first('${tableIdCode}')" title="首页">
                        <span class=""></span>
                    </a>
                </div>
                <div class="l-bar-button l-bar-btnprev">
                    <a href="#" onclick="previous('${tableIdCode}');" title="上一页">
                        <span class=""></span>
                    </a>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
			<span class="pcontrol"> 
				<input size="4" value="1" style="width: 20px;text-align:center" maxlength="3" class="inputText"
                       type="text" id="navNum${tableIdCode}" name="navNum"/>/ <span>20</span>
			</span>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <div class="l-bar-button l-bar-btnnext">
                    <a href="#" onclick="next('${tableIdCode}')" title="下一页">
                        <span></span>
                    </a>
                </div>
                <div class="l-bar-button l-bar-btnlast">
                    <a href="#" onclick="last('${tableIdCode}')" title="尾页">
                        <span></span>
                    </a>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
			<span>
				<input type="button" id="btnGo" value="GO" class="btn-go" onclick="jumpTo('${tableIdCode}');"/>
			</span>
            </div>
        </div>
        <div class="l-clear"></div>

        <a id="_nav${tableIdCode}" href="${baseHref}" style="display:none"></a>
    </div>
</div>
