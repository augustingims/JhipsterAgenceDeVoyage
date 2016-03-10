'use strict';

angular.module('agenceDeVoyageApp')
    .controller('BusDetailController', function ($scope, $rootScope, $stateParams, entity, Bus, Voyage) {
        $scope.bus = entity;
        $scope.load = function (id) {
            Bus.get({id: id}, function(result) {
                $scope.bus = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:busUpdate', function(event, result) {
            $scope.bus = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
