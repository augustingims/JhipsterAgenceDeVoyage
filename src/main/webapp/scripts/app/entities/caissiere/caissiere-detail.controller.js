'use strict';

angular.module('agenceDeVoyageApp')
    .controller('CaissiereDetailController', function ($scope, $rootScope, $stateParams, entity, Caissiere, Ticket) {
        $scope.caissiere = entity;
        $scope.load = function (id) {
            Caissiere.get({id: id}, function(result) {
                $scope.caissiere = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:caissiereUpdate', function(event, result) {
            $scope.caissiere = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
