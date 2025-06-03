angular.module('bankingApp').controller('LoginController', ['$scope', '$http', '$window', function($scope, $http, $window) {
    $scope.credentials = {
        username: '',
        password: ''
    };
 
    $scope.error = null;
 
    $scope.login = function() {
        $scope.error = null;
 
        const data = `username=${encodeURIComponent($scope.credentials.username)}&password=${encodeURIComponent($scope.credentials.password)}`;
 
        $http({
            method: 'POST',
            url: '/api/auth/login',
            data: $scope.credentials,
            headers: { 'Content-Type': 'application/json' },
            withCredentials: true  // ensure cookies (session) are sent/received properly
        }).then(function(response) {
            // Force redirect to dashboard after login success
            $window.location.href = '/dashboard/admin'; // or '/dashboard/customer' based on user role
        }, function(errorResponse) {
            $scope.error = 'Invalid username or password';
        });
    };
}]);
 