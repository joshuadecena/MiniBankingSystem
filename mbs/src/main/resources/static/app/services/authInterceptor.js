angular.module('bankingApp')
  .factory('authInterceptor', ['$q', '$injector', '$location', 
  function($q, $injector, $location) {
    return {
      request: function(config) {
        let authService = $injector.get('authService');
        
		if (!config.url.includes('/api/') || config.url.includes('/api/auth/')) {
		        config.withCredentials = true;
		        return config;
		}
		
		let token = authService.getAccessToken();
        
		if (token && authService.isTokenExpired(token)) {
		  console.warn('Token expired or about to expire, attempting refresh');
		  if (!config._retry) {
		    return authService.refreshToken()
		      .then(newToken => {
		        config.headers = config.headers || {};
		        config.headers.Authorization = 'Bearer ' + newToken;
		        config._retry = true;
		        return config;
		      })
		      .catch(err => {
		        authService.logout();
		        $location.path('/login');
		        return $q.reject(err);
		      });
		  }
		}
		
		if (token) {
		  config.headers = config.headers || {};
		  config.headers.Authorization = 'Bearer ' + token;
		  config.withCredentials = true;
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