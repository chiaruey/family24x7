/*
 * Validate upon registration
 */
$(document).ready(function() {
	resetRegForm();
 	
	$('#addNewHouseHeadBtn').click(function(e){
		addNewHouseHead();
		return false;
	});
	$('#addFamilyBtn').click(function(e) {
		addFamilyMember();
		return false;
	});
	$('#finishRegBtn').click(function(e) {
		var appPath = getAppPath();
		document.location.href = appPath + 'admin';	
		return false;
	});

	$('#dobMonth').change(function(e){
		var month = $(this).val();
		if (month != 'Month') {
			refreshDateList();
		} else {
			$('#dobDay').children().remove();
			$('#dobDay').append($('<option></option>').attr('value', 'Day').html('Day:'));
			for (i=1; i<=31; i++) {
				$('#dobDay').append($('<option></option>').attr('value', i).html(i));
			}
		}
	});

	$('#dobYear').change(function(e){
		var year = $(this).val();
		if (year != 'Year') {
			refreshDateList();
		}
	});

	$('#password').change(function(e){
		validatePassword();
	});
	
	$('#role').change(function(e){
		var role = $(this).val();
		if (role == 'Dad') {
			$('#gender').val('M');
		} else {
			$('#gender').val('F');
		}
		return false;
	});
	
	$('#repeatPassword').change(function(e){
		validateRepeatPassword();
	});
	
	$('#familyName').change(function(e){
		validateFamilyName();
	});
	
	$('#username').change(function(e){
		validateUsername($(this).closest('form'));
	});
	
	function resetRegForm() {
		$('input[type=text]').val('');
		$('input[type=password]').val('');
		$('.secureQuestion').val('0');
		$('#regErrorSpan').text('');
	}
	function refreshDateList() {
		var appPath = getAppPath();
		
		var year = $('#dobYear').val();
		var month = $('#dobMonth').val();
		var day = $("#dobDay").val();

		if (month != 'Month') {
			$('#dobDay').children().remove();
			$('#dobDay').append($('<option></option>').attr('value', 'Day').html('Day:'));

			if (year != 'Year') {
				$.get(getAppPath() +  'registration/getDateList.json', {'year':year, 'month':month}, function(dayList) {
					if (dayList) {
						
						for (i = 1; i <= dayList.length; i++) {
							$('#dobDay').append($('<option></option>').attr('value', i).html(i));
						}
						
						$("#dobDay").val(day);
					}
				});
				
			} else {
				$.get(getAppPath() + 'registration/getDateList.json', {'month':month}, function(dayList) {
					if (dayList) {
						for (i = 1; i <= dayList.length; i++) {
							$('#dobDay').append($('<option></option>').attr('value', i).html(i));
						}
						$("#dobDay").val(day);
					}
				});				
			}
		}
		
	}

	// ========
	// Step 1
	// ========
	// validate step1
	var regStep1Rules = {
			rules : {
				username : {
					required:true
				},
				familyName : {
					required:true
				},
				email : {
					email : true, 
					required:true
				},
				firstName : {
					required : true
				},
				lastName : {
					required : true
				},
				password : {
					required: true
				},
				repeatPassword : {
					required: true
				},
				dobMonth : {
					number : true
				}, 
				dobDay : {
			        number : true
				},
				dobYear : { 
					number : true
				},
				street : {
					required : true
				},
				city : {
					required : true
				},
				state : {
					required : true
				},
				country : {
					required : true
				},
				zip : {
					required : true
				},
				secureQuestion : {
					required : true,
					range: [1, 99]
				}, 
				secureQuestionAnswer : { 
					required : true
				}
			},
			groups : {
				dob : "dobMonth dobDay dobYear"
			},
			messages : {
				dobMonth : "Please enter a valid date",
				dobDay : "Please enter a valid date",
				dobYear : "Please enter a valid date",
				secureQuestion: "Please select a security question"
			},
			errorPlacement: function(error, element) {
				if (element.attr("name") == "dobMonth" || element.attr("name") == "dobDay"  || element.attr("name") == "dobYear" ) {
					var errElmt = $('<br/><div>');
					error.appendTo(errElmt);
					errElmt.insertAfter("#dobYear");	
				} else {
					var errElmt = $('<br/><div>');
					error.appendTo(errElmt);
					errElmt.insertAfter(element);							
				}

			}				
	};
	
	function validatePassword(form) {
		
		if ($('.invalidPassword').length > 0) {
			$('.invalidPassword').remove();
		}
				
		var password = $(form).find('input[name="password"]').val();

		if (password.length > 15 || password.length < 6) {
			var errElmt = $('<p class="error invalidPassword">Password must be between 6-15 characters</p>');
			errElmt.insertAfter('#password');
			return false;
		}
		return true;
	}


	function validateRepeatPassword(form) {
		
		if ($('.invalidRepeatPassword').length > 0) {
			$('.invalidRepeatPassword').remove();
		}
				
		var password = $(form).find('input[name="password"]').val();
		var repeatPassword = $(form).find('input[name="repeatPassword"]').val();

		if (password != repeatPassword) {
			var errElmt = $('<p class="error invalidRepeatPassword">Password does not match</p>');
			errElmt.insertAfter('#repeatPassword');
			return false;
		}
		return true;
	}


	function validateFamilyName() {
		if ($('.invalidFamilyName').length > 0) {
			$('.invalidFamilyName').remove();
		}
		
		var familyName = $('#regStep1Form').find('input[name="familyName"]').val();
		if (validateFamilyNameString(familyName) == false) {
			var errElmt = $('<p class="error invalidFamilyName">familyname allows only lowercase, period(.), underscore(_) with 3-30 characters.</p>');
			errElmt.insertAfter('#familyName');
			return false;
		} 

		// Validate against existing family name
		$.get(getAppPath() +  'registration/isExistingFamilyName.json', {'familyName':familyName}, function(response) {
			if (response.result == true) {				
				var errElmt = $('<p class="error invalidFamilyName">Someone already has that family name. Please try another.</p>');
				errElmt.insertAfter('#familyName');
				return false;
			} 
		});
		return true;
	}

	function validateUsername(form) {
		if ($('.invalidUserName').length > 0) {
			$('.invalidUserName').remove();
		}

		var username = $(form).find('input[name="username"]').val();
		if (validateUserNameString(username) == false) {
			var errElmt = $('<p class="error invalidUserName">username allows only lowercase, period(.), underscore(_) with 3-30 characters.</p>');
			errElmt.insertAfter('#username');
			return false;
		} 
		
		$.get(getAppPath() + 'registration/isExistingUsername.json', {'username':username}, function(response) {
			if (response.result == true) {				
				var errElmt = $('<p class="error invalidUserName">Someone already has that username. Please try another.</p>');
				errElmt.insertAfter('#username');
				return false;
			} 
		});
		return true;
	}


	function addNewHouseHead() {
		var validPassword = validatePassword($('#regStep1Form'));
		var validRepeatPassword = validateRepeatPassword($('#regStep1Form'));
		var validUsername = validateUsername($('#regStep1Form'));
		var validFamilyname = validateFamilyName();
		
		var customvalidation = validPassword && validRepeatPassword && validUsername && validateFamilyName();

		if ((!$('#regStep1Form').validate(regStep1Rules).form())||!customvalidation) {		
			return false;
		}

		$('#regStep1Form').submit();

	}	
	
	
	// ========
	// Step 2
	// ========
	// validate step2
	var regStep2Rules = {
			rules : {
				username : {
					required:true
				},
				email : {
					email : true, 
				},
				firstName : {
					required : true
				},
				lastName : {
					required : true
				},
				password : {
					required: true
				},
				repeatPassword : {
					required: true
				},
				dobMonth : {
					number : true
				}, 
				dobDay : {
			        number : true
				},
				dobYear : { 
					number : true
				},
			},
			groups : {
				dob : "dobMonth dobDay dobYear"
			},
			messages : {
				dobMonth : "Please enter a valid date",
				dobDay : "Please enter a valid date",
				dobYear : "Please enter a valid date",
				secureQuestion: "Please select a security question"
			},
			errorPlacement: function(error, element) {
				if (element.attr("name") == "dobMonth" || element.attr("name") == "dobDay"  || element.attr("name") == "dobYear" ) {
					var errElmt = $('<br/><div>');
					error.appendTo(errElmt);
					errElmt.insertAfter("#dobYear");	
				} else {
					var errElmt = $('<br/><div>');
					error.appendTo(errElmt);
					errElmt.insertAfter(element);							
				}

			}				
	};
	

	function addFamilyMember(form) {
		var validPassword = validatePassword($('#regStep2Form'));
		var validRepeatPassword = validateRepeatPassword($('#regStep2Form'));
		var validUsername = validateUsername($('#regStep2Form'));
		
		var customvalidation = validPassword && validRepeatPassword && validUsername;

		if ((!$('#regStep2Form').validate(regStep2Rules).form())||!customvalidation) {
			return false;	
		}

		$('#regStep2Form').submit();

	}	


	
});

