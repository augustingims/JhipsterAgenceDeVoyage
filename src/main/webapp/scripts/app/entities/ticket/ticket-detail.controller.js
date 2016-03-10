'use strict';

angular.module('agenceDeVoyageApp')
    .controller('TicketDetailController', function ($scope, $rootScope, $stateParams, entity, Ticket, Client, Caissiere, Voyage) {
        $scope.ticket = entity;
        $scope.load = function (id) {
            Ticket.get({id: id}, function(result) {
                $scope.ticket = result;
            });
        };
        var unsubscribe = $rootScope.$on('agenceDeVoyageApp:ticketUpdate', function(event, result) {
            $scope.ticket = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
