<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

	<div class="row">
		
		<div class="col-md-4">
			<div class="ui-corner-all" id="myInfoWrapper">
				<h2> My Information </h2>
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
			
			<div class="list-group">
				<a  href="javascript:void(0)" id="manageAccountLink" class="list-group-item active"><span>Manage Account Settings</span></a>
				<c:if test="${user.admin}"> 
					<a href="javascript:void(0)" id="manageFamilyLink" class="list-group-item"><span>Manage My Family</span></a>
				</c:if>
				<a href="javascript:void(0)" id="manageAddressLink" class="list-group-item"><span>My Home Address</span></a>
				<c:if test="${user.admin}"> 
					<a href="javascript:void(0)" id="manageTracTypeLink" class="list-group-item"><span>Manage Money Transition Type</span></a>
				</c:if>
			</div>			
		</div>
		
		<div  class="col-md-8">
			<tiles:insertAttribute name="content" />
		</div>	
		

	</div> 
	<script type="text/javaScript" src="${requestScope.appPath}static/js/family/family.admin.js" ></script>