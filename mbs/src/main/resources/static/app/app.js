angular.module('bankingApp', ['ngRoute'])
.config(function($routeProvider) {
    $routeProvider
    .when('/login', {
        templateUrl: 'static/app/components/login/login.html',
        controller: 'LoginController'
    })
    .when('/dashboard', {
        // Use a function to dynamically decide templateUrl & controller based on role
        templateUrl: function() {
            // This function runs during routing
            // Get role from some auth service or localStorage (adjust accordingly)
            var role = localStorage.getItem('userRole'); // example source of role
            
            if (role === 'admin') {
                return 'static/app/components/dashboard/admin/admin.html';
            } else if (role === 'customer') {
                return 'static/app/components/dashboard/customer/customer.html';
            } else {
                // fallback - redirect to login or a default
                return 'static/app/components/login/login.html';
            }
        },
        controller: function($scope, $location) {
            var role = localStorage.getItem('userRole');

            if (role === 'admin') {
                // Dynamically load admin controller
                // You may need to register it or use ng-controller in admin.html
                $location.path('/dashboard/admin');
            } else if (role === 'customer') {
                // Dynamically load customer controller
                $location.path('/dashboard/customer');
            } else {
                // no role? go to login
                $location.path('/login');
            }
        }
    })
    .when('/dashboard/admin', {
        templateUrl: 'static/app/components/dashboard/admin/admin.html',
        controller: 'AdminController'
    })
    .when('/dashboard/customer', {
        templateUrl: 'static/app/components/dashboard/customer/customer.html',
        controller: 'CustomerController'
    })
    .when('/transfer', {
        templateUrl: 'static/app/components/transfer/transferView.html',
        controller: 'TransferController'
    })
    .when('/history', {
        templateUrl: 'static/app/components/history/historyView.html',
        controller: 'HistoryController'
    })
    .otherwise({ redirectTo: '/login' });
})
.run(function($rootScope, $location, AuthService) {
    $rootScope.$on('$routeChangeStart', function(event, next) {
        if (next.originalPath !== '/login' && !AuthService.isAuthenticated()) {
            $location.path('/login');
        }
    });
});
