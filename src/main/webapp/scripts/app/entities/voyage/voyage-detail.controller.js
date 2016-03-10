'use strict';

angular.module('agenceDeVoyageApp')
    .controller('VoyageDetailController', function ($scope, $rootScope, $stateParams, entity, Voyage, Ticket, Bus, Chauffeur) {
        $scope.voyage = entity;
        $scope.load = function (id) {
            Voyage.get({id: id}, function(result) {
                $scope.voyage = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:voyageUpdate', function(event, result) {
            $scope.voyage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
