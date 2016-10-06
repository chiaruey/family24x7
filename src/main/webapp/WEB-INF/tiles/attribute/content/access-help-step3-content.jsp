<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">
	<div class="col-md-5">
		
		<h3 class="text-primary"> Access Assistance Step3</h3>
		<p class="text-primary">
			<span class="glyphicon glyphicon-ok"></span>Please enter your new password
		</p>
		<p class="text-primary">
			<span class="glyphicon glyphicon-ok"></span>Send email to <a
				href="mailto:family24x7@gmail.com" target="_top">Customer
				Support</a> for difficulties
		</p>		

	</div>
	<div class="col-md-7">
		<c:if test="${success != true }">
			<div class="error"><span id="errorText">${errorMsg}</span></div>
		</c:if>
		<div >
			<form class="form-horizontal" role="form" id="accessHelpStep3Form"
				name="form" novalidate>

				<table class="table table-bordered">
					<tr>
						<th nowrap="nowrap" align="right">username</th>
						<td><input type="text" id="username" name="username" value="${username}" disabled /></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">Password</th>
						<td><input type="password" id="password" name="password" value=""  /></td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="right">Repeat Password</th>
						<td><input type="password" id="repeatPassword" name="repeatPassword" value="" /></td>
					</tr>				
				</table>

			</form>

			<div>
				<input id="resetPassword"
					class="btn btn-primary"
					type="submit" value="Submit" />

			</div>
			<br />

		</div>




	</div>





</div>
