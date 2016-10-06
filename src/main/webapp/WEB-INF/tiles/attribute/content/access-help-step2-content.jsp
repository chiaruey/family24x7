<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">


	<div class="col-md-5">

		<div class="introContent ui-corner-all">
			<h3 class="text-primary"> Access Assistance Step2</h3>
			<p class="text-primary">
				<span class="glyphicon glyphicon-ok"></span>Please enter secure
				answer to continue
			</p>
			<p class="text-primary">
				<span class="glyphicon glyphicon-ok"></span>Send email to <a
					href="mailto:family24x7@gmail.com" target="_top">Customer
					Support</a> for difficulties
			</p>


		</div>

	</div>
	<div class="col-md-7">
		<c:if test="${success != true }">
			<div class="error"><span id="errorText">${errorMsg}</span></div>
		</c:if>
		<div class="accessHelpFormDiv">
			<form class="form-horizontal" id="accessHelpStep2Form" role="form" 
				name="form" novalidate>

				<table class="table table-bordered">
					<tr>
						<th align="right">username</th>
						<td><input type="text" id="username" name="username" value="${username}" size="60" disabled /></td>
					</tr>
					<tr>
						<th align="right">secure question</th>
						<td><input type="text" id="secureQuestion" name="secureQuestion" value="${secureQuestion.secureQuestionText}" size="60"  disabled /></td>
					</tr>
					<tr>
						<th align="right">secure answer</th>
						<td><input type="text" id="secureQuestionAnswer" name="secureQuestionAnswer" size="60" value="" /></td>
					</tr>

				</table>
				<div>
					<input id="validateSecureQuestion"
						class="btn btn-primary"
						type="submit" value="Submit" />
					
				</div>
				<br />

			</form>

		</div>




	</div>




</div>
