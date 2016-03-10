'use strict';

angular.module('agenceDeVoyageApp').controller('ChauffeurDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Chauffeur', 'Voyage',
        function($scope, $stateParams, $modalInstance, entity, Chauffeur, Voyage) {

        $scope.chauffeur = entity;
        $scope.voyages = Voyage.query();
        $scope.load = function(id) {
            Chauffeur.get({id : id}, function(result) {
                $scope.chauffeur = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:chauffeurUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.chauffeur.id != null) {
                Chauffeur.update($scope.chauffeur, onSaveFinished);
            } else {
                Chauffeur.save($scope.chauffeur, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
