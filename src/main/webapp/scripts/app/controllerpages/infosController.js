'use strict';

angular.module('agenceDeVoyageApp').controller('InfosController',

        function($scope, Client,$state,$timeout) {

        $scope.client = {};

            var onSaveFinished = function (result) {
                $scope.$emit('agenceDeVoyageApp:clientUpdate', result);
                $scope.$emit('agenceDeVoyageApp:infosclient', result);
                $state.go("ok");
                $timeout(function() {
                    $state.go("infos",{reload:true});
                }, 3000);
                $scope.client = {};
            };

        $scope.save = function () {
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveFinished);
            } else {
                $scope.client.demande = true;
                Client.save($scope.client, onSaveFinished);
            }
        };

        });
