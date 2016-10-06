<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">


	<div class="col-md-5">
		<h3 class="text-primary"> Access Assistance Step1</h3>
		<p class="text-primary">
			<span class="glyphicon glyphicon-ok"></span>Please enter username to
			continue
		</p>
		<p class="text-primary">
			<span class="glyphicon glyphicon-ok"></span>Send email to <a
				href="mailto:family24x7@gmail.com" target="_top">Customer
				Support</a> for difficulties
		</p>


	</div>

	<div class="col-md-7">
		<c:if test="${success != true }">
			<div class="error">
				<span id="errorText">${errorMsg}</span>
			</div>
		</c:if>
		<div>
			<form class="form-horizontal" role="form" id="accessHelpStep1Form"
				name="form" novalidate>

				<table class="table table-bordered">
					<tr>
						<th>username</th>
						<td>&nbsp;<input type="text" id="username" name="username"
							value="" /></td>
					</tr>


				</table>

				<div>
					<input id="validateUsername"
						class="btn btn-primary"
						type="submit" value="Submit" />
				</div>
				<br />

			</form>

		</div>




	</div>




</div>
