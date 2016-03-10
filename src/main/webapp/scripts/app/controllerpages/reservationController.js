'use strict';

angular.module('agenceDeVoyageApp').controller('ReservationController',

        function($scope, Client,$state,$timeout) {

        $scope.client = {};

            var onSaveFinished = function (result) {
                $scope.$emit('agenceDeVoyageApp:clientReservation', result);
                $state.go("ok");
                $timeout(function() {
                    $state.go("reservation",null,{reload:true});
                }, 3000);
                $scope.client = {};
            };

        $scope.save = function () {
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveFinished);
            } else {
                $scope.client.reserver = true;
                Client.save($scope.client, onSaveFinished);
            }
        };

        });
