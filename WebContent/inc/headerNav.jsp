<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%
String userName = request.getRemoteUser(); 
%>
<div class="lotusFrame" >
<form method="post" action="ibm_security_logout" id='logoutForm' name=”logout” style='display:none'>
   <input type="submit" name="logout" value="Logout">
   <!—logoutExitPage denotes the destination page after the user
       logs out -->
   <input type="hidden" name="logoutExitPage" value="/">
</form>
<script>
function logout(){
	dojo.byId("logoutForm").submit();
}
</script>
		<div class="lotusBanner" role="banner">
			<div class="lotusRightCorner" >	
				<div class="lotusInner">
					<a href="http://www-12.lotus.com/ldd/doc/oneuidoc/docpublic/examples/3colSummary.htm#lotusMainContent" accesskey="S" class="lotusAccess">
						<img src="${ctx}/resources/images/blank.gif" alt="Skip to main content link. Accesskey S">
					</a> 
					<div class="lotusLogo">
						<span class="lotusAltText">Lotus &lt;Product Name&gt;</span>
					</div>
					<ul class="lotusInlinelist lotusUtility">
						<li class="lotusFirst">
							<span class="lotusUser"><%=userName%></span>
						</li>
						<li>
							<a href="javascript:logout();">注销</a>
						</li>
					</ul>
					<ul class="lotusInlinelist lotusLinks" role="navigation" style="">
						<li class="lotusFirst lotusSelected"><a href="${ctx}/admin">首页</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="lotusTitleBar">
			<div class="lotusRightCorner">
				<div class="lotusInner">
					<ul class="lotusTabs" role="navigation">
						<!--put class="lotusSelected" on the li element of the selected tab-->
						<li class="lotusSelected">
							<div>
								<a href="#">首页</a>
							</div>
						</li>
										
					</ul>
					
						<form class="lotusSearch" action="${ctx}/admin/ctrl/ldap/searchPerson" method="post" role="search" target="_blank">
							<table class="lotusLayout" cellspacing="0" role="presentation" border="0">
								<tbody>
									<tr>
										<td style="padding-right:12px;font-family: '微软雅黑';color: white;vertical-align: middle;font-size: 11pt;font-weight: bold;">
											搜索
										</td>
										<td><input id="searchParam" name="searchParam"
											class="lotusText lotusInactive" type="text"
											value="输入搜索关键字" title="Enter Keywords"></td>
										<td>
											<span class="lotusBtnImg" title="搜索">
												<input class="lotusSearchButton" type="image" alt="" aria-label="search button" src="${ctx}/resources/images/blank.gif">
												<span class="lotusAltText">Search</span>
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
				</div>
			</div>
		</div>