angular.module('bankingApp')
.factory('BankService', function($q) {
    // Mock data - replace with real API calls later
    var accounts = {
        'cust1234': { balance: 1000, owner: 'customer1' },
        'cust5678': { balance: 2500, owner: 'customer2' },
        'admin001': { balance: 5000, owner: 'admin' }
    };
    
    var transactions = {
        'cust1234': [
            { id: 1, date: '2023-01-01', description: 'Initial deposit', amount: 1000, balance: 1000 },
            { id: 2, date: '2023-01-15', description: 'Transfer to cust5678', amount: -200, balance: 800 }
        ],
        'cust5678': [
            { id: 1, date: '2023-01-01', description: 'Initial deposit', amount: 2000, balance: 2000 },
            { id: 2, date: '2023-01-15', description: 'Transfer from cust1234', amount: 200, balance: 2200 }
        ],
        'admin001': [
            { id: 1, date: '2023-01-01', description: 'System account', amount: 5000, balance: 5000 }
        ]
    };
    
    return {
        getBalance: function(accountNumber) {
            return $q(function(resolve) {
                setTimeout(function() {
                    resolve({
                        balance: accounts[accountNumber] ? accounts[accountNumber].balance : 0,
                        accountNumber: accountNumber
                    });
                }, 300);
            });
        },
        
        transferFunds: function(fromAccount, toAccount, amount, description) {
            return $q(function(resolve, reject) {
                setTimeout(function() {
                    if (!accounts[fromAccount] || !accounts[toAccount]) {
                        reject('Invalid account number');
                        return;
                    }
                    
                    if (accounts[fromAccount].balance < amount) {
                        reject('Insufficient funds');
                        return;
                    }
                    
                    if (amount <= 0) {
                        reject('Amount must be positive');
                        return;
                    }
                    
                    // Update balances
                    accounts[fromAccount].balance -= amount;
                    accounts[toAccount].balance += amount;
                    
                    // Create transactions
                    var date = new Date().toISOString().split('T')[0];
                    var fromTx = {
                        id: transactions[fromAccount].length + 1,
                        date: date,
                        description: description || ('Transfer to ' + toAccount),
                        amount: -amount,
                        balance: accounts[fromAccount].balance
                    };
                    
                    var toTx = {
                        id: transactions[toAccount].length + 1,
                        date: date,
                        description: description || ('Transfer from ' + fromAccount),
                        amount: amount,
                        balance: accounts[toAccount].balance
                    };
                    
                    transactions[fromAccount].push(fromTx);
                    transactions[toAccount].push(toTx);
                    
                    resolve({
                        success: true,
                        newBalance: accounts[fromAccount].balance
                    });
                }, 500);
            });
        },
        
        getTransactions: function(accountNumber) {
            return $q(function(resolve) {
                setTimeout(function() {
                    resolve(transactions[accountNumber] || []);
                }, 300);
            });
        },
        
        getAllAccounts: function() {
            return $q(function(resolve) {
                setTimeout(function() {
                    resolve(accounts);
                }, 300);
            });
        }
    };
});