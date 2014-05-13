<#if canOutputHtml>
<ul <#if attrid??>id="${attrid}"</#if>>${name}
	<#list childs as cmenu>
		<#if cmenu.canOutputHtml>
			<li <#if cmenu.attrid??>id="${cmenu.attrid}"</#if>><a href="${cmenu.url}" >${cmenu.name}</a></li>
		</#if>
	</#list>
</ul>
</#if>
