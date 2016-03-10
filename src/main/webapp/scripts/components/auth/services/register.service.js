'use strict';

angular.module('agenceDeVoyageApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


