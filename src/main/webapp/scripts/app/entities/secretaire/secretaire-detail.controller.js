'use strict';

angular.module('agenceDeVoyageApp')
    .controller('SecretaireDetailController', function ($scope, $rootScope, $stateParams, entity, Secretaire, Responsable, Client) {
        $scope.secretaire = entity;
        $scope.load = function (id) {
            Secretaire.get({id: id}, function(result) {
                $scope.secretaire = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:secretaireUpdate', function(event, result) {
            $scope.secretaire = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
