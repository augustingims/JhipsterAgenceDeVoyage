'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Chauffeur', function ($resource, DateUtils) {
        return $resource('api/chauffeurs/:id', {}, {
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
