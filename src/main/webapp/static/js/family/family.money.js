////////////////////////////
// JQuery
////////////////////////////
$(document).ready(function(event) {

	$('[name="ioe"]').change(function(e){
		refreshTransType($(this).closest('form'));

	});
	
	// ============================
	// For calculating Total
	// ============================
	var totalIncome = $('input[name^="transAmountI"]').sum().toFixed(2);
	
	var totalExpense = $('input[name^="transAmountE"]').sum().toFixed(2);
	$('#totalIncome').text(totalIncome).formatCurrency();
	$('#totalExpense').text(totalExpense).formatCurrency();
	
	var netTotal = totalIncome - totalExpense;
	
	if (netTotal > 0)
		$('#netTotal').addClass('green').removeClass('red');
	else
		$('#netTotal').addClass('red').removeClass('green');
	
	$('#netTotal').text(netTotal).formatCurrency();
	
	
	// ==========================================================
	// Scrolling between the next Month and the previous month
	// ==========================================================
	$('#prev_month_arrow').click(function(e){
		var prevMonth = $("#moneyForm").find('input[name="prevMonth"]').val();
		var prevYear = $("#moneyForm").find('input[name="prevYear"]').val();
		//http://localhost:8080calendar
		var pathname = location.pathname;
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf(pathname)) + pathname;
		
		window.location=url + "?month=" + prevMonth+"&year=" + prevYear;		
	});
	

	$('#next_month_arrow').click(function(e){
		var nextMonth = $("#moneyForm").find('input[name="nextMonth"]').val();
		var nextYear = $("#moneyForm").find('input[name="nextYear"]').val();
		
		var pathname = location.pathname;
		var href = location.href;
		var url = href.slice(0, href.lastIndexOf(pathname)) + pathname;
		
		window.location=url + "?month=" + nextMonth + "&year=" + nextYear;
	});

	// ====================================
	// For adding a monetary transition
	// ====================================
	
	// Show up add money transition dialog box
	$('#addTracBtn').click(function(e){
//		refrestAddNewTransDlg();
//		$('#newTracPopup').dialog('open');
		
		$("#newTracPopup").modal("show");
		return false;		
	});

	// Date picker
	$('input[name="tracDate"]').datepicker({
		stepMonths: 0,
		onClose: function() {$(this).valid();}
	});
	
	// Show up add event dialog box
	$('#createTracBtn').click(function(e){
		createTransition();
		return false;		
	});
	
	// ============================================
	// For updating an existing monetary transition
	// ============================================
	
	// Date picker
	$('input[name="tracDate"]').datepicker();
	
	// Show up update event dialog box
	$('#updateTracBtn').click(function(e){
		updateTransition();
		return false;		
	});
	
	$('.refreshPageBtn').click(function (e) {
		refreshPage();
	});
	
});

function refrestAddNewTransDlg() {
	
	$('#newTracForm').find('select[name="ioe"]').val('I');
	$('#newTracForm').find('textarea[name="comments"]').val('');
	$('#newTracForm').find('input[name="amount"]').val('');
	
//	$(this).parent().next('.newTransNameDiv').remove();
	
	// Update Transaction Type
	refreshTransType($('#newTracForm'));
	$("#newTracForm").validate().resetForm();
}

function refreshPage() {
	var href = location.href;
	var url = href.replace(/#/g,'');
	window.location = url;
}

function refreshTransType(form, dfltTransType) {
	var ioe = $(form).find('select[name="ioe"]').val();

	$(form).find('select[name="transType"]').children().remove();
	
	$.get(getAppPath() + 'getActiveTransitionTypes.json', {'ioe':ioe}, function(data){
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
			errorMsg('Fail to get transaction type. Please come back later!', 'System Error');
		}
	});	
	
	
//	$(form).find('select[name="transType"]').append($("<option></option>").attr("value", (ioe=="I")?"newIncome":"newExpense").text((ioe =="I")?"[new income]":"[new expense]"));
}

function createTransition() {
	
//	if (!validateTransition($('#newTracForm'))) {
//		return false;
//	}

	var comments = $('#newTracForm').find('textarea[name="comments"]').val();
	var tracDate = $('#newTracForm').find('input[name="tracDate"]').val();
	var amount = $('#newTracForm').find('input[name="amount"]').val();
	var transType = $('#newTracForm').find('select[name="transType"]').val();
	var newTransName = $('#newTracForm').find('input[name="newTransName"]').val();

	$.post(getAppPath() + 'addMoneyTransition', {'transType':transType, 'comments':comments, 'amount':amount, 'tracDate':tracDate, 'newTransName':newTransName} , function(data){
		if (data.success) {
			// get application href
			var pathname = location.pathname;
			var appname = pathname.slice(0, pathname.lastIndexOf('/'));
			var apphref = location.origin + appname;
			
//			var url = apphref + '/money';
			
			
			$("#transactionCreatedPopup").modal("show");
//			infoMsg('The Monetary Transaction has been successfully created!', 'Monetary Transaction Created', function(){ window.location = url });
		} else if (data.errorMsg) {
			errorMsg('fail to create Moneytary Transaction due to ' + data.errorMsg);
		} else {
			errorMsg('fail to create Moneytary Transaction. Please try again later');
		}


	}, 'json');

	$("#newTracPopup").modal("hide");
	return true;
}


function validateTransition(form) {
	// ==================================
	// money transition dialog validation
	// ==================================
	
	var transDialogRules = {
		rules : {
			tracDate : {
				date : true
			},
			amount : {
				positiveNumber : true
			}
		},
		messages: {
			tracDate : {
				date : "Date in wrong format"
			}, 
			amount : {
				positiveNumber : "Amount must be positive number"
					
			}
		},			
		errorPlacement: function(error, element) {
			if (element.attr('name') == 'tracDate') {
				var errElmt = $('<div>');
				error.appendTo(errElmt);						
			} else {
				var errElmt = $('<br/><div>');
				error.appendTo(errElmt);
				errElmt.insertAfter(element);				
			}

		}
	};



	if (!$(form).validate(transDialogRules).form()) {
		return false;
	}	

	return true;

}


function updateTransition() {

//	if (!validateTransition($('#updTracForm'))) {
//		return false;
//	}
	var tracDate = $('#updTracForm').find('input[name="tracDate"]').val();
	var comments = $('#updTracForm').find('textarea[name="comments"]').val();
	var amount = $('#updTracForm').find('input[name="amount"]').val();
	var transId = $('#updTracForm').find('input[name="transId"]').val();

	$.post(getAppPath() + 'updMoneyTransition', {'transId':transId,  'tracDate':tracDate, 'comments':comments, 'amount':amount} , function(data){
			if (data.success) {
				// get application href
//				var pathname = location.pathname;
//				var appname = pathname.slice(0, pathname.lastIndexOf('/'));
//				var apphref = location.origin + appname;
//				
//				var url = apphref + '/money';
//				infoMsg('The Monetary Transaction has been successfully updated!', 'Monetary Transaction Updated', function(){ window.location = url });
				
				$("#transactionUpdatedPopup").modal("show");
			} else if (data.errorMsg) {
				errorMsg('fail to update Moneytary Transaction due to ' + data.errorMsg);
			} else {
				errorMsg('fail to create Moneytary Transaction. Please try again later');
			}


		}, 'json');
	$("#updTracPopup").modal("hide");
	return true;
}


function doCommand(select, id) {
	var selectedValue = select[select.selectedIndex].value;

	if (selectedValue == 'edit') {
		$.get(getAppPath() + 'getSingleTransition', {'id':id}, function(data){
				var dfltTransType = data.ioe + data.transTypeId;
				$('#updTracForm').find('select[name="ioe"]').val(data.ioe).attr('disabled', true);
				refreshTransType($('#updTracForm'), dfltTransType);
				$('#addTracBtn').text('save the change');		
				$("#updTracPopup").modal("show");
				$('#updTracForm').find('input[name="transId"]').val(data.transId);
				$('#updTracForm').find('textarea[name="comments"]').val(data.comments);
				$('#updTracForm').find('input[name="tracDate"]').val(data.date);
				$('#updTracForm').find('input[name="amount"]').val(data.amount);

				$('#updTracForm').find('select[name="transType"]').attr('disabled', true);
		});
		
	} else if (selectedValue == 'delete') {

		$.post(getAppPath() +  'deleteMoneyTransition', {'id':id}, function(data){
			if (data.success) {
				// get application href
				var pathname = location.pathname;
				var appname = pathname.slice(0, pathname.lastIndexOf('/'));
				var apphref = location.origin + appname;
				
				var url = apphref + '/money';
				infoMsg('This Monetary Transaction has been successfully deleted!', 'Monetary Transaction Deleted', function(){ window.location = url });
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

////////////////////////////
// Angular JS logic
////////////////////////////

angular.module('moneyView', ['ngMessages', 'ngAnimate']) 
.controller('addTracCtrl', ['$scope', '$http', function ($scope, $http) {
	
	
	
}]);


////////////////////////////////////////////////////////////////////////////////////////

