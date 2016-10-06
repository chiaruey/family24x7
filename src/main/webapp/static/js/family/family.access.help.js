/*
 * Validate upon registration
 */
$(document).ready(function() {
	
	$('#validateUsername').click(function(e){
		$('#errorText').text('');
		validateUsername();
		return false;
	});
 	
	$('#validateSecureQuestion').click(function(e){
		
		$('#errorText').text('');
		validateSecureQuestion();
		return false;
	});
	
	
	$('#resetPassword').click(function(e){
		
		$('#errorText').text('');
		resetPassword();
		return false;
	});
	
	
	$('[name="password"]').change(function(e){
		validatePassword();
	});
	
	
	$('[name="repeatPassword"]').change(function(e){
		validateRepeatPassword();
	});
	
	
	//////////////////
	// validate step1
	
	var accessHelpStep1Rules = {
		rules : {
			username : {
				required:true
			}
		},
		errorPlacement: function(error, element) {	
			var errElmt = $('<br/><div>');
			error.appendTo(errElmt);
			errElmt.insertAfter(element);							
		}
	};
	
	function validateUsername() {
		if ($('.invalidUserName').length > 0) {
			$('.invalidUserName').remove();
		}

		var username = $('#accessHelpStep1Form').find('input[name="username"]').val();

		if (!$('#accessHelpStep1Form').validate(accessHelpStep1Rules).form()) {
			return false;
		} 
		

		$.get(getAppPath() +  'accessHelp/verifyUsername.json', {'username':username}, function(response) {
			if (response.success == false) {			
				var errElmt = $('<p class="error invalidUserName">This username does not exist in the system.</p>');
				errElmt.insertAfter('#username');
				return false;
			} else {
				document.location.href = getAppPath() + 'accessHelp/step2?username=' + username;
			} 
		});

	}
	
	
	////////////////////
	// validate step2
	
	var accessHelpStep2Rules = {
		rules : {
			secureQuestionAnswer : {
				required:true
			}
		},
		errorPlacement: function(error, element) {	
			var errElmt = $('<br/><div>');
			error.appendTo(errElmt);
			errElmt.insertAfter(element);							
		}
	};
	

	
	function validateSecureQuestion() {
		$('#errorText').text('')

		var secureQuestionAnswer = $('#accessHelpStep2Form').find('input[name="secureQuestionAnswer"]').val();
		var username = $('#accessHelpStep2Form').find('input[name="username"]').val();

		if (!$('#accessHelpStep2Form').validate(accessHelpStep2Rules).form()) {
			return false;
		} 
		

		$.get(getAppPath() +  'accessHelp/verifySecurityQuestion.json', {'username':username,'secureQuestionAnswer':secureQuestionAnswer}, function(response) {
			if (response.success == false) {	
				$()
				var errElmt = $('<p class="error invalidSecureAnswer">The security answer does not match.</p>');
				errElmt.insertAfter('#secureQuestionAnswer');
				return false;
			} else {
				document.location.href = getAppPath() + 'accessHelp/step3?username=' + username;
			} 
		});

	}
	

	////////////////////
	// validate step3
	
	var accessHelpStep3Rules = {
			rules : {		
				password : {
					required: true
				},
				repeatPassword : {
					required: true
				}
			},
			errorPlacement: function(error, element) {
				var errElmt = $('<br/><div>');
				error.appendTo(errElmt);
				errElmt.insertAfter(element);	
			}				
	};
	
	
	function validatePassword() {
		
		if ($('.invalidPassword').length > 0) {
			$('.invalidPassword').remove();
		}
				
		
		var password = $('#accessHelpStep3Form').find('input[name="password"]').val();

		if (password.length > 15 || password.length < 6) {
			var errElmt = $('<br/><p class="error invalidPassword">Password must be between 6-15 characters</p>');
			errElmt.insertAfter($('#accessHelpStep3Form').find('input[name="password"]'));
			return false;
		}
		return true;
	}



	function validateRepeatPassword() {
		
		if ($('.invalidRepeatPassword').length > 0) {
			$('.invalidRepeatPassword').remove();
		}
				
		var password = $('#accessHelpStep3Form').find('input[name="password"]').val();
		var repeatPassword = $('#accessHelpStep3Form').find('input[name="repeatPassword"]').val();
		
		if (password != repeatPassword) {
			var errElmt = $('<br/><p class="error invalidRepeatPassword">Password does not match</p>');
			errElmt.insertAfter($('#accessHelpStep3Form').find('input[name="repeatPassword"]'));
			return false;
		}
		return true;
	}

	function successPwdChangeDialog() {
	    var titleMsg = 'INFO';

	    var outputMsg = 'Your password has been successfully changed.';

	    $("<div class='jqMessage'></div>").html(outputMsg).dialog({
	        title: titleMsg,
	        resizable: false,
	        modal: true,
	        buttons: {
	            "OK": function () {
	            	if (goLoginPage()) {
	            		$(this).dialog("close");
	            	}
	                
	            }
	        }
	    }).prev(".ui-dialog-titlebar").css("color","blue");;
	}
	
	function goLoginPage() {
		document.location.href = getAppPath() + 'login ';
		return true;
	}

	function resetPassword() {
		$('#errorText').text('');
		
		var username = $('#accessHelpStep3Form').find('input[name="username"]').val();
		var password = $('#accessHelpStep3Form').find('input[name="password"]').val();
			
		var validPassword = validatePassword();
		var validRepeatPassword = validateRepeatPassword();
		
		var customvalidation = validPassword && validRepeatPassword;

		if ((!$('#accessHelpStep3Form').validate(accessHelpStep3Rules).form())||!customvalidation) {

			return false;
		} 
		
		$.post(getAppPath() +  'accessHelp/resetPassword.json', {'username':username,'password':password}, function(response) {
			if (response.success == false) {	
				$('#errorText').text('Fail to reset password, please contact help desk');
			} else {
				successPwdChangeDialog();
				
			} 
		});
		
	}




		
});



