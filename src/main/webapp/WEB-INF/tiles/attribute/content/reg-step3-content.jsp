<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

	<div class="row">
		<div class="col-md-5 jumbotron" >

			  <p class="lead text-primary">Registration Step 3 of 3</p>
			  <dl class="text-info">
			    <dt>To add home address</dt>
			    <dd>- Select the <strong>I have more family member</strong> check box
			    <dd>- Fill all fields</dd>
			    <dd>- Click on the <strong>ADD NEW FAMILY MEMBER</strong> button</dd>
			    <br />		  
			    <dt>To skip this step</dt>
			    <dd>- Leave all fields blank </dd>
			    <dd>- Click on the <strong>Skip</strong> button</dd>
			    <br />   		    
			  </dl>		

			
			<br />
			<c:if test="${! empty newMemberInfo.firstName}">
				<p class="lead text-primary">Family Member List</p>
				<table class="table table-bordered table-condensed">
					<tbody>
						<tr><td colspan="2"><strong>House Head</strong></td></tr>
						<tr><th scope="row">Name:</th><td>${newMemberInfo.firstName }&nbsp;${newMemberInfo.lastName}</td></tr>
						<tr><th scope="row">Email:</th><td>${newMemberInfo.email }</td></tr>
						<tr><th scope="row">role:</th><td>${newMemberInfo.role }</td></tr>
					</tbody>
				</table>
			
			</c:if>
			
			<br />
			<c:if test="${newMemberInfo.familyList.size() > 0}">
				<c:forEach items="${familyMember.role}" var="familyMember">
					<br />
					<table class="table table-bordered table-condensed">
					<tbody>
						<tr><th scope="row">Name:</th><td>${familyMember.firstName }&nbsp;${familyMember.lastName }</td></tr>
						<tr><th scope="row">Email:</th><td>${familyMember.email }</td></tr>
						<tr><th scope="row">role:</th><td>${familyMember.role }</td></tr>
					</tbody>
				</table>
				</c:forEach>	
			
			</c:if>
		</div>	
	
		<div class="col-md-7"   ng-app="">
		
			<c:if test="${success != true }">
				<div id="regErrorDiv" class="error"><span id="regErrorSpan">${errorMsg}</span></div>
			</c:if>		
			
				<form id="updateAddressForm" name="updateAddressForm" class="form-horizontal"
					action="${requestScope.appPath}registration/updateAddress" method="post">
					
				    <div class="form-group">
				      <div class="col-sm-offset-2 col-sm-10">
				        <div class="checkbox">
				          <label><input type="checkbox"  ng-model="addHomeAddress"> check to add home address </label>
				        </div>
				      </div>
				    </div>
				    					
					<input type="hidden" name="id" value="${family.address.id }" />
					
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="email">street:</label>
				      <div class="col-sm-10">
				        <input type="street" class="form-control" id="street" placeholder="Enter street" ng-disabled="!addHomeAddress" >
				      </div>
				    </div>					
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="city">City:</label>
				      <div class="col-sm-10">
				        <input type="city" class="form-control" id="city" placeholder="Enter city" ng-disabled="!addHomeAddress" >
				      </div>
				    </div>					
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="city">State:</label>
				      <div class="col-sm-10">
				        <input type="state" class="form-control" id="state" placeholder="Enter state" ng-disabled="!addHomeAddress" >
				      </div>
				    </div>					

				    <div class="form-group">
				      <label class="control-label col-sm-2" for="country">Country:</label>
				      <div class="col-sm-10">
				        <input type="country" class="form-control" id="country" placeholder="Enter country" ng-disabled="!addHomeAddress" >
				      </div>
				    </div>					
				    <div class="form-group">
				      <label class="control-label col-sm-2" for="country">ZIP:</label>
				      <div class="col-sm-10">
				        <input type="zip" class="form-control" id="zip" placeholder="Enter zip" ng-disabled="!addHomeAddress" >
				      </div>
				    </div>		

											    			
				    <div class="form-group">
				      <div class="col-sm-offset-2 col-sm-10">
						<input class="btn btn-primary" type="submit" id="addAddressBtn" ng-disabled="!addHomeAddress" value="Add home Address" />	
						<input class="btn btn-default" type="submit" id="skilAddressBtn" ng-disabled="addHomeAddress" value="I don't want to add address" />	
				      </div>
				    </div>		
				</form>

		</div>	
	</div> 