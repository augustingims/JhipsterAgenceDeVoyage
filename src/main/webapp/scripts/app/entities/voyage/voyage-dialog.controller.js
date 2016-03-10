'use strict';

angular.module('agenceDeVoyageApp').controller('VoyageDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Voyage', 'Ticket', 'Bus', 'Chauffeur',
        function($scope, $stateParams, $modalInstance, $q, entity, Voyage, Ticket, Bus, Chauffeur) {

        $scope.voyage = entity;
        $scope.tickets = Ticket.query();
        $scope.buss = Bus.query({filter: 'voyage-is-null'});
        $q.all([$scope.voyage.$promise, $scope.buss.$promise]).then(function() {
            if (!$scope.voyage.bus.id) {
                return $q.reject();
            }
            return Bus.get({id : $scope.voyage.bus.id}).$promise;
        }).then(function(bus) {
            $scope.buss.push(bus);
        });
        $scope.chauffeurs = Chauffeur.query({filter: 'voyage-is-null'});
        $q.all([$scope.voyage.$promise, $scope.chauffeurs.$promise]).then(function() {
            if (!$scope.voyage.chauffeur.id) {
                return $q.reject();
            }
            return Chauffeur.get({id : $scope.voyage.chauffeur.id}).$promise;
        }).then(function(chauffeur) {
            $scope.chauffeurs.push(chauffeur);
        });
        $scope.load = function(id) {
            Voyage.get({id : id}, function(result) {
                $scope.voyage = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:voyageUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.voyage.id != null) {
                Voyage.update($scope.voyage, onSaveFinished);
            } else {
                Voyage.save($scope.voyage, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
