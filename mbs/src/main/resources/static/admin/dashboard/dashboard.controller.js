angular.module('adminDashboardApp', [])
.controller('DashboardController', ['$http', function($http) {
    var vm = this;
    vm.page = 0;
    vm.size = 10;
    vm.sort = 'accountId,asc';
    vm.accountFilter = '';
    vm.accounts = [];
    vm.totalPages = 0;

    vm.loadAccounts = function() {
        let params = {
            page: vm.page,
            size: vm.size,
            sort: vm.sort,
        };
        if (vm.accountFilter) {
            params.name = vm.accountFilter;
        }

        $http.get('/admin/accounts', { params: params })
            .then(function(response) {
                vm.accounts = response.data.content;
                vm.totalPages = response.data.totalPages;
            }, function(error) {
                console.error('Error fetching accounts:', error);
            });
    };

    vm.sortBy = function(field) {
        let [currentField, currentOrder] = vm.sort.split(',');
        if (currentField === field) {
            vm.sort = field + ',' + (currentOrder === 'asc' ? 'desc' : 'asc');
        } else {
            vm.sort = field + ',asc';
        }
        vm.loadAccounts();
    };

    vm.loadAccounts();
}]);
