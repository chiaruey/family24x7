<%@ tag %>
<%@ attribute name="transitionTypes" type="java.util.List" required="true" %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div class="modal fade"  id="updTracPopup"  role="dialog" title="Add new monetary transaction">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="text-info text-center">
					<span class="glyphicon glyphicon-pencil"></span> Create a transaction
				</h4>
			</div>
			<div class="modal-body" style="padding: 40px 50px;">
				<p class="validateTips">All form fields are required.</p>
				<form id="updTracForm">
					<div class="form-group row">							
						<label for="tracDate" class="col-sm-4">Date</label>
						<input type="text" name="tracDate" class="col-sm-8 required date text ui-widget-content ui-corner-all" />	
					</div>		
					<div class="form-group row">
						<label for="ioe" class="col-sm-4">Income/Expense</label>
						<select class="ui-widget-content ui-corner-all col-sm-8" name="ioe">
							<option value="I">income</option>
							<option value="E">expense</option>
						</select>												
					</div>			
					<div class="form-group row">
						<label for="transType" class="col-sm-4">Transaction Type</label>
						<select class="ui-widget-content ui-corner-all col-sm-8" name="transType">
							<option id="newTransType">[New]</option>
							<c:forEach items="${transitionTypes}" var="transType">
								<option class="existTransType" value="${transType.ioe}${transType.id}-${transType.name}">${transType.name}</option>												
							</c:forEach>
						</select>							
					</div>	
					<div class="form-group row">
						<label for="comments" class="col-sm-4">Comments</label>
						<textarea name="comments" onkeyup="maxcharsForTextArea(this, 30, event);" onkeydown="maxcharsForTextArea(this, 30, event);" class="required text ui-widget-content ui-corner-all col-sm-8"></textarea>			
					</div>
					<div class="form-group row">
						<label for="amount" class="col-sm-4">Amount</label>
						<input type="text" name="amount" value="" class="required text ui-widget-content ui-corner-all col-sm-8" />		
					</div>			
					<input type="hidden" name="transId" value="-1" />
				</form>
				
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary btn-lg" id="updateTracBtn"
					data-dismiss="modal">
					<span class="glyphicon glyphicon-ok"></span> Update this transaction
				</button>
			</div>
			
		</div>
	</div>
</div>

<!-- Informational message for successfully updating messages -->
<div class="modal fade" id="transactionUpdatedPopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="text-success text-center">
					<span class="glyphicon glyphicon-ok"></span> Monetary Transaction Updated
				</h4>
			</div>

			<div class="modal-body">
				<p class="text-success">
					The Monetary Transaction has been successfully updated
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="refreshPageBtn btn btn-success btn-lg" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
