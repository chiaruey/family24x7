<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>

<!-- This tag is to be queried by JavaScript for Admin Type -->
<div class="adminType manageFamily" ></div>

<div>

	<div id="manageFamilyHeader">
		<span class="title">Manage My Family Member(s) </span>
		<div class="floatRight padding10px">
			<form>
				<input
					class="jqButton ui-state-default ui-corner-all ui-widget ui-button addNewBtn"
					type="submit" id="addFamilyBtn" value="+ Add new" />
			</form>
		</div>
	</div>
	<table class="jqTable centerTable">
		<thead>
			<tr>
				<th>Role</th>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Admin</th>
				<th>Date Of Birth</th>
				<th>active</th>
				<c:if test="${user.admin}">
					<th>Options</th>
				</c:if>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${family.familyMember}" var="familyMember">
				<tr class="transition">
					<td>${familyMember.roleName}</td>
					<td>${familyMember.username}</td>
					<td>${familyMember.firstName}</td>
					<td>${familyMember.lastName}</td>
					<td>${familyMember.email}</td>
					<td>${familyMember.admin}</td>
					<td><fmt:formatDate value="${familyMember.dob}" /></td>
					<td
						<c:choose>
					        <c:when test="${familyMember.active == 'true' }">
					        	class="green"
					        </c:when>
					        <c:otherwise>
					        	class="red"
					        </c:otherwise>
				        </c:choose>>${familyMember.active}
				    </td>
					<c:if test="${user.admin}">
						<td><select class="pct90"
							onchange="doCommand(this, '${familyMember.username}')">
								<option value="select">Select</option>
								<option value="edit">Edit</option>
								<option value="resetPassword">Reset Password</option>
								<c:if test="${family.househead.id != familyMember.id && user.id != familyMember.id}">
									<c:choose>
										<c:when test="${familyMember.active == 'true' }">
											<option value="deactivate">deactivate</option>
										</c:when>
										<c:otherwise>
											<option value="activate">activate</option>
										</c:otherwise>
									</c:choose>
								</c:if>
						</select></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>


<admin:addFamilyPopup />
<admin:editFamilyPopup />
<admin:resetFamilyPasswordPopup />