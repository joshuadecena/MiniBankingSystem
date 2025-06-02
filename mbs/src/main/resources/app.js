angular.module('bankingApp', ['ngRoute']);
.config(function($routeProvider) {
    $routeProvider
	.when('/login', {
	    templateUrl: function() {
	        return 'app/components/login/login.html';
	    },
	    controller: 'LoginController'
	})
    .when('/dashboard', {
        templateUrl: 'app/components/dashboard/dashboard.html',
        controller: 'DashboardController'
    })
    .when('/transfer', {
        templateUrl: 'app/components/transfer/transferView.html',
        controller: 'TransferController'
    })
    .when('/history', {
        templateUrl: 'app/components/history/historyView.html',
        controller: 'HistoryController'
    })
    .otherwise({ redirectTo: '/login' });
})
.run(function($rootScope, $location, AuthService) {
    $rootScope.$on('$routeChangeStart', function(event, next) {
       // if (next.originalPath !== '/login' && !AuthService.isAuthenticated()) {
          //  $location.path('/login');
        }
    });
});