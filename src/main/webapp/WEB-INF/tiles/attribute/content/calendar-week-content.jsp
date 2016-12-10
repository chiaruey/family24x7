<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="calendar" tagdir="/WEB-INF/tags/calendar"%>

<div class="contentWrapper">
	<form id="calendarForm">
		<div class="calendarHeader">
			
			<div class="dayWeekMonthDiv row" class="ui-buttonset">
				<span class="daySpan col-sm-4 ui-corner-left ui-state-default">Day</span>
				<span class="weekSpan col-sm-4 ui-state-active">Week</span>
				<span id="${weekCalendar.year}-${weekCalendar.month}"
					class="monthSpan col-sm-4 ui-state-default ui-corner-right">Month</span>
			</div>
			
			<div id="weekScrollHeader" class="calendarScrollHeader ui-corner-all ui-state-default">
				<div id="prev_week_arrow" class="icon-div ui-state-default ui-corner-all floatLeft">
					<span class="ui-icon ui-icon-circle-triangle-w"></span>
				</div>											
				<div class="calendar_date_string_div">
					<span>${weekCalendar.weekDaysString}</span>
				</div>
				<div id="next_week_arrow" class="icon-div ui-state-default ui-corner-all floatRight">
					<span class="ui-icon ui-icon-circle-triangle-e"></span>
				</div>					
			</div>
			
			<div class="addEventHeader">				
				<input class="jqButton" type="submit" id="addWeeklyEventBtn"
						value="+ Add new event" />
			</div>
							
			<input type="hidden" name="prevWeekString" value="${weekCalendar.prevWeekString}">
			<input type="hidden" name="nextWeekString" value="${weekCalendar.nextWeekString}">				
			<input type="hidden" name="dateString" value="${weekCalendar.dateString}"> 
			<input type="hidden" name="lastDayString" value="${weekCalendar.lastDayString}"> 
			<input type="hidden" name="firstDayString" value="${weekCalendar.firstDayString}">
		</div>
	
	
		<table class='calendarWeekTable'>
			<tbody>
				<c:forEach items="${weekCalendar.calendarDayMap}" var="day"
					varStatus="status">
					<tr>
						<td  id="${day.value.dateString}" class="weekCell ">
							<div class="minHeight40">
								<span class="bold green font1_2em">${day.key}</span> 
								<c:if test="${day.value.today == 'true'}">
									<span class="ui-state-default bold margin5px padding5px">TODAY</span>
								</c:if>													
							</div>
							<table>					
								<c:choose>
									<c:when test="${fn:length(day.value.eventList) gt 0}">
										<c:forEach items="${day.value.eventList}" var="event"  varStatus="status">
											<tr class="padding10px">
												<c:choose>
													<c:when test="${event.allDay == true }">
														<td class="pct20">${event.startDateTime}</td>
														<td class="pct5">&nbsp;</td>
														<td class="pct20"> ${event.endDateTime}</td>
													</c:when>
													<c:otherwise>
														<td class="pct20">${event.startDateTime}</td>
														<td class="pct5"> &nbsp; to &nbsp;</td> 
														<td class="pct20">${event.endDateTime}</td>
													</c:otherwise>
												</c:choose>
												<td class="pct5">:</td>

												<td class="pct40">${event.title}</td>
											</tr>
										</c:forEach>										
									</c:when>
									<c:otherwise>
										<tr class="padding10px">
											<td class="pct10"></td>
											<td class="pct20"></td>
											<td class="pct40">No Events</td>
											<td class="pct10"></td>
											<td class="pct20"></td>
										</tr>					
									</c:otherwise>
								</c:choose>

							</table> 
						</td>
					</tr>
				</c:forEach>
			</tbody>
	
		</table>
	</form>
</div>


<calendar:addWeeklyEventPopup />
	



<script type="text/javaScript"
	src="static/js/family/family.calendar.js"></script>

