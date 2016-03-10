'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Bus', function ($resource, DateUtils) {
        return $resource('api/buss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
