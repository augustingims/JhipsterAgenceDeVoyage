'use strict';

angular.module('agenceDeVoyageApp').controller('ClientDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Client', 'Secretaire', 'Ticket',
        function($scope, $stateParams, $modalInstance, $q, entity, Client, Secretaire, Ticket) {

        $scope.client = entity;
        $scope.secretaires = Secretaire.query({filter: 'client-is-null'});
        $q.all([$scope.client.$promise, $scope.secretaires.$promise]).then(function() {
            if (!$scope.client.secretaire.id) {
                return $q.reject();
            }
            return Secretaire.get({id : $scope.client.secretaire.id}).$promise;
        }).then(function(secretaire) {
            $scope.secretaires.push(secretaire);
        });
        $scope.tickets = Ticket.query();
        $scope.load = function(id) {
            Client.get({id : id}, function(result) {
                $scope.client = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:clientUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.client.id != null) {
                Client.update($scope.client, onSaveFinished);
            } else {
                Client.save($scope.client, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
