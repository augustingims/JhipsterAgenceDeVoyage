'use strict';

angular.module('agenceDeVoyageApp').controller('BusDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Bus', 'Voyage',
        function($scope, $stateParams, $modalInstance, entity, Bus, Voyage) {

        $scope.bus = entity;
        $scope.voyages = Voyage.query();
        $scope.load = function(id) {
            Bus.get({id : id}, function(result) {
                $scope.bus = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:busUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.bus.id != null) {
                Bus.update($scope.bus, onSaveFinished);
            } else {
                Bus.save($scope.bus, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
