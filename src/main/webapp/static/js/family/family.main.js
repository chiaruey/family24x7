$(document).ready(function() {
//	var bodyClass = $('body').attr('class');
	if ($('body').hasClass('homeVew')) {
		$('#home-tab').addClass('ui-tabs-selected ui-state-active');
		$('#money-tab').removeClass('ui-tabs-selected ui-state-active');
		$('#calendar-tab').removeClass('ui-tabs-selected ui-state-active');
		$('#admin-tab').removeClass('ui-tabs-selected ui-state-active');
		
	} else if ($('body').hasClass('moneyView')) {
		$('#home-tab').removeClass('ui-tabs-selected ui-state-active');		
		$('#money-tab').addClass('ui-tabs-selected ui-state-active');
		$('#calendar-tab').removeClass('ui-tabs-selected ui-state-active');
		$('#admin-tab').removeClass('ui-tabs-selected ui-state-active');

	} else if ($('body').hasClass('calendarView')) {
		$('#home-tab').removeClass('ui-tabs-selected ui-state-active');		
		$('#money-tab').removeClass('ui-tabs-selected ui-state-active');		
		$('#calendar-tab').addClass('ui-tabs-selected ui-state-active');
		$('#admin-tab').removeClass('ui-tabs-selected ui-state-active');		
		
	} else if ($('body').hasClass('adminView')) {
		$('#home-tab').removeClass('ui-tabs-selected ui-state-active');		
		$('#money-tab').removeClass('ui-tabs-selected ui-state-active');		
		$('#calendar-tab').removeClass('ui-tabs-selected ui-state-active');
		$('#admin-tab').addClass('ui-tabs-selected ui-state-active');		

	} 

});