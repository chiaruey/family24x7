<%@ tag %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div id ="editFamilyPopup" class="admimFamilyPopup" title="Edit family member">

	<form id="editFamilyForm">
		<fieldset>
			<input type="hidden" name="id" value=""/>
			<div class="popupField">
				<label for="username">Username</label>
				<input type="text" name="username" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>		
			<div class="popupField">
				<label for="email">Email</label>
				<input type="text" name="email" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>
			<div class="popupField">
				<label for="firstName">First Name</label>
				<input type="text" name="firstName" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>	
			<div class="popupField">
				<label for="lastName">Last Name</label>
				<input type="text" name="lastName" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>
			<div class="popupField">
				<label for="role">Role</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="role">
					<c:forEach items="${allRoles}" var="role">
						<option value="${role.name()}">${role.name()}</option>
					</c:forEach>
				</select>						
			</div>	
			<div class="popupField">
				<label for="gender">Gender</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="gender">
					<option value="M">Male</option>
					<option value="F">Female</option>
				</select>												
			</div>
			<div class="popupField">
				<label for="admin">Admin</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="admin">
					<option value="true">True</option>
					<option value="false">False</option>
				</select>												
			</div>					
			<div class="popupField">
				<label>Date Of Birth</label>

					<select name="dobMonth" id="dobMonth" class="dobField">
						<option value="Month">Month:</option>
						<c:forEach items="${monthList}" var="month">
							<option value="${month}">${month + 1}</option>
						</c:forEach>
					</select>						
					<select name="dobDay" id="dobDay" class="dobField">
						<option value="Day">Day:</option>
						<c:forEach items="${dayList}" var="day"> 
							<option value="${day}">${day}</option>
						</c:forEach>								
					</select>
					<select name="dobYear" id="dobYear" class="dobField">
						<option value="Year">Year:</option>
						<c:forEach items="${yearList}" var="year">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>																				

			</div>			
				
		</fieldset>
	</form>
</div>
