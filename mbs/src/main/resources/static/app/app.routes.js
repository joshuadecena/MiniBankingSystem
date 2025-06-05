// static/app/app.routes.js
(function() {
  'use strict';

  angular
    .module('mbsApp')
    .config(['$routeProvider', function($routeProvider) {
      $routeProvider
        .when('/login', {
          templateUrl: 'app/login/login.html',
          controller: 'LoginController',
          controllerAs: 'vm'
        })
        .when('/dashboard/admin', {
          templateUrl: 'app/dashboard/admin/admin.html',
          controller: 'AdminController',
          controllerAs: 'vm'
        })
        .when('/dashboard/customer', {
          templateUrl: 'app/dashboard/customer/customer.html',
          controller: 'CustomerController',
          controllerAs: 'vm'
        })
        .otherwise({
          redirectTo: '/login'
        });
    }]);
})();
