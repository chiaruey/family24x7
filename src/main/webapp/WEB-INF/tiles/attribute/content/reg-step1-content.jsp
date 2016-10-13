<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="row">
	
	<div class="col-md-5 jumbotron">
		<p class="lead text-primary">Registration Step 1 of 3</p>
		  <dl class="text-info">
		    <dt>Smart</dt>
		    <dd>- You are making the right decision for your family</dd>
		    <br />		  
		    <dt>Secure</dt>
		    <dd>- All the information is secured in the Amazon cloud</dd>
		    <br />
		    <dt>Next Step</dt>
		    <dd>- Click on the button to enter your family information</dd>    	
		    <br />	    		    
		  </dl>		
	</div>
	
	
	<div class="col-md-7">
		<c:if test="${success != true }">
			<div id="regErrorDiv" class="error">
				<span id="regErrorSpan">${errorMsg}</span>
			</div>
		</c:if>
		<sf:form cssClass="form-horizontal" id="regStep1Form"
			action="${requestScope.appPath}registration/addHouseHead"
			method="post" modelAttribute="newMemberInfo">
	
			<div class="form-group">
				<label class="control-label col-sm-3" for="familyName">Family ID:</label>
				<div class="col-sm-9">
				  <sf:input path="familyName" cssClass="form-control"
								value="${newMemberInfo.familyName }"  placeholder=" unique family id" />
				</div>
			</div>
	
			<div class="form-group">
				<label class="control-label col-sm-3" for="username">Username:</label>
				<div class="col-sm-9">
				  <sf:input path="username" cssClass="form-control" placeholder=" Username" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="email">Email:</label>
				<div class="col-sm-9">
				  <sf:input path="email" cssClass="form-control" placeholder=" email" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="firstName">Full Name:</label>

		    		<div class="col-xs-4">
		    			<sf:input path="firstName" cssClass="form-control" placeholder=" first name" />
		    		</div>
		    		<div class="col-xs-4">
		    			<sf:input path="lastName" cssClass="form-control" placeholder=" last name"/>
		    		</div>			    									  
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="password">Password:</label>
				<div class="col-sm-9">
				  <sf:input path="password" cssClass="form-control" placeholder=" password"/>
				</div>
			</div>
	
			<div class="form-group">
				<label class="control-label col-sm-3" for="repeatPassword">Repeat password:</label>
				<div class="col-sm-9">
				  <sf:input path="repeatPassword" cssClass="form-control" placeholder=" repeat password" />
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="secureQuestion">Secure Question:</label>
				<div class="col-sm-9">
					<sf:select path="secureQuestion" id="secureQuestion"
						cssClass="form-control">
						<c:forEach items="${secureQuestionList}" var="secureQuestion">
							<sf:option value="${secureQuestion.id}">${secureQuestion.secureQuestionText}</sf:option>
						</c:forEach>
					</sf:select>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="secureQuestionAnswer">Secure Answer:</label>
				<div class="col-sm-9">
				  <sf:input path="secureQuestionAnswer" cssClass="form-control" placeholder=" secure answer"/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-sm-3" for="secureQuestionAnswer">Date Of Birth:</label>
			    <div class="col-sm-9">
		    		<div class="pull-left col-xs-3">
					    <sf:select cssClass="form-control" path="dobMonth" id="dobMonth">
							<sf:option value="Month">Month:</sf:option>
							<c:forEach items="${monthList}" var="month">
								<sf:option value="${month}">${month + 1}</sf:option>
							</c:forEach>
						</sf:select> 			    		
		    		</div>
		    		<div class="col-xs-1 lead text-primary"> / </div>
		    		<div class="col-xs-3">
						<sf:select cssClass="form-control" path="dobDay" id="dobDay">
							<sf:option value="Day">Day:</sf:option>
							<c:forEach items="${dayList}" var="day">
								<sf:option value="${day}">${day}</sf:option>
							</c:forEach>
						</sf:select> 				    		
		    		</div>
		    		<div class="col-xs-1 lead text-primary"> / </div>
		    		<div class="col-xs-3">
						<sf:select cssClass="form-control" path="dobYear" id="dobYear">
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
					<sf:select path='gender' id='gender' cssClass='form-control"'>
						<sf:option value="M">male</sf:option>
						<sf:option value="F">female</sf:option>
					</sf:select>
				</div>
			</div>


			<div class="form-group">
				<label class="control-label col-sm-3" for="gender">Role:</label>
				<div class="col-sm-9">
					<sf:select path='role' id='role' cssClass='form-control"'>
						<c:forEach items="${allRoles}" var="role">
							<c:if test="${role.isAdmin() }">
								<sf:option value="${role.name()}">${role.name()}</sf:option>
							</c:if>
						</c:forEach>
					</sf:select>
				</div>
			</div>
		    <div class="form-group">
		      <div class="col-sm-offset-3 col-sm-9">
		        <input id="addNewHouseHeadBtn" class="btn btn-primary" type="submit" value="Join Family 24x7" />
		      </div>
		    </div>				

		</sf:form>

	</div>





</div>
