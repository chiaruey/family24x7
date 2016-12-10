<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="calendar" tagdir="/WEB-INF/tags/calendar"%>

<div class="contentWrapper">

	<div class="calendarHeader">
		<div class="dayWeekMonthDiv row" class="ui-buttonset">

			<span 
				class="daySpan col-sm-4 ui-corner-left ui-state-active">Day</span>
			<span id="${dayCalendar.dateString}" class="weekSpan col-sm-4 ui-state-default">Week</span>
			<span id="${dayCalendar.year}-${dayCalendar.month}"
				class="monthSpan col-sm-4 ui-state-default ui-corner-right">Month</span>

		</div>
		
		<div id="dayScrollHeader" class="calendarScrollHeader ui-corner-all ui-state-default">
			<div id="prev_day_arrow" class="icon-div ui-state-default ui-corner-all floatLeft">
				<span class="ui-icon ui-icon-circle-triangle-w"></span>
			</div>											
			<div class="calendar_date_string_div">
				<span>${dayCalendar.monthAbbr} &nbsp;
			${dayCalendar.dayOfMonth}, &nbsp; ${dayCalendar.year}, &nbsp;
			${dayCalendar.dayName}</span>
			</div>
			<div id="next_day_arrow" class="icon-div ui-state-default ui-corner-all floatRight">
				<span class="ui-icon ui-icon-circle-triangle-e"></span>
			</div>								
		</div>

		<c:if test="${dayCalendar.today == 'true'}">
			<span id="dailyToday" class="bold">TODAY</span>
		</c:if>

				
		<div class="addEventHeader">
			<form id="calendarForm">
				<input type="hidden" name="yesterdayDateString" value="${dayCalendar.yesterdayDateString}">
				<input type="hidden" name="tomorrowDateString" value="${dayCalendar.tomorrowDateString}">	
							
				<input type="hidden" name="dateString" value="${dayCalendar.dateString}"> 
				
				<input
					class="jqButton ui-state-default ui-corner-all ui-widget ui-button "
					type="submit" id="addDailyEventBtn" value="+ Add new event" />
			</form>
		</div>
	</div>


	<table class='calendarDayTable ui-widget ui-widget-content ui-corner-all pct100'>
		<thead>
			<tr>
				<th class="ui-widget-header ui-corner-all ui-state-default pct10">Start Time</th>
				<th class="ui-widget-header ui-corner-all ui-state-default pct10">End Time</th>
				<th class="ui-widget-header ui-corner-all ui-state-default pct20">Title</th>
				<th class="ui-widget-header ui-corner-all ui-state-default pct40">Description</th>
				<th class="ui-widget-header ui-corner-all ui-state-default pct10">all Date</th>
				<th class="ui-widget-header ui-corner-all ui-state-default pct10">option</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(dayCalendar.eventList) gt 0}">
					<c:forEach items="${dayCalendar.eventList}" var="event"  varStatus="status">
						<tr>
							<td>${event.startDateTime}</td>
							<td>${event.endDateTime}</td>
							<td>${event.title}</td>
							<td>${event.description}</td>
							<td>${event.allDay}</td>
							<td><select class=" ui-widget pct90" onchange="doCommand(this, ${event.id})">
								<option class="ui-selectmenu-text" value="select">Select</option>
								<option class="ui-selectmenu-text" value="edit">Edit</option>
								<option class="ui-selectmenu-text" value="delete">Delete</option>
							</select></td>
							
						</tr>
					</c:forEach>										
				</c:when>
				<c:otherwise>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td>No Events</td>
						<td></td>
					</tr>					
				</c:otherwise>
			</c:choose>
		</tbody>

	</table>

</div>


<calendar:addDailyEventPopup dateHeader="${dayCalendar.monthAbbr} ${dayCalendar.dayOfMonth}, ${dayCalendar.year}, ${dayCalendar.dayName}" dateString="${dayCalendar.dateString}"/>
<calendar:updateDailyEventPopup dateHeader="${dayCalendar.monthAbbr} ${dayCalendar.dayOfMonth}, ${dayCalendar.year}, ${dayCalendar.dayName}" dateString="${dayCalendar.dateString}"/>


<script type="text/javaScript"
	src="static/js/family/family.calendar.js"></script>

