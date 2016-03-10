'use strict';

angular.module('agenceDeVoyageApp').controller('ResponsableDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Responsable', 'Secretaire',
        function($scope, $stateParams, $modalInstance, entity, Responsable, Secretaire) {

        $scope.responsable = entity;
        $scope.secretaires = Secretaire.query();
        $scope.load = function(id) {
            Responsable.get({id : id}, function(result) {
                $scope.responsable = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:responsableUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.responsable.id != null) {
                Responsable.update($scope.responsable, onSaveFinished);
            } else {
                Responsable.save($scope.responsable, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
