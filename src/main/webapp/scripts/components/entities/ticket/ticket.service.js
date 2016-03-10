'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Ticket', function ($resource, DateUtils) {
        return $resource('api/tickets/:id', {}, {
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
