angular.module('bankingApp')
.factory('AuthService', ['$http', '$q', function($http, $q) {
    var authenticated = false;

    return {
        login: function(username, password) {
            return $http({
                method: 'POST',
                url: '/perform_login',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
            }).then(function(response) {
                authenticated = true;
                return response.data;
            }).catch(function(error) {
                authenticated = false;
                return $q.reject("Invalid username or password");
            });
        },

        logout: function() {
            return $http.post('/perform_logout').then(function() {
                authenticated = false;
            });
        },

        isAuthenticated: function() {
            return authenticated;
        }
    };
}]);
