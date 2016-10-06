<%@ tag %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div id ="addMonthlyEventPopup" class="eventPopup" title="Add new Event">
	<p class="validateTips">All form fields are required.</p>

	<form id="monthlyEventForm" class="eventForm">
		<fieldset>
			<div class="popupField textPopupField">
				<label class="popupLabel" for="eventTitle">Title</label>
				<input type="text" id="eventTitle" name="eventTitle" value="" class="eventTitle required eventInput text ui-widget-content ui-corner-all" />								
			</div>	
			<div class="popupField textPopupField">
				<label class="popupLabel" for="allDayEvent">All Day Event</label>
				<input type="checkbox" name="allDayEvent" value="allDay" class="eventInput ui-widget-content ui-corner-all" />								
			</div>															
			<div class="popupField eventDateField textPopupField">
				<label class="popupLabel" for="eventFromDate">From </label>
				<input type="text" id="eventFromDate" name="eventFromDate" class="required date eventInput text ui-widget-content ui-corner-all" />	
				<select name="eventFromTime" id="eventFromTime" class="pct40">
					<c:forEach items="${monCalendar.timeSet}" var="selectedTime">
						<option value="${selectedTime}">${selectedTime}</option>
					</c:forEach>
				</select>							
				
			</div>
			
			<div class="popupField eventDateField textPopupField">
				<label class="popupLabel" for="eventToDate">To</label>
				<input type="text" id="eventToDate" name="eventToDate" class="required date eventInput text ui-widget-content ui-corner-all" />
				<select name="eventToTime" id="eventToTime" class="pct40">
					<c:forEach items="${monCalendar.timeSet}" var="selectedTime">
						<option value="${selectedTime}">${selectedTime}</option>
					</c:forEach>
				</select>	
			</div>
			<div class="popupField textAreaPopupField">
				<label class="popupLabel" for="eventComments">Comments</label>
				<textarea id="eventComments" name="eventComments" onkeyup="maxcharsForTextArea(this, 100, event);" onkeydown="maxcharsForTextArea(this, 100, event);" class="eventComment required eventInput text ui-widget-content ui-corner-all"></textarea>			
			</div>
				
		</fieldset>
	</form>
</div>
