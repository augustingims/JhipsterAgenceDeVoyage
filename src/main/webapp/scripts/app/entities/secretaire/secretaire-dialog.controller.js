'use strict';

angular.module('agenceDeVoyageApp').controller('SecretaireDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Secretaire', 'Responsable', 'Client',
        function($scope, $stateParams, $modalInstance, $q, entity, Secretaire, Responsable, Client) {

        $scope.secretaire = entity;
        $scope.responsables = Responsable.query({filter: 'secretaire-is-null'});
        $q.all([$scope.secretaire.$promise, $scope.responsables.$promise]).then(function() {
            if (!$scope.secretaire.responsable.id) {
                return $q.reject();
            }
            return Responsable.get({id : $scope.secretaire.responsable.id}).$promise;
        }).then(function(responsable) {
            $scope.responsables.push(responsable);
        });
        $scope.clients = Client.query();
        $scope.load = function(id) {
            Secretaire.get({id : id}, function(result) {
                $scope.secretaire = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:secretaireUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.secretaire.id != null) {
                Secretaire.update($scope.secretaire, onSaveFinished);
            } else {
                Secretaire.save($scope.secretaire, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
