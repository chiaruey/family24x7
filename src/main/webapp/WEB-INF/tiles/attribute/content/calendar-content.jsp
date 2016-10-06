
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="calendar" tagdir="/WEB-INF/tags/calendar"%>

<div class="contentWrapper"> 
	
	<div class="calendarHeader">
		
		<div class="dayWeekMonthDiv" class="ui-buttonset">
			<span  class="daySpan ui-corner-left dayWeekMonthSpan ui-state-default">Day</span>
			<span class="weekSpan ui-state-default dayWeekMonthSpan">Week</span>
			<span class="monthSpan ui-state-active ui-corner-right dayWeekMonthSpan">Month</span>
		</div>
	
		<div id="monthScrollHeader" class="calendarScrollHeader ui-corner-all ui-state-default">
			<form id="calendarForm">
				<div id="prev_month_arrow" class="icon-div ui-state-default ui-corner-all floatLeft">
					<span class="ui-icon ui-icon-circle-triangle-w"></span>
				</div>
				<div class="calendar_date_string_div">
					<span>${monCalendar.monthName} &nbsp; ${monCalendar.year}</span>
				</div>
				<div id="next_month_arrow"
					class="icon-div ui-state-default ui-corner-all floatRight">
					<span class="ui-icon ui-icon-circle-triangle-e"></span>
				</div>
				
				<input type="hidden" name="dateString"
					value="${monCalendar.dateString}"> 
								
					
				<input type="hidden" name="prevMonthString"
					value="${monCalendar.prevMonthString}"> 
					
				<input type="hidden" name="nextMonthString" 
					value="${monCalendar.nextMonthString}">
					
				<input type="hidden" name="lastDayString"
					value="${monCalendar.lastDayString}"> 
					
				<input type="hidden" name="firstDayString" 
					value="${monCalendar.firstDayString}">
			</form>
									
		</div>

		<div class="addEventHeader">

			<input class="jqButton" type="submit" id="addMonthlyEventBtn"
				value="+ Add new event" />

		</div>


	</div>
	<table id="calendarTable">	
		<tr>
			<td>
				<table class="pct100">
					<thead>
						<tr>
							<td class="dayHeader">Sun</td>
							<td class="dayHeader">Mon</td>
							<td class="dayHeader">Tue</td>
							<td class="dayHeader">Wed</td>
							<td class="dayHeader">Thu</td>
							<td class="dayHeader">Fri</td>
							<td class="dayHeader">Sat</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${monCalendar.calendarWeekList}" var="daysInWeek" varStatus="status">
						<tr class="monthlyCalendarRow">
							<c:forEach items="${daysInWeek}" var="day" varStatus="status">
								<td id="${day.value.dateString}" class="monthlyCalendarCell noBackground ui-state-default">
									${day.value.month + 1} /${day.value.dayOfMonth}
									<c:if test="${day.value.today == 'true'}">
										<span class="ui-state-default bold margin5px padding5px">TODAY</span>
									</c:if>
									
									<br />	
									<c:choose>
										<c:when test="${fn:length(day.value.eventList) gt 1}">
											<br/>
											<span class='bold font1_2em green'>${fn:length(day.value.eventList)} &nbsp; Events </span>
										</c:when>
										<c:when test="${fn:length(day.value.eventList) gt 0}">
											<br/>
											<span class='bold font1_2em green'>${fn:length(day.value.eventList)} &nbsp; Event </span>
										</c:when>						
									</c:choose>
																		 
								</td>
							</c:forEach>
						</tr>
						</c:forEach>
					</tbody>
					
				</table>
			</td>
		</tr>
	
	</table>

			
	</div> 
	
	<calendar:addMonthlyEventPopup />
	
	
	<script type="text/javaScript" src="static/js/family/family.calendar.js"></script>
	
	