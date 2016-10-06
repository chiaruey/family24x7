<%@ tag %>
<%@ attribute name="divId" type="java.lang.String" required="true" %>
<%@ attribute name="formId" type="java.lang.String" required="true" %>
<%@ attribute name="divTitle" type="java.lang.String" required="true" %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div  class="tracPopup" id="${divId}" title="${divTitle}">
	<p class="validateTips">All form fields are required.</p>

	<form id="${formId}">
		<fieldset>
			<div class="popupField">
				<label for="ioe">Income/Expense</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="ioe">
					<option value="I">income</option>
					<option value="E">expense</option>
				</select>												
			</div>			
			<div class="popupField">
				<label for="transType">Name</label>
				<input type="text" name="tracTypeName" value="" class="required tracInput text ui-widget-content ui-corner-all" />								
			</div>
			<input type="hidden" name="transTypeId" value="-1" />	
		</fieldset>
	</form>
</div>
