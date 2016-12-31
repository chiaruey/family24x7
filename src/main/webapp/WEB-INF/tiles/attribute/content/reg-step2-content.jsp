<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

	<div class="row" >
		<div class="col-md-5 jumbotron">

			  <p class="lead text-primary">Registration Step 2 of 3</p>
			  <dl class="text-info">
			    <dt>To add a family member</dt>
			    <dd>- Select the <strong>I have more family member</strong> check box
			    <dd>- Fill all fields</dd>
			    <dd>- Click on the <strong>ADD NEW FAMILY MEMBER</strong> button</dd>
			    <br />		  
			    <dt>To go to the next step</dt>
			    <dd>- Leave all fields blank </dd>
			    <dd>- Click on the <strong>Go to the next step</strong> button</dd>
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
	
		<div class="col-md-7" ng-app="">
		
			<c:if test="${success != true }">
				<div id="regErrorDiv" class="error"><span id="regErrorSpan">${errorMsg}</span></div>
			</c:if>		
			
			<sf:form cssClass="form-horizontal" id="regStep2Form" method="post" action="${requestScope.appPath}registration/addNewFamilyMember" modelAttribute="newFamilyMemberInfo">
				<div class="form-group">
			      <div class="col-sm-offset-3 col-sm-9">
			        <div class="checkbox">
			          <label><input type="checkbox" ng-model="addFamily"> I want to add another family member</label>
			        </div>
			      </div>
			    </div>
						
				<div class="form-group">
					<label class="control-label col-sm-3" for="username">Username:</label>
					<div class="col-sm-9">
					  <sf:input path="username" cssClass="form-control" placeholder=" Username" ng-disabled="!addFamily" />
					</div>
				</div>
	
				<div class="form-group">
					<label class="control-label col-sm-3" for="email">Email:</label>
					<div class="col-sm-9">
					  <sf:input path="email" cssClass="form-control" placeholder=" email" ng-disabled="!addFamily" />
					</div>
				</div>
	
				<div class="form-group">
					<label class="control-label col-sm-3" for="firstName">Full Name:</label>
	
			    		<div class="col-xs-4">
			    			<sf:input path="firstName" cssClass="form-control" placeholder=" first name" ng-disabled="!addFamily"/>
			    		</div>
			    		<div class="col-xs-4">
			    			<sf:input path="lastName" cssClass="form-control" placeholder=" last name" ng-disabled="!addFamily"/>
			    		</div>			    									  
				</div>
		
				<div class="form-group">
					<label class="control-label col-sm-3" for="password">Password:</label>
					<div class="col-sm-9">
					  <sf:input path="password" cssClass="form-control" placeholder=" password" ng-disabled="!addFamily"/>
					</div>
				</div>
		
				<div class="form-group">
					<label class="control-label col-sm-3" for="repeatPassword">Repeat password:</label>
					<div class="col-sm-9">
					  <sf:input path="repeatPassword" cssClass="form-control" placeholder=" repeat password" ng-disabled="!addFamily" />
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-3" for="secureQuestionAnswer">Date Of Birth:</label>
				    <div class="col-sm-9">
			    		<div class="col-xs-3">
						    <sf:select cssClass="form-control" path="dobMonth" id="dobMonth" ng-disabled="!addFamily">
								<sf:option value="Month">Month:</sf:option>
								<c:forEach items="${monthList}" var="month">
									<sf:option value="${month}">${month + 1}</sf:option>
								</c:forEach>
							</sf:select> 			    		
			    		</div>
			    		<div class="col-xs-1 lead text-primary"> / </div>
			    		<div class="col-xs-3">
							<sf:select cssClass="form-control" path="dobDay" id="dobDay" ng-disabled="!addFamily">
								<sf:option value="Day">Day:</sf:option>
								<c:forEach items="${dayList}" var="day">
									<sf:option value="${day}">${day}</sf:option>
								</c:forEach>
							</sf:select> 				    		
			    		</div>
			    		<div class="col-xs-1 lead text-primary"> / </div>
			    		<div class="col-xs-3">
							<sf:select cssClass="form-control" path="dobYear" id="dobYear" ng-disabled="!addFamily">
								<sf:option value="Year">Year:</sf:option>
								<c:forEach items="${yearList}" var="year">
									<sf:option value="${year}">${year}</sf:option>
								</c:forEach>
							</sf:select>					    		
			    		</div>			    							
				    </div>
				</div>
	
				<div class="form-group">
					<label class="control-label col-sm-3" for="gender">Gender:</label>
					<div class="col-sm-9">
						<sf:select path='gender' id='gender' cssClass='form-control' ng-disabled='!addFamily'>
							<sf:option value="M">male</sf:option>
							<sf:option value="F">female</sf:option>
						</sf:select>
					</div>
				</div>
	
	
				<div class="form-group">
					<label class="control-label col-sm-3" for="gender">Role:</label>
					<div class="col-sm-9">
						<sf:select path='role' id='role' cssClass='form-control' ng-disabled='!addFamily'>
							<c:forEach items="${allRoles}" var="role">
								<c:if test="${role.isAdmin() }">
									<sf:option value="${role.name()}">${role.name()}</sf:option>
								</c:if>
							</c:forEach>
						</sf:select>
					</div>
				</div>
						
				<div class="form-group">
					<label class="control-label col-sm-3" for="gender">Administrator:</label>
					<div class="col-sm-9">
						<sf:select path='admin' id='admin' cssClass='form-control' ng-disabled='!addFamily'>
							<sf:option value="true">true</sf:option>
							<sf:option value="false">false</sf:option>
						</sf:select>
					</div>
				</div>		
					
			    <div class="form-group">
			      <div class="col-sm-offset-3 col-sm-9">
					<input class="btn btn-primary" type="submit" id="addFamilyBtn" ng-disabled="!addFamily" value="Add new family member" />	
					<input class="btn btn-default" type="submit" id="finishRegBtn" ng-disabled="addFamily" value="Go to the next step" />	
			      </div>
			    </div>			
	
			</sf:form>

		</div>	
	</div> 