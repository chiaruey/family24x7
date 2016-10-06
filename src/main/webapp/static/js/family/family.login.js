
angular.module('loginView', ['ngMessages', 'ngAnimate']) 
    .controller('LoginCtrl', ['$scope', '$http', function ($scope, $http) {

        var fieldWithFocus;
 
        $scope.vm = {
            submitted: false,
            errorMessages: [],
            errorMessage: ''
        };

        $scope.focus = function (fieldName) {
            fieldWithFocus = fieldName;
        };

        $scope.blur = function (fieldName) {
            fieldWithFocus = undefined;
        };

        $scope.isMessagesVisible = function (fieldName) {
            return fieldWithFocus === fieldName || $scope.vm.submitted;
        };

        $scope.preparePostData = function () {
            var username = $scope.vm.username != undefined ? $scope.vm.username : '';
            var password = $scope.vm.password != undefined ? $scope.vm.password : '';
            
            return 'username=' + username + '&password=' + password;
        };

        $scope.onLogin = function () {
            console.log('Attempting login with username ' + $scope.vm.username + ' and password ' + $scope.vm.password);

            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {
                return;
            }

            var postData = $scope.preparePostData();
            
            
            $http({
                method: 'POST',
                url: getAppPath() + 'authenticate',
                data: postData,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-Login-Ajax-call": 'true'
                }
            })       
            .then(function(response) {
                if (response.data == 'ok') {
                    window.location.replace(getAppPath() + 'home');
                }
                else {
                    $scope.vm.errorMessages = [];
                    $scope.vm.errorMessages.push({description: 'Access denied'});
                    $scope.vm.errorMessage = 'Access Denied';
                }
            })
            ;
        };

    }])
    .controller('demoUsersController', ['$scope', function($scope) {
		$scope.items = ['single', 'couple', 'family of 4'];
		$scope.selection = $scope.items[0];
		

        $scope.init = function () {
            // check if there is query in url
            // and fire search in case its value is not empty
//        	alert('hi, loginView');
//        	myCssVar='css-class';
        };
        
        
        $scope.$on('$viewContentLoaded', function() {
//        	alert('hi, viewContentLoaded');
//        	myCssVar='css-class';
        });
    }])
    .directive('single', function() {
    	return {
    	    restrict: 'E',
    	    template: '<table class="table table-bordered">\
    	    	<tr><th>username:</th><th>password:</th></tr>\
    	    	<tr><td><span>the.single</span></td><td><span>test123</span></td></tr></table>'
    	  };
		})
	.directive('couple', function(){
		return {
			restrict: 'E',
			template: '<table class="table table-bordered"><tr><th>username:</th><th>password:</th></tr>\
				<tr><td><span>the.husband</span></td><td><span>test123</span></td></tr>\
				<tr><td><span>the.wife</span></td><td><span>test123</span></td></tr></table>'
		};
	})
	.directive('familyof4', function(){
		return {
			restrict: 'E',
			template: '<table class="table table-bordered" ><tr><th>username:</th><th>password:</th></tr>\
				<tr><td><span>the.dad</span></td><td><span>test123</span></td></tr>\
				<tr><td><span>the.mom</span></td><td><span>test123</span></td></tr>\
				<tr><td><span>the.son</span></td><td><span>test123</span></td></tr>\
				<tr><td><span>the.daughter</span></td><td><span>test123</span></td></tr></table>'
		};
	})

;