angular.module('miniBankingApp')
  .controller('NavbarController', ['authService', '$location', function(authService, $location) {
    var vm = this;

    vm.isLoggedIn = function() {
      return authService.isLoggedIn();
    };

    vm.getUserRole = function() {
      return authService.getUserRole();
    };

    vm.logout = function() {
      authService.logout();
      $location.path('/login');
    };
  }]);
