<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/inc/import.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
	<%@include file="/inc/meta.jsp"%>   
	<script type="text/javascript" src="${ctx}/resources/scripts/medialib/index.js"></script>
	<style>
		@IMPORT url("${ctx}/resources/scripts/dojo/dojox/image/resources/Lightbox.css");
		@IMPORT url("${ctx}/resources/scripts/dojo/medialib/themes/MultiUploadImagePanel.css");
	</style>
	<script type="text/javascript">  
	require(
	// 模块标识集合
	[ "dojo", "dojo/parser", "dijit/Tree", "dojox/data/JsonRestStore","medialib/ThumbnailPicList","dojo/store/JsonRest",
	  "dojox/image/Lightbox","dijit/form/DateTextBox"],
	// 回调函数，调用依赖性求值结果
	function(dojo) {
		dojo.ready(function() {
			init();
		});
	});
	</script>
</head> 

<body class="lotusui lotusSpritesOn claro">
	<div data-dojo-id="store" data-dojo-type="dojox.data.JsonRestStore" data-dojo-props="target:'${pageContext.request.contextPath}/admin/ctrl/dir/dirtree/',labelAttribute:'name'"></div>
	<div data-dojo-id="model" data-dojo-type="dijit.tree.ForestStoreModel" data-dojo-props="store:store,deferItemLoadingUntilExpand:true,rootLabel:'全部',rootId:'root',query:'rootNodes',childrenAttrs:['children']"/>
	<div data-dojo-id="mediaListStore" data-dojo-type="dojo.store.JsonRest"  data-dojo-props="target:'${ctx}/admin/ctrl/media/list/'"></div>
	<%@include file="/inc/headerNav.jsp"%>
	<!-- 主界面 -->  
	<div class="lotusMain">
		<div class="lotusColLeft">
			<div class="lotusMenu leftMenuFrame">
				<div class="lotusBottomCorner">
					<div class="lotusInner">
						<ul>
							<li class="lotusSelected" role="menuitem">
							<a href="javascript:;">我的目录</a></li>
						</ul>
						<div style="" class="leftMenuGraphicDiv">
							<div id="leftDirTree" data-dojo-type="dijit.Tree" model="model" showRoot="true" persist="true" style="padding: 2px 5px 2px 5px;"></div>
						</div>
						<ul>
							<li class="lotusSelected" role="menuitem">
							<a href="javascript:;">我的标签</a></li>
						</ul>
						<div style="" class="leftMenuGraphicDiv tagPanelDiv">
						<a href='#'>花</a>
						<a href='#'>鸟</a>
						<a href='#'>鱼</a>
						<a href='#'>虫</a>
						<a href='#'>工程图</a>
						<a href='#'>主任图</a>
						<a href='#'>big</a>
						<a href='#'>巨兔</a>
						<a href='#'>无标题</a>
						<div align="right">
							<a href='#' style="font-weight: normal;">更多...</a>
						</div>
						</div>
					</div>
				</div> 
			</div> 		 
		</div>
 
		<div class="lotusColRight"> 
			<div class="lotusWidget2"> 
				<h2>
					<span class="lotusLeft">图片分类</span> 
					<a class="lotusIcon lotusActionMenu" href="javascript:;" aria-haspopup="true">
						<img src="${ctx}/resources/css/images/blank.gif" alt="" aria-label="actions button" /> 
						<span class="lotusAltText">Actions</span>
					</a>
					<!--if you are surfacing another button, add it here (you'll have to create a class and CSS for it)-->
				</h2>
				<div class="lotusWidgetBody">
					<div class="catalogTitle">按日期分类</div>
					<div class="catalogItem catalogItemSelected"><a href='javascript:void()'>最近一周</a></div>
					<div class="catalogItem catalogItemSelected"><a href='javascript:void()'>最近一月</a></div>
					<div class="catalogItem"><a href='javascript:void()'>最近一年</a></div>
					<div class='catalogSplit'></div>
					<div class="catalogTitle">按一级分类</div>
					<div class="catalogItem catalogItemSelected"><a href='javascript:void()'>个人图片</a></div>
					<div class="catalogItem"><a href='javascript:void()'>部门图片</a></div>
					<div class="catalogItem"><a href='javascript:void()'>公司图片</a></div>
					<div class='catalogSplit'></div>
					<div class="catalogTitle">按二级分类</div>
					<div class="catalogItem catalogItemSelected"><a href='javascript:void()'>工程图纸</a></div>
					<div class="catalogItem"><a href='javascript:void()'>宣传资料</a></div>
					<div class="catalogItem"><a href='javascript:void()'>亲戚朋友</a></div>
					<div class="catalogItem"><a href='javascript:void()'>名胜古迹</a></div>
					<div class="catalogItem"><a href='javascript:void()'>特写</a></div>
					<div class="catalogItem"><a href='javascript:void()'>随拍</a></div>
					<div class="catalogItem"><a href='javascript:void()'>名山大川</a></div>
					<div class="catalogItem catalogItemSelected"><a href='javascript:void()'>保密</a><div class="removeCatalogHandler"><a href='javascript:void(0);'><img width="20px" height="20px" src="${ctx}/resources/images/blank.gif"/></a></div></div>
					
				</div>
				<!--end widget body-->

			</div>
			<!--end widget-->
		</div>
 
		<!--end colRight-->
		<div class="lotusContent" role="main">
			<div class="lotusWidget2">
				<h2>
					<span class="lotusLeft">多媒体文件列表</span> 
					
					<table class="lotusViewControl lotusRight" cellpadding="0" role="presentation">
						<tbody>
						  	<tr>
								<td>
						    		<div>
						    			<a id="viewAsTilesToggleLink" class="lotusSprite lotusView lotusTileOff" title="缩略图" href="javascript:;">
											<span class="lotusAltText">Tiles</span>
										</a>
						    			<a id="viewAsListToggleLink" class="lotusSprite lotusView lotusSummaryOn" title="详细列表">
						    				<span class="lotusAltText">Summary</span>
						    			</a>
										<!-- <a id="viewAsDetailToggleLink" class="lotusSprite lotusView lotusDetailsOn lotusSelected" title="Details for all entries" href="javascript:;">
											<span class="lotusAltText">Details</span>
										</a>
										<a id="viewAsTreeToggleLink" class="lotusSprite lotusView lotusTreeOff" title="Tree view of all entries" href="javascript:;">
											<span class="lotusAltText">Tree</span>
										</a> -->
										
									</div>
								</td>
							   
						 	</tr>
						</tbody>
					</table>
				</h2>
				</div>
				<div class="lotusWidgetBody">
					<div style="padding-left: 5px;">
						<a href="javascript:openUploadDialog();">上传图片</a>
						<a href="javascript:#;">全屏浏览</a>
						<a href="javascript:freshList();">刷新</a>
					</div>
					<div data-dojo-id="piclist" id="piclist" data-dojo-type="medialib.ThumbnailPicList" data-dojo-props="store:mediaListStore">
						<span class='loadingText'><img src="${ctx}/resources/images/loading.gif"></img> 加载数据中，请稍等。。。。</span>
					</div>
				</div>
			<!--end widget-->

		</div>
	</div>

	<%@include file="/inc/footer.jsp"%>
	</div>
</body>
</html>