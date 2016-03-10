'use strict';

angular.module('agenceDeVoyageApp').controller('CaissiereDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Caissiere', 'Ticket',
        function($scope, $stateParams, $modalInstance, entity, Caissiere, Ticket) {

        $scope.caissiere = entity;
        $scope.tickets = Ticket.query();
        $scope.load = function(id) {
            Caissiere.get({id : id}, function(result) {
                $scope.caissiere = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:caissiereUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.caissiere.id != null) {
                Caissiere.update($scope.caissiere, onSaveFinished);
            } else {
                Caissiere.save($scope.caissiere, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
