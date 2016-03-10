 'use strict';

angular.module('agenceDeVoyageApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-agenceDeVoyageApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-agenceDeVoyageApp-params')});
                }
                return response;
            }
        };
    });
