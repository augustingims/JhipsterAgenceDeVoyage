'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Client', function ($resource, DateUtils) {
        return $resource('api/clients/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_delivrance = DateUtils.convertLocaleDateFromServer(data.date_delivrance);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_delivrance = DateUtils.convertLocaleDateToServer(data.date_delivrance);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_delivrance = DateUtils.convertLocaleDateToServer(data.date_delivrance);
                    return angular.toJson(data);
                }
            }
        });
    });
