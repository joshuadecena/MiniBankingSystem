angular.module('bankingApp')
.controller('CustomerController', function($scope, $location, authService, bankService) {
    $scope.view = 'balance';
    $scope.account = {};
    $scope.transactions = [];
    $scope.transferData = {
        toAccount: '',
        amount: 0
    };
    
    // Load account data
    bankService.getAccount(authService.getCurrentUser().accountId)
    .then(function(account) {
        $scope.account = account;
    });
    
    // Load transactions
    bankService.getTransactions(authService.getCurrentUser().accountId)
    .then(function(transactions) {
        $scope.transactions = transactions;
    });
    
    $scope.showBalance = function() {
        $scope.view = 'balance';
    };
    
    $scope.showTransfer = function() {
        $scope.view = 'transfer';
    };
    
    $scope.showHistory = function() {
        $scope.view = 'history';
    };
    
    $scope.transferFunds = function() {
        bankService.transferFunds(
            authService.getCurrentUser().accountId,
            $scope.transferData.toAccount,
            $scope.transferData.amount
        ).then(function(response) {
            $scope.transferMessage = 'Transfer successful!';
            $scope.transferSuccess = true;
            $scope.account.balance = response.newBalance;
            $scope.transferData = { toAccount: '', amount: 0 };
        }, function(error) {
            $scope.transferMessage = 'Transfer failed: ' + error;
            $scope.transferSuccess = false;
        });
    };
    
    $scope.logout = function() {
        authService.logout();
        $location.path('/login');
    };
});