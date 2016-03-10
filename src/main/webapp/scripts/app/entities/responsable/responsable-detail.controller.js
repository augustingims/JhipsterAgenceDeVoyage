'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ResponsableDetailController', function ($scope, $rootScope, $stateParams, entity, Responsable, Secretaire) {
        $scope.responsable = entity;
        $scope.load = function (id) {
            Responsable.get({id: id}, function(result) {
                $scope.responsable = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:responsableUpdate', function(event, result) {
            $scope.responsable = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
