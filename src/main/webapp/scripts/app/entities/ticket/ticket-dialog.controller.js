'use strict';

angular.module('agenceDeVoyageApp').controller('TicketDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Ticket', 'Client', 'Caissiere', 'Voyage',
        function($scope, $stateParams, $modalInstance, $q, entity, Ticket, Client, Caissiere, Voyage) {

        $scope.ticket = entity;
        $scope.clients = Client.query({filter: 'ticket-is-null'});
        $q.all([$scope.ticket.$promise, $scope.clients.$promise]).then(function() {
            if (!$scope.ticket.client.id) {
                return $q.reject();
            }
            return Client.get({id : $scope.ticket.client.id}).$promise;
        }).then(function(client) {
            $scope.clients.push(client);
        });
        $scope.caissieres = Caissiere.query({filter: 'ticket-is-null'});
        $q.all([$scope.ticket.$promise, $scope.caissieres.$promise]).then(function() {
            if (!$scope.ticket.caissiere.id) {
                return $q.reject();
            }
            return Caissiere.get({id : $scope.ticket.caissiere.id}).$promise;
        }).then(function(caissiere) {
            $scope.caissieres.push(caissiere);
        });
        $scope.voyages = Voyage.query({filter: 'ticket-is-null'});
        $q.all([$scope.ticket.$promise, $scope.voyages.$promise]).then(function() {
            if (!$scope.ticket.voyage.id) {
                return $q.reject();
            }
            return Voyage.get({id : $scope.ticket.voyage.id}).$promise;
        }).then(function(voyage) {
            $scope.voyages.push(voyage);
        });
        $scope.load = function(id) {
            Ticket.get({id : id}, function(result) {
                $scope.ticket = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('agenceDeVoyageApp:ticketUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ticket.id != null) {
                Ticket.update($scope.ticket, onSaveFinished);
            } else {
                Ticket.save($scope.ticket, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
