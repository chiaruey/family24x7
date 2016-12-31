$(document).ready(function() {
	
	// ==================================
	// For add or modify transition type
	// ===================================
	
	$('[name="ioe"]').change(function(e){
		refreshTransType($(this).closest('form'));

	});
	
	$('[name="username"]').change(function(e){
		validateUsername($(this));
	});
	
	$('[name="password"]').change(function(e){
		validatePassword($(this));
	});
	
	
	$('[name="repeatPassword"]').change(function(e){
		validateRepeatPassword($(this));
	});
	

	function refreshTransType(form, dfltTransType) {
		var ioe = $(form).find('select[name="ioe"]').val();

		$(form).find('select[name="transType"]').children().remove();
		
		$.get(getAppPath() + 'getTransitionTypes', {'ioe':ioe}, function(data){
			if (data) {
				$.each(data, function(key, value)			
						{   
							var transTypeValue = value.ioe + value.id;
							if (dfltTransType != undefined && transTypeValue == dfltTransType) {
							     $(form).find('select[name="transType"]').
						          append($("<option></option>").
						          attr("class", "existTransType").
						          attr("value", transTypeValue).
						          attr('selected', 'selected').
						          text(value.name));
							} else {
							     $(form).find('select[name="transType"]').
						          append($("<option></option>").
						          attr("class", "existTransType").
						          attr("value", transTypeValue).
						          text(value.name)); 
							}


						});				
			} else {
				errorMsg('System Error. Fail to get transaction type. Please come back later!', 'SYSTEM ERROR');
			}
		});	
		
		
//		$(form).find('select[name="transType"]').append($("<option></option>").attr("value", (ioe=="I")?"newIncome":"newExpense").text((ioe =="I")?"[new income]":"[new expense]"));
	}

	// ==========================================
	// For adding a monetary transitional type
	// ==========================================

	// Open up dialog box for adding new monetary transaction
	$('#newTracTypePopup').dialog({ 
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
					"Create a transaction type": function() {
					if (createTracType()) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 
	
	// Open up dialog box for adding new monetary transaction
	$('#updTracTypePopup').dialog({ 
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Update the transaction type": function() {
					if (updateTransitionType()) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	

	
	// Show up add money transition dialog box
	$('#addTracTypeBtn').click(function(e){
		$('#newTracTypePopup').dialog('open');
		return false;		
	});
	

	function createTracType() {
		var tracTypeName = $('#newTracTypeName').find('input[name="tracTypeName"]').val();
		var ioe = $('#newTracTypeName').find('select[name="ioe"]').val();

		$.post(getAppPath() + 'addMoneyTracType', {'ioe':ioe, 'tracTypeName':tracTypeName} , function(data){
				if (data.success) {
					var url = getAppPath() + 'admin/manageMonetaryTracType';
					infoMsg('The Monetary Transaction Type has been successfully created!', 'SUCCESS', function(){ window.location = url });
				} else if (data.errorMsg) {
					errorMsg('fail to create Moneytary Transaction due to ' + data.errorMsg);
				} else {
					errorMsg('fail to create Moneytary Transaction. Please try again later');
				}

			}, 'json');
		return true;
	}

	function updateTransitionType() {

		if (!validateTransType($('#tracTypeNameForm'))) {
			return false;
		}
		
		var tracTypeName = $('#tracTypeNameForm').find('input[name="tracTypeName"]').val();
		
		var transTypeId = $('#tracTypeNameForm').find('input[name="transTypeId"]').val();
		
		$.post(getAppPath() + 'updMoneyTransitionType.json', {'transTypeId':transTypeId,  'transTypeName':tracTypeName} , function(data){
				if (data.success) {					
					var url = getAppPath() + 'admin/manageMonetaryTracType';
					infoMsg('This Monetary Transaction Type has been successfully updated!', 'SUCCESS', function(){ window.location = url });
				} else if (data.errorMsg) {
					errorMsg('fail to update Moneytary Transaction Type due to ' + data.errorMsg);
				} else {
					errorMsg('fail to create Moneytary Transaction Type. Please try again later');
				}

			}, 'json');
		return true;
	}

	function validateTransType(form) {
		// =======================================
		// money transition Type dialog validation
		// =======================================
		
		var transTypeDialogRules = {
			rules : {
				tracTypeName : {
					required : true
				}
			},	
			errorPlacement: function(error, element) {
				var errElmt = $('<br/><div>');
				error.appendTo(errElmt);
				errElmt.insertAfter(element);				
			}
		};



		if (!$(form).validate(transTypeDialogRules).form()) {
			return false;
		}	

		return true;

	}

	
	// ================================
	// For Left Menu Bar 
	// ================================
	
	$('#manageAccountLink').click(function(e){
		document.location.href = getAppPath() + 'admin';
	});
	
	$('#manageFamilyLink').click(function(e){
		document.location.href = getAppPath() + 'admin/ManageMyFamily';	
	});
	
	$('#manageAddressLink').click(function(e){
		document.location.href = getAppPath() + 'admin/manageAddress';
	});
	
	$('#manageTracTypeLink').click(function(e){
		document.location.href = getAppPath() +  'admin/manageMonetaryTracType';
	});
	
	if ($('.adminType').hasClass('manageAccount')) {
		$('#manageAccountLink').addClass('active');
		$('#manageAddressLink').removeClass('active');
		$('#manageFamilyLink').removeClass('active');		
		$('#manageTracTypeLink').removeClass('active');
	} else if ($('.adminType').hasClass('manageAddress')) {
		$('#manageAccountLink').removeClass('active');
		$('#manageAddressLink').addClass('active');
		$('#manageFamilyLink').removeClass('active');		
		$('#manageTracTypeLink').removeClass('active');
	} else if ($('.adminType').hasClass('manageFamily')) {
		$('#manageAccountLink').removeClass('active');
		$('#manageFamilyLink').addClass('active');
		$('#manageAddressLink').removeClass('active');
		$('#manageTracTypeLink').removeClass('active');
	} else {
		$('#manageAccountLink').removeClass('active');
		$('#manageFamilyLink').removeClass('active');
		$('#manageAddressLink').removeClass('active');
		$('#manageTracTypeLink').addClass('active');		
	}	  

	// ================================
	// For Manage Home Address
	// ================================

	// validate update address pop-up
	var updateAddressRules = {
			rules : {
				id : {
					required:true
				},			
				street : {
					required:true
				},
				city : {
					required : true
				},
				state : {
					required : true
				},
				country : {
					required: true
				},
				zip : {
					required : true
				}
			},
			errorPlacement: function(error, element) {
				var errElmt = $('<br/><div>');
				error.appendTo(errElmt);
				errElmt.insertAfter(element);	
			}				
	};



	$('#modifyHomeAddressBtn').click(function(e){
		
		if (!$('#updateAddressForm').validate(updateAddressRules).form()) {
			return false;
		} 
		var id = $('#updateAddressForm').find('input[name="id"]').val();
		
		var street = $('#updateAddressForm').find('input[name="street"]').val();
		var city = $('#updateAddressForm').find('input[name="city"]').val();
		var state = $('#updateAddressForm').find('input[name="state"]').val();
		var country = $('#updateAddressForm').find('input[name="country"]').val();
		var zip = $('#updateAddressForm').find('input[name="zip"]').val();	
		
		$('#updateAddressForm').submit();
		return true;

	});
	
	// ==========================================
	// For manage family member
	// ==========================================

	
	// Show up add family member dialog box
	$('#addFamilyBtn').click(function(e){
		refrestAddFamilyDlg();
		$('#addFamilyPopup').dialog('open');
		return false;		
	});
	
	// Open up dialog box for adding a family member
	$('#addFamilyPopup').dialog({ 
		autoOpen: false,
		height: 500,
		width: 400,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Add this family member": function() {
					if (addFamilyMember()) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	

	// Open up dialog box for editing a family member
	$('#editFamilyPopup').dialog({ 
		autoOpen: false,
		height: 470,
		width: 400,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Save your change": function() {
					if (updateFamilyMember()) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	

	// Open up dialog box for editing a family member
	$('#resetFamilyPasswordPopup').dialog({ 
		autoOpen: false,
		height: 270,
		width: 400,
		modal: true,
/*		position: 'center', */
		buttons: {
					"Save your change": function() {
					if (resetFamilyMemberPassword()) {
						$(this).dialog("close");
					}
				},
				Cancel: function() {
					$(this).dialog("close");
				}
			}
	}); 	
	

	// Refresh date list based on month
	$('#dobMonth').change(function(e){
		var month = $(this).val();
		if (month != 'Month') {
			refreshDateList();
		} else {
			$('#dobDay').children().remove();
			$('#dobDay').append($('<option></option>').attr('value', 'Day').text('Day:'));
			var i=0;
			for (i=0; i<=31; i++) {
				$('#dobDay').append($('<option></option>').attr('value', i).text(i));
			}
		}
	});
	
	// Refresh date list based on year
	$('#dobYear').change(function(e){
		var year = $(this).val();
		if (year != 'Year') {
			refreshDateList();
		}
	});


	
});

// validate add family pop-up
var familyMemberRules = {
		rules : {
			username : {
				required:true
			},			
			email : {
				email : true, 
				required:false
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
			dobMonth : {
				number : true
			}, 
			dobDay : {
		        number : true
			},
			dobYear : { 
				number : true
			}
		},
		groups : {
			dob : "dobMonth dobDay dobYear"
		},
		messages : {
			dobMonth : "Please enter a valid date",
			dobDay : "Please enter a valid date",
			dobYear : "Please enter a valid date"
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



function refreshDateList() {
	var year = $('#dobYear').val();
	var month = $('#dobMonth').val();
	var day = $("#dobDay").val();

	if (month != 'Month') {
		
		$('#dobDay').children().remove();
		$('#dobDay').append($('<option></option>').attr('value', 'Day').text('Day:'));
		
		if (year != 'Year') {
			$.get(getAppPath() + 'admin/getDateList.json', {'year':year, 'month':month}, function(dayList) {
				if (dayList) {
					
					for (i = 1; i <= dayList.length; i++) {
						$('#dobDay').append($('<option></option>').attr('value', i).html(i));
					}
					
					$("#dobDay").val(day);
				}
			});
			
		} else {
			$.get(getAppPath() + 'admin/getDateList.json', {'month':month}, function(dayList) {
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

// refresh the add family dialog box to default
function refrestAddFamilyDlg() {
	$('#addFamilyForm').find('select[name="dobDay"]').val('Day');
	$('#addFamilyForm').find('select[name="dobMonth"]').val('Month');
	$('#addFamilyForm').find('select[name="dobYear"]').val('Year');
	$('#addFamilyForm').find('select[name="gender"]').val('M');	
	$('#addFamilyForm').find('input').val('');
}

function addFamilyMember() {
	
	var validUsername = validateUsername($('#addFamilyForm').find('input[name="username"]'));
	
	if ((!$('#addFamilyForm').validate(familyMemberRules).form()) || (!validUsername)) {

		return false;

	} 
	
	var username = $('#addFamilyForm').find('input[name="username"]').val();	

	var email = $('#addFamilyForm').find('input[name="email"]').val();
	var firstName = $('#addFamilyForm').find('input[name="firstName"]').val();
	var lastName = $('#addFamilyForm').find('input[name="lastName"]').val();
	var password = $('#addFamilyForm').find('input[name="password"]').val();
	var gender = $('#addFamilyForm').find('select[name="gender"]').val();
	var dobYearStr = $('#addFamilyForm').find('select[name="dobYear"]').val();
	var dobMonthStr = $('#addFamilyForm').find('select[name="dobMonth"]').val();
	var dobDayStr = $('#addFamilyForm').find('select[name="dobDay"]').val();
	var role = $('#addFamilyForm').find('select[name="role"]').val();
	var adminStr = $('#addFamilyForm').find('select[name="admin"]').val();
	
	$.post(getAppPath() + 'admin/addNewFamilyMember', {'username':username, 'email':email, 'firstName':firstName, 'lastName':lastName, 'password':password, 'gender':gender, 
		'dobYearStr':dobYearStr, 'dobMonthStr':dobMonthStr, 'dobDayStr':dobDayStr, 'role':role, 'adminStr':adminStr}, function(data){

		if (data.success) {		
			var url = getAppPath() + 'admin';
			infoMsg('The new family member ' + firstName + ' ' + lastName + ' has been successfully added to the system!', 'SUCCESS', function(){ window.location = url });

		} else if (data.errorMsg) {
			errorMsg('fail to add family member due to ' + data.errorMsg);
		} else {
			errorMsg('fail to add family member. Please try again later');
		}	

	}, 'json');
	return true;
}


function updateFamilyMember() {
	
	if ((!$('#editFamilyForm').validate(familyMemberRules).form())) {
		return false;
	} 
	
	var username = $('#editFamilyForm').find('input[name="username"]').val();	

	var email = $('#editFamilyForm').find('input[name="email"]').val();
	var firstName = $('#editFamilyForm').find('input[name="firstName"]').val();
	var lastName = $('#editFamilyForm').find('input[name="lastName"]').val();
	var gender = $('#editFamilyForm').find('select[name="gender"]').val();
	var dobYearStr = $('#editFamilyForm').find('select[name="dobYear"]').val();
	var dobMonthStr = $('#editFamilyForm').find('select[name="dobMonth"]').val();
	var dobDayStr = $('#editFamilyForm').find('select[name="dobDay"]').val();
	var role = $('#editFamilyForm').find('select[name="role"]').val();
	var adminStr = $('#editFamilyForm').find('select[name="admin"]').val();

	$.post(getAppPath() + 'admin/updateFamilyMember', {'username':username, 'email':email, 'firstName':firstName, 'lastName':lastName, 'gender':gender, 
		'dobYearStr':dobYearStr, 'dobMonthStr':dobMonthStr, 'dobDayStr':dobDayStr, 'role':role, 'adminStr':adminStr}, function(data){

		if (data.success) {		
			var url = getAppPath() + 'admin';
			infoMsg('This family member ' + firstName + ' ' + lastName + ' has been successfully updated!', 'SUCCESS', function(){ window.location = url });		
		} else if (data.errorMsg) {
			errorMsg('fail to update family member due to ' + data.errorMsg);
		} else {
			errorMsg('fail to update family member. Please try again later');
		}	


	}, 'json');
	
	return true;
}

function resetFamilyMemberPassword() {
	
	if ((!$('#editFamilyForm').validate(familyMemberRules).form())) {
		return false;
	} 
	
	var username = $('#resetFamilyPasswordForm').find('input[name="username"]').val();	
	var password = $('#resetFamilyPasswordForm').find('input[name="password"]').val();

	$.post(getAppPath() + 'admin/resetFamilyPassword', {'username':username, 'password':password}, function(data){
		if (data.success) {		
			var url = getAppPath() + 'admin';
			infoMsg('This password of ' + username + ' has been has been successfully updated!', 'SUCCESS', function(){ window.location = url });		
		} else if (data.errorMsg) {
			errorMsg('fail to update family member due to ' + data.errorMsg);
		} else {
			errorMsg('fail to update family member. Please try again later');
		}	

	}, 'json');
	
	return true;
}


// ==========================================
// For Update Account Settings
// ==========================================

//validate add family pop-up
var updateAccountSettingsRules = {
		rules : {		
			email : {
				email : true, 
				required:false
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
			secureQuestion : {
				required : true,
				range: [1, 99]
			}, 
			secureQuestionAnswer : { 
				required : true
			}
		},
		messages: {
			secureQuestion: "The secure question is required"
		},
		errorPlacement: function(error, element) {
			var errElmt = $('<br/><div>');
			error.appendTo(errElmt);
			errElmt.insertAfter(element);	
		}				
};


$('#updateMyAccountBtn').click(function(e){
	if ((!$('#updateAccountSettingsForm').validate(updateAccountSettingsRules).form())||!validatePassword($('#updateAccountSettingsForm').find('input[name="password"]'))) {
		return false;
	} 
	var userId = $('#updateAccountSettingsForm').find('input[name="userId"]').val();
	
	var email = $('#updateAccountSettingsForm').find('input[name="email"]').val();
	var firstName = $('#updateAccountSettingsForm').find('input[name="firstName"]').val();
	var lastName = $('#updateAccountSettingsForm').find('input[name="lastName"]').val();
	var password = $('#updateAccountSettingsForm').find('input[name="password"]').val();
	var secureQuestion = $('#updateAccountSettingsForm').find('select[name="secureQuestion"]').val();
	var secureQuestionAnswer = $('#updateAccountSettingsForm').find('input[name="secureQuestionAnswer"]').val();	
	
	$('#updateAddressForm').submit();
	return true;
});



function updateMyAccount() {
	
	if ((!$('#updateAccountForm').validate(familyMemberRules).form())) {
		return false;
	} 
	
	var username = $('#updateAccountForm').find('input[name="username"]').val();	

	var email = $('#updateAccountForm').find('input[name="email"]').val();
	var firstName = $('#updateAccountForm').find('input[name="firstName"]').val();
	var lastName = $('#updateAccountForm').find('input[name="lastName"]').val();
	var password = $('#updateAccountForm').find('input[name="password"]').val();
	var gender = $('#updateAccountForm').find('select[name="gender"]').val();
	var dobYearStr = $('#updateAccountForm').find('select[name="dobYear"]').val();
	var dobMonthStr = $('#updateAccountForm').find('select[name="dobMonth"]').val();
	var dobDayStr = $('#updateAccountForm').find('select[name="dobDay"]').val();
	var role = $('#updateAccountForm').find('select[name="role"]').val();

	
	$.post(getAppPath() + 'admin/updateFamilyMember', {'username':username, 'email':email, 'firstName':firstName, 'lastName':lastName, 'password':password, 'gender':gender, 
		'dobYearStr':dobYearStr, 'dobMonthStr':dobMonthStr, 'dobDayStr':dobDayStr, 'role':role, 'adminStr':adminStr}, function(data){

		if (data.success) {		
			var url = getAppPath() + 'admin';
			infoMsg('This family member ' + firstName + ' ' + lastName + ' has been successfully updated!', 'SUCCESS', function(){ window.location = url });		
		} else if (data.errorMsg) {
			errorMsg('fail to update family member due to ' + data.errorMsg);
		} else {
			errorMsg('fail to update family member. Please try again later');
		}	


	}, 'json');
	
	return true;
}

function doCommand(select, username) {
	var selectedValue = select[select.selectedIndex].value;
	
	if (selectedValue == 'edit') {
		$.get(getAppPath() + 'admin/showEditUser', {'username':username}, function(data){
			if (data.success) {
				$('#editFamilyPopup').dialog('open');
				$('#editFamilyForm').find('input[name="username"]').val(data.username).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');	
				$('#editFamilyForm').find('input[name="email"]').val(data.email);				
				$('#editFamilyForm').find('input[name="firstName"]').val(data.firstName);				
				$('#editFamilyForm').find('input[name="lastName"]').val(data.lastName);				
				$('#editFamilyForm').find('select[name="gender"]').val(data.gender).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');	;	
				$('#editFamilyForm').find('select[name="admin"]').val(data.admin);	
				$('#editFamilyForm').find('select[name="role"]').val(data.roleName).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');	
				$('#editFamilyForm').find('select[name="dobMonth"]').val(data.dobMonth);	
				$('#editFamilyForm').find('select[name="dobDay"]').val(data.dobDay);	
				$('#editFamilyForm').find('select[name="dobYear"]').val(data.dobYear);					
				$('#editFamilyForm').find('input[name="id"]').val(data.id);	
				if (data.hoh == true) {
					$('#editFamilyForm').find('select[name="admin"]').attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');
				}
			} else {
				errorMsg('fail to edit user ' + user + ' due to ' + errorMsg);
			}
		}, 'json');

		

	} else if (selectedValue == 'resetPassword') {
		$.get(getAppPath() + 'admin/showEditUser', {'username':username}, function(data){
			if (data.success) {
				$('#resetFamilyPasswordPopup').dialog('open');
				$('#resetFamilyPasswordForm').find('input[name="username"]').val(data.username).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');						
				$('#resetFamilyPasswordForm').find('input[name="password"]').val(data.password);
				$('#resetFamilyPasswordForm').find('select[name="gender"]').val(data.gender).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');	;	
				$('#resetFamilyPasswordForm').find('select[name="role"]').val(data.roleName).attr('disabled','disabled').removeClass('ui-widget-content').addClass('disabled');					
				$('#resetFamilyPasswordForm').find('input[name="id"]').val(data.id);	
			} else {
				errorMsg('fail to reset user password for ' + user + ' due to ' + errorMsg);
			}
		}, 'json');
		
	} else if (selectedValue == 'activate') {
		confirmActivateMember(username);

	} else if (selectedValue == 'deactivate') {
		confirmDeactivateMember(username);
	}
	$(select).val('select');
	
	return false;
}



function doTrancTypeCommand(select, id, name) {
	var selectedValue = select[select.selectedIndex].value;
	
	if (selectedValue == 'edit') {
	
		$.get(getAppPath() + 'getSingleTransitionType.json', {'id':id}, function(data){
			var dfltTransType = data.ioe + data.transTypeId;
			$('#tracTypeNameForm').find('select[name="ioe"]').val(data.ioe).attr('disabled', true);
			$('#tracTypeNameForm').find('input[name="tracTypeName"]').val(data.name);	
			$('#tracTypeNameForm').find('input[name="transTypeId"]').val(data.id);	
			$('#updTracTypePopup').dialog('open');					
		});
		
	} else if (selectedValue == 'activate') {
		confirmActivateTracType(id, name);
	} else if (selectedValue == 'deactivate') {
		confirmDeactivatev(id, name);
	}

	$(select).val('select');
	
	return false;
}


function confirmActivateMember(username) {

    $("<div class='jqMessage'></div>").html('Are you sure you want to activate this user ' + username).dialog({
        title: 'Activate Member',
        resizable: false,
        modal: true,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
    	 		$.post(getAppPath() + 'admin/activateFamilyMember', {'username':username, 'activate':true}, function(data){
    				if (data.success) {
    					var href = location.href;
    					var url = href.replace(/#/g,'');
    					infoMsg('This family member has been successfully activated !', 'SUCCESS', function(){ window.location = url });	
    				} else if (data.errorMsg) {
    					errorMsg('fail to activate user ' + + username + ' due to ' + data.errorMsg);
    				} else {
    					errorMsg('fail to activate user ' + username + '. Please try again later');
    				}			
    			});	
            },
                "No": function () {
                $(this).dialog('close');
            }
        }
    }).prev(".ui-dialog-titlebar").css("color","blue");;
}


function confirmDeactivateMember(username) {

    $("<div class='jqMessage'></div>").html('Are you sure you want to deactivate this user ' + username).dialog({
        title: 'Deactivate Member',
        resizable: false,
        modal: true,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
		 		$.post(getAppPath() + 'admin/activateFamilyMember', {'username':username, 'activate':false}, function(data){
					if (data.success) {
						var href = location.href;
						var url = href.replace(/#/g,'');
						infoMsg('This family member has been successfully deactivated!', 'SUCCESS', function(){ window.location = url });	
					} else if (data.errorMsg) {
						errorMsg('fail to deactivate user ' + + username + ' due to ' + data.errorMsg);
					} else {
						errorMsg('fail to deactivate user ' + username + '. Please try again later');
					}			
				});	
            },
                "No": function () {
                $(this).dialog('close');
            }
        }
    }).prev(".ui-dialog-titlebar").css("color","blue");;
}


function confirmActivateTracType(id, name) {

    $("<div class='jqMessage'></div>").html('Do you want to activate this transition type : ' + name).dialog({
        title: 'Activate Transition Type',
        resizable: false,
        modal: true,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
        		$.post(getAppPath() + 'activateFamilyTracType', {'id':id, 'activate':true}, function(data){
        			if (data.success) {					
        				var href = location.href;
        				var url = href.replace(/#/g,'');
        				infoMsg('The Monetary Transaction has been successfully activated!', 'SUCCESS', function(){ window.location = url });	
        			} else if (data.errorMsg) {
        				errorMsg('fail to activate Moneytary Transaction due to ' + data.errorMsg);
        			} else {
        				errorMsg('fail to activate Moneytary Transaction. Please try again later');
        			}			
        		});
            },
                "No": function () {
                $(this).dialog('close');
            }
        }
    }).prev(".ui-dialog-titlebar").css("color","blue");;

    
}


function confirmDeactivatev(id, name) {

    $("<div class='jqMessage'></div>").html('Do you want to deactivate this transition type : ' + name).dialog({
        title: 'Deactivate Transition Type',
        resizable: false,
        modal: true,
        buttons: {
            "Yes": function () {
                $(this).dialog('close');
            	$.post(getAppPath() + 'activateFamilyTracType', {'id':id, 'activate':false}, function(data){
            		if (data.success) {					
            			var href = location.href;
            			var url = href.replace(/#/g,'');
            			infoMsg('The Monetary Transaction has been successfully deactivate!', 'SUCCESS', function(){ window.location = url });	
            		} else if (data.errorMsg) {
            			errorMsg('fail to deactivate Moneytary Transaction due to ' + data.errorMsg);
            		} else {
            			errorMsg('fail to deactivate Moneytary Transaction. Please try again later');
            		}			
            	});
            },
                "No": function () {
                $(this).dialog('close');
            }
        }
    }).prev(".ui-dialog-titlebar").css("color","blue");;
}


function validateUsername(usernameInput) {
	
	if ($('.invalidUserName').length > 0) {
		$('.invalidUserName').remove();
	}
	
	var username = $(usernameInput).val();
	if (validateUserNameString(username) == false) {
		var errElmt = $('<p class="error invalidUserName">username allows only lowercase, period(.), underscore(_) with 3-30 characters.</p>');
		errElmt.insertAfter(usernameInput);
		return false;
	} 
	
	$.get(getAppPath() + 'registration/isExistingUsername.json', {'username':username}, function(response) {
		if (response.result == true) {				
			var errElmt = $('<p class="error invalidUserName">Someone already has that username. Please try another.</p>');
			errElmt.insertAfter(usernameInput);
			return false;
		} 
	});
	return true;
}

function validatePassword(passwordInput) {
	
	if ($('.invalidPassword').length > 0) {
		$('.invalidPassword').remove();
	}
			
	var password = $(passwordInput).val();

	if (password.length > 15 || password.length < 6) {
		var errElmt = $('<br/><p class="error invalidPassword">Password must be between 6-15 characters</p>');
		errElmt.insertAfter(passwordInput);
		return false;
	}
	return true;
}


function validateRepeatPassword(repeatPasswordInput) {
	
	if ($('.invalidRepeatPassword').length > 0) {
		$('.invalidRepeatPassword').remove();
	}
			
	var password = $(repeatPasswordInput).closest('form').find('input[name="password"]').val();
	var repeatPassword = $(repeatPasswordInput).val();
	
	if (password != repeatPassword) {
		var errElmt = $('<br/><p class="error invalidRepeatPassword">Password does not match</p>');
		errElmt.insertAfter(repeatPasswordInput);
		return false;
	}
	return true;
}



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

