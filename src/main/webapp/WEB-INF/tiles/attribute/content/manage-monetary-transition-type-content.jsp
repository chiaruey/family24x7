<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="money" tagdir="/WEB-INF/tags/money"%>


<!-- This tag is to be queried by JavaScript for Admin Type -->
<div class="adminType manageMoneyTransaction"></div>

<div id="manageMoneyTransactionDiv">
	<div id="manageMoneyTransactionHeader">
		<span class="title">Family Transaction Types</span>
		<div class="floatRight padding10px">
			<form>
				<input type="submit" value="+ Add new transaction type"
					id="addTracTypeBtn" class="addNewBtn jqButton floatRight " />
			</form>
		</div>
	</div>
	<table class="centerTable">
		<thead>
			<tr>
				<th>I/E</th>
				<th>name</th>
				<th>active</th>
				<th>Options</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(expenseTypeList) gt 0}">
					<c:forEach items="${expenseTypeList}" var="ets" varStatus="status">
						<tr>
							<td class="red ">Expense</td>
							<td>${ets.name}</td>
							<td
								<c:choose>
								        <c:when test="${ets.active == 'true' }">
								        	class="green"
								        </c:when>
								        <c:otherwise>
								        	class="red"
								        </c:otherwise>
							        </c:choose>>${ets.active}</td>
							<td><select class="pct90"
								onchange="doTrancTypeCommand(this, ${ets.id}, '${ets.name}')">
									<option value="select">Select</option>
									<option value="edit">Edit</option>
									<c:choose>
										<c:when test="${ets.active == 'true' }">
											<option value="deactivate">deactivate</option>
										</c:when>
										<c:otherwise>
											<option value="activate">activate</option>
										</c:otherwise>
									</c:choose>

							</select></td>
						</tr>

					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td></td>
						<td></td>
						<td class="red">No Family expense types defined</td>
						<td></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${fn:length(incomeTypeList) gt 0}">

					<c:forEach items="${incomeTypeList}" var="its" varStatus="status">
						<tr>
							<td class="green ">Income</td>
							<td>${its.name}</td>
							<td
								<c:choose>
								        <c:when test="${its.active == 'true' }">
								        	class="green"
								        </c:when>
								        <c:otherwise>
								        	class="red"
								        </c:otherwise>
							        </c:choose>>${its.active}</td>
							<td><select class="pct90"
								onchange="doTrancTypeCommand(this, ${its.id}, '${ets.name}')">
									<option value="select">Select</option>
									<option value="edit">Edit</option>
									<c:choose>
										<c:when test="${its.active == 'true' }">
											<option value="deactivate">deactivate</option>
										</c:when>
										<c:otherwise>
											<option value="activate">activate</option>
										</c:otherwise>
									</c:choose>
							</select></td>
						</tr>

					</c:forEach>


				</c:when>
				<c:otherwise>
					<tr>
						<td></td>
						<td></td>
						<td class="green">No Family Income types defined</td>
						<td></td>
					</tr>
				</c:otherwise>
			</c:choose>

		</tbody>
	</table>
</div>


<money:tracTypePopup divId="newTracTypePopup" formId="newTracTypeName"
	divTitle="Add new transactional type" />
<money:tracTypePopup divId="updTracTypePopup" formId="tracTypeNameForm"
	divTitle="update transactional type" />
