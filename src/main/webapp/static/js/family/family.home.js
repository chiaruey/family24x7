$(document).ready(function() {
	
	$('.familyMemberTable').hide();
	
	$('.familyMemberHeading').click(function(e){
		$('.familyMemberHeading').not(this).removeClass('ui-state-active'); 
		$('.familyMemberHeading').not(this).children('.jqIcon').removeClass('ui-icon-triangle-1-s').addClass('ui-icon-triangle-1-e');
		$('.familyMemberTable').not($(this).next()).hide();
		$(this).next('.familyMemberTable').toggle('slow');
		return false;
	});
	
	
	// ==========================================
	// For adding a wall message
	// ==========================================

	// Show up add event dialog box
	$('#addMessageBtn').click(function(e){
		addMyMessage();
		return false;		
	});
	
	$('#openAddMessagePopupBtn').click(function(e){
		$("#addMyMessagePopup").modal("show");
		
		return false;		
	});	
	
	$('.refreshPageBtn').click(function(e) {
		refreshPage();
		return false;
	});


});

//Set max chars for textArea
function checkMessageChars(field, max, event)
{
	if (field.value.length > max) {
		field.value = field.value.substring(0, max);
 		if (event.type != 'keydown') {
			$("#tooManyCharsPopup").modal("show");
		} 
		
		return false;
	} 
	return true;
}


function addMyMessage() {
	
	if (!validateMyMessage($('#myMessageForm'))) {
		return false;
	}

	var myMessageText = $('#myMessageForm').find('textarea[name="myMessageText"]').val();
	
	
	$.post(getAppPath() + 'addMyMessage.json', {'wallMessageText':myMessageText} , function(data){

		if (data) {
			var href = location.href;
			var url = href.replace(/#/g,'');
			$("#messageCreatedPopup").modal("show");
		} else {
			$("systemErrorPopup").modal("show");
			errorMsg('Fail to create your message. Please try again later');
		}


	}, 'json');
	
	$("#addMyMessagePopup").modal("hide");

	return true;
}

function refreshPage() {
	var href = location.href;
	var url = href.replace(/#/g,'');
	window.location = url;
}

function validateMyMessage(form) {
	// =======================================
	// money transition Type dialog validation
	// =======================================
	
	var wallMessageRules = {
		rules : {
			wallMessageText : {
				required : true
			}
		},	
		errorPlacement: function(error, element) {
			var errElmt = $('<br/><div>');
			error.appendTo(errElmt);
			errElmt.insertAfter(element);				
		}
	};



	if (!$(form).validate(wallMessageRules).form()) {
		return false;
	}	

	return true;

}

function deleteWallMessage(id) {
	$.post(getAppPath() + 'deleteWallMessage.json', {'id':id}, function(data){
		if (data.success) {
			$('#messageDeletedPopup').modal('show');
		} else if (data.errorMsg) {
			errorMsg('fail to delete wall message due to ' + data.errorMsg);
		} else {
			errorMsg('fail to delete wall message. Please try again later');
		}			
	}, 'json');
	
}



