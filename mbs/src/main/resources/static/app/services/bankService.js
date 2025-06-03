angular.module('bankingApp')
.factory('bankService', function($http, $q) {
    // In a real app, these would make HTTP calls to your backend
    return {
        getAccount: function(accountId) {
            return $q(function(resolve) {
                // Mock data
                resolve({
                    accountNumber: accountId,
                    balance: 5000.00,
                    userId: 'user_' + accountId,
                    name: 'John Doe',
                    email: 'john@example.com',
                    address: '123 Main St',
                    dateOfBirth: new Date(1985, 5, 15)
                });
            });
        },
        
        getTransactions: function(accountId) {
            return $q(function(resolve) {
                // Mock data
                var transactions = [
                    {
                        date: new Date(),
                        description: 'Initial deposit',
                        amount: 5000.00,
                        balance: 5000.00
                    },
                    {
                        date: new Date(Date.now() - 86400000),
                        description: 'Grocery store',
                        amount: -125.50,
                        balance: 4874.50
                    }
                ];
                resolve(transactions);
            });
        },
        
        transferFunds: function(fromAccount, toAccount, amount) {
            return $q(function(resolve) {
                // Mock successful transfer
                resolve({
                    success: true,
                    newBalance: 4874.50 - amount
                });
            });
        },
        
        getAllAccounts: function() {
            return $q(function(resolve) {
                // Mock data
                resolve([
                    {
                        userId: 'user_12345',
                        accountId: '12345',
                        name: 'John Doe',
                        email: 'john@example.com',
                        address: '123 Main St',
                        dateOfBirth: new Date(1985, 5, 15),
                        balance: 5000.00
                    },
                    {
                        userId: 'user_67890',
                        accountId: '67890',
                        name: 'Jane Smith',
                        email: 'jane@example.com',
                        address: '456 Oak Ave',
                        dateOfBirth: new Date(1990, 2, 22),
                        balance: 7500.00
                    }
                ]);
            });
        }
    };
});