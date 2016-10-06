<%@ tag %>
<%@ attribute name="dateHeader" type="java.lang.String" required="true" %>
<%@ attribute name="dateString" type="java.lang.String" required="true" %>
<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div id ="addDailyEventPopup" class="eventPopup"  title="ADD NEW EVENT">

	<span class="green bold font1_2em">${dateHeader}</span>

	<form id="dailyEventForm" class="eventForm">
		<fieldset class="noBorder">
			<div class="popupField textPopupField">
				<label class="popupLabel" for="eventTitle">Event Title</label>
				<input type="text" id="eventTitle" name="eventTitle" value="" class="eventTitle required eventInput text ui-widget-content ui-corner-all" />								
			</div>		
		
			<div class="popupField textPopupField">
				<label class="popupLabel" for="allDayEvent">All Day Event</label>
				<input type="checkbox" name="allDayEvent" value="allDay" class="eventInput ui-widget-content ui-corner-all" />								
			</div>							
		
			<div class="popupField textPopupField">
				<label class="popupLabel" for="eventFromTime">From Time</label>
				<select name="eventFromTime" id="eventFromTime" class="pct40">
					<c:forEach items="${dayCalendar.timeSet}" var="selectedTime">
						<option value="${selectedTime}">${selectedTime}</option>
					</c:forEach>
				</select>							
			</div>
				
			<div class="popupField textPopupField">
				<label class="popupLabel" for="eventToTime">To Time</label>
				<select name="eventToTime" id="eventToTime" class="pct40">
					<c:forEach items="${dayCalendar.timeSet}" var="selectedTime">
						<option value="${selectedTime}">${selectedTime}</option>
					</c:forEach>
				</select>	
			</div>
		
			<div class="popupField textAreaPopupField">
				<label class="popupLabel" for="eventComments">Comments</label>
				<textarea id="eventComments"  name="eventComments" onkeyup="maxcharsForTextArea(this, 100, event);" onkeydown="maxcharsForTextArea(this, 100, event);" class="eventComment required eventInput text ui-widget-content ui-corner-all"></textarea>			
			</div>		
			<input type="hidden" id="eventToDate"  name="eventToDate" value="${dateString}" /> 	
			<input type="hidden" id="eventFromDate" name="eventFromDate" value="${dateString}" /> 				
				
		</fieldset>
	</form>
</div>
