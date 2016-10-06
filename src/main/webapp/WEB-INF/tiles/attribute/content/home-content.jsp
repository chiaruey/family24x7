<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="home" tagdir="/WEB-INF/tags/home"%>


<div class="row">
	<div class="col-md-4">
		<div>
			<h3 class="text-info">Your Information</h3>
			<table class="table table-bordered width98pct font0_9em text-info">
				<tbody>
					<tr>
						<th scope="row">Username:</th>
						<td>${user.username }</td>
					</tr>
					<tr>
						<th scope="row">Role:</th>
						<td>${user.roleName }</td>
					</tr>
					<tr>
						<th scope="row">Street:</th>
						<td>${family.address.street }</td>
					</tr>
					<tr>
						<th scope="row">City:</th>
						<td>${family.address.city }</td>
					</tr>
					<tr>
						<th scope="row">State:</th>
						<td>${family.address.state }</td>
					</tr>
					<tr>
						<th scope="row">Country:</th>
						<td>${family.address.country }</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="minHeight20"></div>
		<div id="myFamilyWrapper">

			<h3 class="text-info">Family Members</h3>
			<div id="myFamily" class="panel-group">
				<c:forEach items="${family.familyMember}" var="familyMember">
					<c:if
						test="${user.id != familyMember.id && familyMember.active == true}">
						<div class="panel panel-info">
							<div class="panel-heading familyMemberHeading"><span class="glyphicon glyphicon-user"></span> ${familyMember.firstName} ${familyMember.lastName}</div>
							<table class="familyMemberTable table table-bordered text-info">
								<tr>
									<th scope="row">Username:</th>
									<td>${familyMember.username}</td>
								</tr>
								<tr>
									<th scope="row">Role:</th>
									<td>${familyMember.roleName}</td>
								</tr>
								<tr>
									<th scope="row">First Name:</th>
									<td>${familyMember.firstName}</td>
								</tr>
								<tr>
									<th scope="row">Last Name:</th>
									<td>${familyMember.lastName}</td>
								</tr>
								<tr>
									<th scope="row">Email:</th>
									<td>${familyMember.email}</td>
								</tr>
							</table>
						</div>

					</c:if>
				</c:forEach>
			</div>
		</div>

	</div>

	<div class="col-md-8">

		<form id="WallMessageForm">
			<div class="row">
				<div class="col-sm-8"><h3 class="text-info">Wall Messages</h3></div>
				<div class="col-sm-4">
				<input
					class="btn btn-primary"
					type="submit" id="openAddMessagePopupBtn" value="+ Add my message" />
				</div>
			</div>
			<div>
				<table id="wallMessageTable" class=" table table-hover table-striped width98pct font0_9em text-info">
					<c:forEach items="${wallMessages}" var="wm">
						<tr>
							<td><img src="${wm.imagePath}"></td>
							<td><fmt:formatDate type="date"
										pattern="MM/dd/yy" value="${wm.createDate}" />
								<br />
								<fmt:formatDate type="time" pattern="HH:mm" value="${wm.createDate}" />
							</span></td>
							<td>
								<h5>${wm.message}</h5>
							</td>
							<td><a href="#" onclick="deleteWallMessage(${wm.id})"><span class="glyphicon glyphicon-remove text-info small"></span></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</div>	

</div> 

<home:addMyMessagePopup />
	
<script type="text/javaScript" src="static/js/family/family.home.js" ></script>