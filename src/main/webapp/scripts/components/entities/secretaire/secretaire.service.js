'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Secretaire', function ($resource, DateUtils) {
        return $resource('api/secretaires/:id', {}, {
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
