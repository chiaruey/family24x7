<%@ tag %>
<%@ attribute name="transitionTypes" type="java.util.List" required="true" %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div  class="tracPopup" id="newTracPopup" title="Add new monetary transaction">
	<p class="validateTips">All form fields are required.</p>

	<form id="newTracForm">
		<fieldset>
			<div class="popupField dateField">
				<label for="tracDate">Date</label>
				<input type="text" name="tracDate" class="required date tracInput text ui-widget-content ui-corner-all" />	
			</div>
			<div class="popupField">
				<label for="ioe">Income/Expense</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="ioe">
					<option value="I">income</option>
					<option value="E">expense</option>
				</select>												
			</div>			
			<div class="popupField">
				<label for="transType">Transition Type</label>
				<select class="tracInput ui-widget-content ui-corner-all" name="transType">
					<option id="newTransType">[New]</option>
					<c:forEach items="${transitionTypes}" var="transType">
						<option class="existTransType" value="${transType.ioe}${transType.id}-${transType.name}">${transType.name}</option>												
					</c:forEach>
				</select>							
			</div>	
			<div class="popupField">
				<label for="comments">Comments</label>
				<textarea name="comments" onkeyup="maxcharsForTextArea(this, 30, event);" onkeydown="maxcharsForTextArea(this, 30, event);" class="required tracInput text ui-widget-content ui-corner-all"></textarea>			
			</div>
			<div class="popupField">
				<label for="amount">Amount</label>
				<input type="text" name="amount" value="" class="required tracInput text ui-widget-content ui-corner-all" />		
			</div>			
			<input type="hidden" name="transId" value="-1" />
		</fieldset>
	</form>
</div>
