'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ChauffeurDetailController', function ($scope, $rootScope, $stateParams, entity, Chauffeur, Voyage) {
        $scope.chauffeur = entity;
        $scope.load = function (id) {
            Chauffeur.get({id: id}, function(result) {
                $scope.chauffeur = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:chauffeurUpdate', function(event, result) {
            $scope.chauffeur = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
