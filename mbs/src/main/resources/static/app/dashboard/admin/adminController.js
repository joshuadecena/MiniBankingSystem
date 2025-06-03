angular.module('bankingApp')
.controller('AdminController', function($scope, $location, authService, bankService) {
    $scope.view = 'accounts';
    $scope.accounts = [];
    $scope.accountTransactions = [];
    $scope.selectedAccountId = '';
    
    // Load all accounts
    bankService.getAllAccounts()
    .then(function(accounts) {
        $scope.accounts = accounts;
    });
    
    $scope.viewTransactions = function(accountId) {
        $scope.selectedAccountId = accountId;
        bankService.getTransactions(accountId)
        .then(function(transactions) {
            $scope.accountTransactions = transactions;
            $scope.view = 'transactions';
        });
    };
    
    $scope.backToAccounts = function() {
        $scope.view = 'accounts';
    };
    
    $scope.logout = function() {
        authService.logout();
        $location.path('/login');
    };
});