<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>

<!-- This tag is to be queried by JavaScript for Admin Type -->
<div class="adminType manageAddress"></div>

<div id="manageAddressDiv">

	<div id="manageAddressHeader">
		<c:choose>
			<c:when test="${user.admin}">
				<span class="title">Manage My Home Address</span>
			</c:when>
			<c:otherwise>
				<span class="title">My Home Address</span>
			</c:otherwise>

		</c:choose>

	</div>
	<c:if test="${addressUpdated == true}">
		<p class="bold green">Your home address has been successfully
			updated</p>
	</c:if>
	<div id="manageAddressTable">
		<c:choose>
			<c:when test="${user.admin}">
				<form id="updateAddressForm" name="updateAddressForm"
					action="${requestScope.appPath}admin/updateAddress" method="post">
					<input type="hidden" name="id" value="${family.address.id }" />
					<table>
						<tr>
							<th nowrap="nowrap" align="right"><div>
									<span>Street:</span>
								</div></th>
							<td>
								<div class="manageAddressRowDiv">
									<input type="text" name="street"
										value="${family.address.street}"
										class="required tracInput text ui-widget-content ui-corner-all"
										size="50" />
								</div>
							</td>
						</tr>
						<tr>
							<th nowrap="nowrap" align="right">City:</th>
							<td>
								<div class="manageAddressRowDiv">
									<input type="text" name="city" value="${family.address.city}"
										class="required tracInput text ui-widget-content ui-corner-all"
										size="25" />
								</div>

							</td>
						</tr>
						<tr>
							<th nowrap="nowrap" align="right">State:</th>
							<td>
								<div class="manageAddressRowDiv">
									<input type="text" name="state" value="${family.address.state}"
										class="required tracInput text ui-widget-content ui-corner-all"
										size="25" />
								</div>

							</td>
						</tr>
						<tr>
							<th nowrap="nowrap" align="right">Country:</th>
							<td>
								<div class="manageAddressRowDiv">
									<input type="text" name="country"
										value="${family.address.country}"
										class="required tracInput text ui-widget-content ui-corner-all"
										size="25" />
								</div>

							</td>
						</tr>
						<tr>
							<th nowrap="nowrap" align="right">ZIP:</th>
							<td>
								<div class="manageAddressRowDiv">
									<input type="text" name="zip" value="${family.address.zip}"
										class="required tracInput text ui-widget-content ui-corner-all"
										size="25" />
								</div>

							</td>
						</tr>

						<tr>
							<td></td>
							<td>
								<div class="manageAccountRowDiv">
									<input
										class="jqButton ui-state-default ui-corner-all ui-widget ui-button"
										type="submit" id="modifyHomeAddressBtn" value="Update Address" />
								</div>

							</td>
						</tr>

					</table>

				</form>
			</c:when>
			<c:otherwise>

				<table class='jqTable centerTable'>
					<tr>
						<th nowrap="nowrap" align="right"><div>
								<span>Street:</span>
							</div></th>
						<td><div class="manageAddressRowDiv">${family.address.street}</div></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">City:</th>
						<td><div class="manageAddressRowDiv">${family.address.city}</div></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">State:</th>
						<td><div class="manageAddressRowDiv">${family.address.state}</div></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">Country:</th>
						<td><div class="manageAddressRowDiv">${family.address.country}</div></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">ZIP:</th>
						<td><div class="manageAddressRowDiv">${family.address.zip}</div></td>
					</tr>

				</table>

			</c:otherwise>
		</c:choose>

	</div>

</div>