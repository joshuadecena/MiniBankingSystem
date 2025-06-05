angular.module('bankingApp')
  .factory('authInterceptor', ['$q', '$injector', '$location', 
  function($q, $injector, $location) {
    return {
      request: function(config) {
		console.log('Intercepting request to:', config.url);
		console.log('Current token:', authService.getAccessToken());
		
        let authService = $injector.get('authService');
        let token = authService.getAccessToken();
        
        // Skip for non-API requests and auth endpoints
		if (!config.url.includes('/api/') || config.url.includes('/api/auth/')) {
		        config.withCredentials = true;
		        return config;
		}
        
        if (token) {
          config.headers = config.headers || {};
          config.headers.Authorization = 'Bearer ' + token;
		  
		  config.withCredentials = true;
		  
		  console.log('Attached headers:', config.headers);
        } else {
			console.warn('No token available for protected route:', config.url);
		}
        
        return config;
      },

      responseError: function(rejection) {
        let authService = $injector.get('authService');
        
        if (rejection.status === 401 && !rejection.config._retry) {
          rejection.config._retry = true;
          
          if (authService.isLoggedIn()) {
            return authService.refreshToken()
              .then(newToken => {
                let $http = $injector.get('$http');
                let newConfig = angular.copy(rejection.config);
                newConfig.headers = newConfig.headers || {};
                newConfig.headers.Authorization = 'Bearer ' + newToken;
                return $http(newConfig);
              })
              .catch(err => {
                authService.logout();
                $location.path('/login');
                return $q.reject(err);
              });
          }
          authService.logout();
          $location.path('/login');
        }
        
        return $q.reject(rejection);
      }
    };
  }])