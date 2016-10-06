	var eventRules = {
		rules : {
			eventTitle : {
				required: true
			},
			eventFromDate : {
				required: true,
				date : true
			},
			eventToDate : {
				required: true,
				date : true
			},
			eventFromTime : {
				required: true,
			},
			eventToTime : {
				required: true,
			},
			eventComments: {
				required: true
			}
		},
		messages: {
			eventTitle : {
				required: " (required)"
			},
			eventFromDate : {
				required: " (from date is required)",
				date : " (wrong date format)"
			}, 
			eventToDate : {
				required: " (to date is required)",
				date : " (wrong date format)"
			},
			eventFromTime : {
				required: " (from time is required)",
			}, 
			eventToTime : {
				required: " (to time required)",
			},			
			eventComments : {
				required: " (required)"
			}
		},			
		errorPlacement: function(error, element) {
			$( element )
				.closest( "form" )
					.find( "label[for='" + element.attr( "id" ) + "']" )
						.append("<br/>").append( error );
		
		}
	};


$(document).ready(function() {
	

	// ==========================================
	// For adding a new monthly event
	// ==========================================

	// Open up dialog box for adding new monetary transaction
	$('#addMonthlyEventPopup').dialog({ 
		autoOpen: false,
		height: 450,
		width: 500,
		modal: true,
	/*	position: 'center', */
		buttons: {
					"Create a new event": function() {
						
					if (createOrUpdateEvent($('#monthlyEventForm'), false)) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	
	
	// Show up add event dialog box
	$('#addMonthlyEventBtn').click(function(e){
		resetEventDialog();
		$('#addMonthlyEventPopup').dialog('open');
		return false;		
	});
	
	
	// ==========================================
	// For adding a new weekly event
	// ==========================================

	// Open up dialog box for adding new monetary transaction
	$('#addWeeklyEventPopup').dialog({ 
		autoOpen: false,
		height: 450,
		width: 500,
		modal: true,
	/*	position: 'center', */
		buttons: {
					"Create a new event": function() {
						
					if (createOrUpdateEvent($('#weeklyEventForm'), false)) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	
	// Show up add event dialog box
	$('#addWeeklyEventBtn').click(function(e){
		resetEventDialog();
		$('#addWeeklyEventPopup').dialog('open');
		return false;		
	});
	
	// ==========================================
	// For updating a new daily event
	// ==========================================

	// Open up dialog box for updating an event
	$('#updateDailyEventPopup').dialog({ 
		autoOpen: false,
		height: 450,
		width: 500,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Update": function() {
					if (createOrUpdateEvent($('#updateEventForm'), true)) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	
	
	// Show up add event dialog box
	$('#addDailyEventBtn').click(function(e){
		resetEventDialog();
		$('#addDailyEventPopup').dialog('open');
		return false;		
	});
	
	// ==========================================
	// For adding a new daily event
	// ==========================================

	// Open up dialog box
	$('#addDailyEventPopup').dialog({ 
		autoOpen: false,
		height: 450,
		width: 500,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Create a new event": function() {
					if (createOrUpdateEvent($('#dailyEventForm'), false)) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	
	
	// Show up add event dialog box
	$('#addDailyEventBtn').click(function(e){
		resetEventDialog();
		$('#addDailyEventPopup').dialog('open');
		return false;		
	});
	
	
	// ==========================================================
	// Scrolling between the next Month and the previous month
	// ==========================================================
	$('#prev_month_arrow').click(function(e){	
		var prevMonthString = $("#calendarForm").find('input[name="prevMonthString"]').val();	
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendar')) + 'calendar?dateString=' + prevMonthString;
		window.location=url;	
	});
	

	$('#next_month_arrow').click(function(e){		
		var nextMonthString = $("#calendarForm").find('input[name="nextMonthString"]').val();	
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendar')) + 'calendar?dateString=' + nextMonthString;
		window.location=url;	

	});
	
	// ==========================================================
	// Scrolling between the next Date and the previous Date
	// ==========================================================
	$('#prev_day_arrow').click(function(e){
		var yesterdayDateString = $("#calendarForm").find('input[name="yesterdayDateString"]').val();	
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendarDay')) + 'calendarDay?dateString=' + yesterdayDateString;
		window.location=url;	
	});
	

	$('#next_day_arrow').click(function(e){		
		var tomorrowDateString = $("#calendarForm").find('input[name="tomorrowDateString"]').val();
		
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendarDay')) + 'calendarDay?dateString=' + tomorrowDateString;
		window.location=url;	
	});

	// ==========================================================
	// Scrolling between the next Week and the previous Week
	// ==========================================================
	$('#prev_week_arrow').click(function(e){
		var prevWeekString = $("#calendarForm").find('input[name="prevWeekString"]').val();	
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendarWeek')) + 'calendarWeek?dateString=' + prevWeekString;
		window.location=url;	
	});
	

	$('#next_week_arrow').click(function(e){		
		var nextWeekString = $("#calendarForm").find('input[name="nextWeekString"]').val();
		
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf('calendarWeek')) + 'calendarWeek?dateString=' + nextWeekString;
		window.location=url;	
	});
	
	$( ".icon-div").hover(
			function() {
				$( this ).addClass( "ui-state-hover" );
			},
			function() {
				$( this ).removeClass( "ui-state-hover" );
			}
		);
	
	/*
	 * monthlyCalendarCell 
	 */
	$('.monthlyCalendarCell').mouseover(function(e){
		$(this).addClass('ui-state-hover').addClass('border4px').removeClass('noBackground').removeClass('ui-state-default');				
	});
	$('.monthlyCalendarCell').mouseleave(function(e){
		$(this).removeClass('ui-state-hover').removeClass('border4px').addClass('noBackground').addClass('ui-state-default');
	});
	
	/*
	 * weekCell
	 */
	$('.weekCell').mouseover(function(e){
		$(this).addClass('border4px');								
	});
	$('.weekCell').mouseleave(function(e){
		$(this).removeClass('border4px');
	});


	/*
	 * dayWeekMonthSpan
	 */
	$('.dayWeekMonthSpan').mouseover(function(e){
		$(this).addClass('ui-state-hover');				
	});
	$('.dayWeekMonthSpan').mouseleave(function(e){
		$(this).removeClass('ui-state-hover');
	});
 

	$(".daySpan").click(function(e) {
		var pathname = location.pathname;
		var href = location.href;
			
		var dateString = $("#calendarForm").find('input[name="dateString"]').val();
		
		// get application href
		var pathname = location.pathname;
		var appname = pathname.slice(0, pathname.lastIndexOf('/'));
		var apphref = location.origin + appname;
		
		var url = apphref + '/calendarDay?dateString=' + dateString;
		
		window.location=url;

		
	});
	
	$(".monthSpan").click(function(e) {
		if ($(this).hasClass("ui-state-default")) {
			$(".daySpan").removeClass("ui-state-active").addClass("ui-state-default");
			$(".weekSpan").removeClass("ui-state-active").addClass("ui-state-default");
			$(this).addClass("ui-state-active").removeClass("ui-state-default");
			
			var dateString = $("#calendarForm").find('input[name="dateString"]').val();
			
			// get application href
			var pathname = location.pathname;
			var appname = pathname.slice(0, pathname.lastIndexOf('/'));
			var apphref = location.origin + appname;
			
			var url = apphref + '/calendar?dateString=' + dateString;
					
			window.location=url;
		}
		
	});
	
	$(".weekSpan").click(function(e) {
		if ($(this).hasClass("ui-state-default")) {
			$(".daySpan").removeClass("ui-state-active").addClass("ui-state-default");
			$(".monthSpan").removeClass("ui-state-active").addClass("ui-state-default");
			$(this).addClass("ui-state-active").removeClass("ui-state-default");
			
			var pathname = location.pathname;
			var href = location.href;
//			var dateString = e.target.id;		
			
			var dateString = $("#calendarForm").find('input[name="dateString"]').val();
			
			// get application href
			var pathname = location.pathname;
			var appname = pathname.slice(0, pathname.lastIndexOf('/'));
			var apphref = location.origin + appname;
			
			var url = apphref + '/calendarWeek?dateString=' + dateString;
			
			window.location=url;

		}
		
	});
	
	
	/*
	 * monthlyCalendarCell SelectDate
	 */
	$(".monthlyCalendarCell").click(function(e) {
		var dateString = $("#calendarForm").find('input[name="dateString"]').val();
		
		// get application href
		var pathname = location.pathname;
		var appname = pathname.slice(0, pathname.lastIndexOf('/'));
		var apphref = location.origin + appname;

		if ($(this).hasClass('selectedCell')) {
			var url = apphref + '/calendarDay?dateString=' + e.target.id;
			window.location=url;				
		} else {
			
			$(".monthlyCalendarCell").each(function(e) {
				$(this).removeClass('selectedCell');
			}); 
			
			$(this).addClass('selectedCell');
			$("#calendarForm").find('input[name="dateString"]').val($(this).attr('id'));

		}
	}); 
	
	
	$(".monthlyCalendarCell").each(function(e) {
		var dateString = $("#calendarForm").find('input[name="dateString"]').val();
		if ($(this).attr('id') == dateString) {
			$(this).addClass('selectedCell');	
		} 
			
	}); 

	
	/*
	 * monthlyCalendarCell SelectDate
	 */	
	$(".weekCell").each(function(e) {
		var dateString = $("#calendarForm").find('input[name="dateString"]').val();
		if ($(this).attr('id') == dateString) {
			$(this).addClass('selectedCell');	
		}
			
	}); 
	
	/*
	 * monthlyCalendarCell SelectDate
	 */
	$(".weekCell").click(function(e) {
		var dateString = $("#calendarForm").find('input[name="dateString"]').val();
		
		// get application href
		var pathname = location.pathname;
		var appname = pathname.slice(0, pathname.lastIndexOf('/'));
		var apphref = location.origin + appname;
		
		if ($(this).hasClass('selectedCell')) {
			var url = apphref + '/calendarDay?dateString=' + dateString;

			window.location=url;				
		} else {

			$(".weekCell").each(function(e) {
				$(this).removeClass('selectedCell');
			}); 
			
			$(this).addClass('selectedCell');
			$("#calendarForm").find('input[name="dateString"]').val($(this).attr('id'));

		}
	}); 

/*
	var dateString = $("#calendarForm").find('input[name="dateString"]').val();
	var minDate = new Date(currentTime.getYear(), currentTime.getMonth() -1, +1); //one day next before month
	var maxDate =  new Date(currentTime.getFullYear(), currentTime.getMonth() +1, -1); // one day before next month
*/
//	var maxDate = new Date(2015, 3, 24);
	// Date picker
	$('input[type="text"][name="eventFromDate"]').datepicker({
		showOn: "button",
		buttonImage:"static/img/calendar.gif",
		buttonImageOnly: true,
		dateFormat: "yy-mm-dd",
		defaultDate:  $("#calendarForm").find('input[name="dateString"]').val(),
		minDate:  $("#calendarForm").find('input[name="firstDayString"]').val(),
		maxDate:  $("#calendarForm").find('input[name="lastDayString"]').val(),
		onClose: function() {$(this).valid();}
	});
		
	

	// Date picker
	$('input[type="text"][name="eventToDate"]').datepicker({
		showOn: "button",
		buttonImage:"static/img/calendar.gif",
		buttonImageOnly: true,
		stepMonths: 0,
		dateFormat: "yy-mm-dd",
		defaultDate:  $("#calendarForm").find('input[name="dateString"]').val(),
		minDate:  $("#calendarForm").find('input[name="firstDayString"]').val(),
		maxDate:  $("#calendarForm").find('input[name="lastDayString"]').val(),
		onClose: function() {$(this).valid();}
	});

/*	
	$('input[type="text"][name="eventFromTime"]').timepicker();
	
	$('input[type="text"][name="eventToTime"]').timepicker();
	*/
	$('input[type="checkbox"][name="allDayEvent"]').change(function() {
        if($(this).is(":checked")) {
            $('[name="eventFromTime"]').addClass("disabled");
            $('[name="eventFromTime"]').attr("disabled", true);
            $('[name="eventFromTime"]').val('');
            $('[name="eventToTime"]').addClass("disabled");
            $('[name="eventToTime"]').attr("disabled", true);
            $('[name="eventToTime"]').val('');
        } else {
            $('[name="eventFromTime"]').removeClass("disabled");
            $('[name="eventFromTime"]').attr("disabled", false);
            $('[name="eventToTime"]').removeClass("disabled");
            $('[name="eventToTime"]').attr("disabled", false);
        	
        }
     
    });

});

function resetEventDialog()
{
	$(".eventForm").find('input[type="text"]').val("");
	$(".eventForm").find('textarea').val("");
	$(".eventForm").validate(eventRules).resetForm();
}

function createOrUpdateEvent(eventForm, isUpdate)
{
 	if (!validateEvent(eventForm)) {
		return false;
	}
	

	var comments = $(eventForm).find('textarea[name="eventComments"]').val();
	var eventTitle = $(eventForm).find('input[name="eventTitle"]').val();
	var eventToTime = $(eventForm).find('select[name="eventToTime"]').val();
	var eventFromTime = $(eventForm).find('select[name="eventFromTime"]').val();
	var eventFromDate = $(eventForm).find('input[name="eventFromDate"]').val();
	var eventToDate = $(eventForm).find('input[name="eventToDate"]').val();
	var allDayEvent = $(eventForm).find('input[name="allDayEvent"]').prop('checked');

	var fromTimeStamp = eventFromDate;;
	var toTimeStamp  = eventToDate;

	if (allDayEvent == false) {
			
		fromTimeStamp = fromTimeStamp + ' ' + eventFromTime;
		toTimeStamp = toTimeStamp + ' ' + eventToTime;
	}

	var fromDateTime = Date.parse(fromTimeStamp);
	var toDateTime = Date.parse(toTimeStamp)
	
	if (fromDateTime > toDateTime) {
		errorMsg("The event's starting time can not be later than its ending time");
		return false;
	} 
	
	// format to yyyy/MM/dd HH:mm:ss for sending to controller
	var startTimeStr = fromDateTime.toString('yyyy/MM/dd HH:mm');
	var endTimeStr = toDateTime.toString('yyyy/MM/dd HH:mm');

//	infoMsg ('startTimeStr = ' + startTimeStr + ', endTimeStr = ' + endTimeStr);
	if (isUpdate == true) {
		var eventIdStr = $(eventForm).find('input[name="eventId"]').val();
		$.post(getAppPath() + 'updateEvent', {'eventId':eventIdStr, 'startTime':startTimeStr, 'endTime':endTimeStr, 'title':eventTitle, 'description':comments, 'allDay':allDayEvent} , function(data){
			if (data.success) {
				var href = location.href;
				var url = href.replace(/#/g,'');
				infoMsg('This event has been successfully updated!', 'EVENT UPDATED', function(){ window.location = url });
			} else if (data.errorMsg) {
				errorMsg('fail to update event due to ' + data.errorMsg);
			} else {
				errorMsg('fail to update event. Please try again later');
			}

		}, 'json');		
		
		
	} else {
		// Creating Monthly, Weekly, or Daily Event
		$.post(getAppPath() + 'addNewEvent', {'startTime':startTimeStr, 'endTime':endTimeStr, 'title':eventTitle, 'description':comments, 'allDay':allDayEvent} , function(data){
			if (data.success) {
				var href = location.href;
				var url = href.replace(/#/g,'');
				infoMsg('This event has been successfully created!', 'EVENT CREATED', function(){ window.location = url });
			} else if (data.errorMsg) {
				errorMsg('fail to create event due to ' + data.errorMsg);
			} else {
				errorMsg('fail to create event. Please try again later');
			}

		}, 'json');		
	}
	
	return false;
}

function validateEvent (eventForm) {
	// ==================================
	// evemt dialog validation
	// ==================================
	
	var eventValidator = $(eventForm).validate(eventRules);

	if (!eventValidator.form()) {
		var errors = eventValidator.numberOfInvalids();
		
		alert ('failing on the validateEvent!');
		alert ('errors -=' + errors);
		eventValidator.defaultShowErrors();
		return false;
	}	
	
	return true;

}

function doCommand(select, id) {
	var selectedValue = select[select.selectedIndex].value;
	
	if (selectedValue == 'edit') {
			$.get(getAppPath() + 'getEvent', {'id':id}, function(data){	
				$('#updateDailyEventPopup').dialog('open');			
				$('#updateEventForm').find('input[name="eventTitle"]').val(data.title);
				$('#updateEventForm').find('textarea[name="eventComments"]').val(data.description);
				$('#updateEventForm').find('input[name="allDayEvent"]').prop("checked", data.allDay == true);
				$('#updateEventForm').find('select[name="eventFromTime"]').val(data.startTime);
				$('#updateEventForm').find('select[name="eventToTime"]').val(data.endTime);
				$('#updateEventForm').find('input[name="eventId"]').val(data.id);
			});

	} else if (selectedValue == 'delete') {
		$.post(getAppPath() + 'deleteEvent.json', {'id':id}, function(data){
			if (data.success) {
				var href = location.href;
				var url = href.replace(/#/g,'');
				infoMsg('This Event has been successfully deleted!', 'SUCCESS', function(){ window.location = url });
			} else if (data.errorMsg) {
				errorMsg('fail to delete Moneytary Transaction due to ' + data.errorMsg);
			} else {
				errorMsg('fail to delete Moneytary Transaction. Please try again later');
			}			
		});
	}
	
	$(select).val('select');
	
	return false;
}

