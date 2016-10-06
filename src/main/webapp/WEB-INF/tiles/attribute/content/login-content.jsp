<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="col-md-6">
	<div ng-init="checked=false" ng-controller="LoginCtrl">
	
		<div class="form-messages errors "
			ng-show="vm.errorMessages.length > 0" ng-cloak>
			<img class="error-icon"
				src="${requestScope.appPath}static/img/error-icon.png">
	
			<div class="messages-group">
				<div ng-repeat="error in vm.errorMessages">{{error.description}}</div>
			</div>
		</div>
	
		<form class="form-horizontal" role="form" ng-submit="onLogin()"
			name="form" novalidate>
	
			<p class="text-info col-sm-8 col-sm-offset-4">
				Sign in with your <strong>Family Account</strong>
			</p>
	
			<div class="form-group">
				<label for="username" class="col-sm-4 control-label">Username</label>
				<div class="col-sm-8">
	
					<input class="form-control" type="text" ng-focus="focus('username')"
						ng-blur="blur('username')" ng-model="vm.username" name="username"
						placeholder="Username" required ng-minlength="3">
	
					<div class="error" ng-show="isMessagesVisible('username')"
						ng-messages="form.username.$error" ng-cloak>
						<div ng-message="required">The username is mandatory</div>
						<div ng-message="minlength">Minimum 3 characters</div>
					</div>
	
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-4 control-label">Password</label>
				<div class="col-sm-8">
					<input type="password" class="form-control"
						ng-focus="focus('password')" ng-blur="blur('password')"
						ng-model="vm.password" name="password" placeholder="Password"
						required ng-minlength="6">
					<div class="error" ng-show="isMessagesVisible('password')"
						ng-messages="form.password.$error" ng-cloak>
						<div ng-message="required">The password is mandatory</div>
						<div ng-message="minlength">Minimum 6 characters</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<p>
						<a href="${requestScope.appPath}accessHelp/step1" id="forgotLnk">I
							can't access my account</a>
				</div>
			</div>
	
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<input id="signInBtn" class="btn btn-primary" type="submit"
						name="signIn" value="Sign in" />
				</div>
	
			</div>
		</form>
	</div>
	
	<div ng-init="checked=false" ng-controller="demoUsersController">
		<form class="form-horizontal" role="form"
		name="form" novalidate>
			<div class="row">
				<div class="col-sm-offset-4 col-sm-8">
					<div class="checkbox form-group ">
						<label> <input type="checkbox" ng-model="checked" class="text-muted"
							name="demouser"> Select to show demo users
						</label>
					</div>
				</div>
	
				<label class="col-sm-4 control-label check-element show-hide" ng-show="checked"> Demo Users</label>
				<div class=" col-sm-8 ">
		
					<div>
						<select class="check-element show-hide form-control" ng-show="checked"
							ng-model="selection" ng-options="item for item in items">
						</select>
					</div>
				</div>
				<div class="col-sm-offset-4 col-sm-8 ">
					<div class="check-element show-hide" ng-show="checked"
						style="clear: both;">
						<br />
						<div class="demoUsers-switch-container" ng-switch on="selection">
							<div  ng-switch-when="single">
								<single></single>
							</div>
							<div ng-switch-when="couple">
								<couple></couple>
							</div>
							<div ng-switch-default>
								<familyof4></familyof4>
							</div>
						</div>
					</div>
				</div>
			</div>
	
		</form>
	

	</div>
	<!-- Angularjs end -->

	<form id="globalForm">
		<input type="hidden" name="appPath" value="${requestScope.appPath}">
	</form>

	<script type="text/javascript"
		src="${requestScope.appPath}static/js/family/family.common.js"></script>
	<script type="text/javascript"
		src="${requestScope.appPath}static/js/family/family.login.js"></script>
</div>


