'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ClientDetailController', function ($scope, $rootScope, $stateParams, entity, Client, Secretaire, Ticket) {
        $scope.client = entity;
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:clientUpdate', function(event, result) {
            $scope.client = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
