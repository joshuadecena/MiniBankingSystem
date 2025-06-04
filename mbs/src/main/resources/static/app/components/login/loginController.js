angular.module('bankingApp').controller('LoginController', ['$scope', '$http', '$window', 'authService',
  function($scope, $http, $window, authService) {
    $scope.credentials = {
      username: '',
      password: ''
    };
    $scope.error = null;

    $scope.login = function() {
      $scope.error = null;

      $http.post('/api/auth/login', $scope.credentials, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true
      }).then(function(response) {
        const userData = {
          token: response.data.token,
          username: response.data.username,
          role: response.data.role
        };

        authService.setUser(userData);

        if (userData.role === 'ADMIN') {
          $window.location.href = '/dashboard/admin';
        } else if (userData.role === 'CUSTOMER') {
          $window.location.href = '/dashboard/customer';
        } else {
          $window.location.href = '/';
        }
      }, function(errorResponse) {
        $scope.error = 'Invalid username or password';
      });
    };
  }
]);
