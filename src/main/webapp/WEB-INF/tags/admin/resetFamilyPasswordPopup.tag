<%@ tag %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div id ="resetFamilyPasswordPopup" class="admimFamilyPopup" title="Reset family member password">

	<form id="resetFamilyPasswordForm">
		<fieldset>
			<input type="hidden" name="id" value=""/>
			<div class="popupField">
				<label for="username">Username</label>
				<input type="text" name="username" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>		
			<div class="popupField">
				<label for="password">Password</label>
				<input type="password" name="password" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>	
			<div class="popupField">
				<label for="repeatPassword">Repeat Password</label>
				<input type="password" name="repeatPassword" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>				
				
		</fieldset>
	</form>
</div>
