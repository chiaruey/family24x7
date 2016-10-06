<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

	<div id="adminContentWrapper" class="contentWrapper">
		
		<div id="adminContentPanelWrapper" class="centerPanelWrapper">
			<tiles:insertAttribute name="content" />
		</div>	
		<div id="adminleftPanel">
			<div class="ui-corner-all" id="myInfoWrapper">
				<h2 id="myInfoTitle"> My Information </h2>
				<table>
					<tbody>
						<tr><th scope="row">Username:</th><td>${user.username }</td></tr>					
						<tr><th scope="row">Role:</th><td>${user.roleName }</td></tr>
						<tr><th scope="row">Street:</th><td>${family.address.street }</td></tr>
						<tr><th scope="row">City:</th><td>${family.address.city }</td></tr>
						<tr><th scope="row">State:</th><td>${family.address.state }</td></tr>
						<tr><th scope="row">Country:</th><td>${family.address.country }</td></tr>
					</tbody>
				</table>
			</div>
			
			<br />
			<ul id="adminLeftMenu">
				<li id="manageAccountLink" class="selected jqButton"><span>Manage Account Settings</span></li>
				<c:if test="${user.admin}"> 
					<li id="manageFamilyLink" class="default jqButton"><span>Manage My Family</span></li>
				</c:if>
				<li id="manageAddressLink" class="default jqButton"><span>My Home Address</span></li>
				<c:if test="${user.admin}"> 
					<li id="manageTracTypeLink" class="default jqButton"><span>Manage Money Transition Type</span></li>
				</c:if>
			</ul>			
		</div>

	</div> 
	<script type="text/javaScript" src="${requestScope.appPath}static/js/family/family.admin.js" ></script>