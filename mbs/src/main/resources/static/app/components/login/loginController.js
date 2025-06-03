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
            url: '/perform_login',
            data: data,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            withCredentials: true  // ensure cookies (session) are sent/received properly
        }).then(function(response) {
            // Force redirect to dashboard after login success
            $window.location.href = '/admin/dashboard/dashboard.html';
        }, function(errorResponse) {
            $scope.error = 'Invalid username or password';
        });
    };
}]);
 