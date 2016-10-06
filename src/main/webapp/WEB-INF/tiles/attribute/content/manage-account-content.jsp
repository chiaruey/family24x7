<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>

<!-- This tag is to be queried by JavaScript for Admin Type -->
<div class="adminType manageAccount" ></div>

<div id="manageAccountDiv">
	<div id="manageAccountHeader">
		<span class="title">Manage Account Setting</span>
	</div>
	<c:if test="${accountUpdated == true}">
		<p class="bold green">Your account has been successfully updated</p>
	</c:if>
	<form id="updateAccountSettingsForm" name="updateAccountSettingsForm"
		action="${requestScope.appPath}admin/updateAccount" method="post">
		<input type="hidden" name="userId" value="${user.id }" />

		<table id="manageAccountTable">
			<tr>
				<th>
					<div align="right">
						<span>Username: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="username" type="text" name="username" size="18"
							value="${user.username}" disabled/>
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<div align="right">
						<span>Email: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="email" type="text" name="email" size="50" value="${user.email}" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<div align="right">
						<span>Name: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="firstName" type="text" name="firstName" size="18"
							value="${user.firstName}" /> 
							<input id="lastName" type="text"
							name="lastName" size="18" value="${user.lastName}" />
					</div>
				</td>
			</tr>
			<tr>
				<th>
					<div align="right">
						<span>Role: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="role" type="text" name="role" size="18"
							value="${user.roleName}"  disabled />
					</div>
				</td>
			</tr>

			<tr>
				<th>
					<div align="right">
						<span>Secure Question: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<select name="secureQuestion">
							<c:forEach items="${secureQuestionList}" var="secureQuestion">
								<option value="${secureQuestion.id}"
									<c:if test="${secureQuestion.id == user.secureQuestionId}">
										selected
									</c:if>														
								>${secureQuestion.secureQuestionText}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>

			<tr>
				<th>
					<div align="right">
						<span>Secure Answer:</span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="secureQuestionAnswer" type="text" name="secureQuestionAnswer" size="18"
							value="${user.secureQuestionAnswer }" />
					</div>
				</td>
			</tr>

			<tr>
				<th>
					<div align="right">
						<span>Admin: </span>
					</div>
				</th>
				<td>
					<div class="manageAccountRowDiv">
						<input id="admin" type="text" name="admin" size="18" value="${user.admin }"
							disabled />
					</div>
				</td>
			</tr>

			<tr>
				<td></td>
				<td>
					<div class="manageAccountRowDiv">
						<input id="updateMyAccountBtn" class="jqButton" type="submit"
							name="updateMyAccountBtn" value="Update Account Settings" />
					</div>

				</td>
			</tr>


		</table>

	</form>

</div>


