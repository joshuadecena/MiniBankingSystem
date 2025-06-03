(function () {
  'use strict';

  angular
    .module('miniBankingApp')
    .controller('LoginController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {

      $scope.credentials = {
        username: '',
        password: ''
      };

      $scope.errorMessage = '';

      $scope.login = function () {
        $http.post('http://localhost:8080/api/auth/login', $scope.credentials)
          .then(function (response) {
            const data = response.data;

            // Save to localStorage
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(data.user));

            // Update global state
            $rootScope.isAuthenticated = true;
            $rootScope.currentUser = data.user;

            // Redirect based on role
            if (data.user.role === 'ADMIN') {
              $location.path('/dashboard');
            } else {
              $location.path('/dashboard');
            }
          })
          .catch(function () {
            $scope.errorMessage = 'Invalid username or password.';
          });
      };
    }]);
})();
