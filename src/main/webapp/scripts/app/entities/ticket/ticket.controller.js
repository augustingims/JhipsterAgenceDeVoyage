'use strict';

angular.module('agenceDeVoyageApp')
    .controller('TicketController', function ($scope, Ticket, ParseLinks) {
        $scope.tickets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Ticket.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tickets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Ticket.get({id: id}, function(result) {
                $scope.ticket = result;
                $('#deleteTicketConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ticket.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTicketConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ticket = {
                nature: null,
                type: null,
                prix: null,
                numero: null,
                id: null
            };
        };
    });
