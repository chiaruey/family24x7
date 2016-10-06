<%@ tag %>

<%@ include file="/WEB-INF/jspf/common/import_tag_lib.jspf" %>

<div class="modal fade" id="addMyMessagePopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="text-info text-center">
					<span class="glyphicon glyphicon-pencil"></span> Add message
				</h4>
			</div>
			<div class="modal-body" style="padding: 40px 50px;">
				<form role="form" id="myMessageForm">
					<div class="form-group">
						<label for="myMessageText">New message</label> 
							
							<textarea id="myMessageText" name="myMessageText"
						onkeyup="checkMessageChars(this, 60, event);"
						onkeydown="checkMessageChars(this, 60, event);" rows="5"
						class="form-control"></textarea>
						<p class="text-muted"> (max: 60 characters)</p>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary btn-lg" id="addMessageBtn"
					data-dismiss="modal">
					<span class="glyphicon glyphicon-ok"></span> Add message
				</button>
			</div>
		</div>
	</div>
</div>

<!-- Error message for too many characters -->
<div class="modal fade" id="tooManyCharsPopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="text-danger text-center">
					<span class="glyphicon glyphicon-ban-circle"></span> Over Maximum letters
				</h4>
			</div>

			<div class="modal-body">
				<p class="text-danger">
					This field allows only 60 letters
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger btn-lg" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Informational message for successfully creating messages -->
<div class="modal fade" id="messageCreatedPopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="text-success text-center">
					<span class="glyphicon glyphicon-ok"></span> Message Created
				</h4>
			</div>

			<div class="modal-body">
				<p class="text-success">
					Your message has been successfully created!
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="refreshPageBtn btn btn-success btn-lg" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<!-- Informational message for successfully deleting messages -->
<div class="modal fade" id="messageDeletedPopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="text-success text-center">
					<span class="glyphicon glyphicon-ok"></span> Message Deleted
				</h4>
			</div>

			<div class="modal-body">
				<p class="text-success">
					Your message has been successfully deleted!
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="refreshPageBtn btn btn-success btn-lg" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>


<!-- SystemError popup -->
<div class="modal fade" id="systemErrorPopup" role="dialog">

	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="text-danger text-center">
					<span class="glyphicon glyphicon-ban-circle"></span> System Error
				</h4>
			</div>

			<div class="modal-body">
				<p class="text-danger">
					Sorry, we have experienced system error, please come back error
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="refreshPageBtn btn btn-danger btn-lg" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
