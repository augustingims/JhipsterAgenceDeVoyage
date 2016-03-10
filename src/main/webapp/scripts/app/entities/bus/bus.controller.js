'use strict';

angular.module('agenceDeVoyageApp')
    .controller('BusController', function ($scope, Bus, ParseLinks) {
        $scope.buss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Bus.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.buss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Bus.get({id: id}, function(result) {
                $scope.bus = result;
                $('#deleteBusConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bus.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bus = {
                nom_bus: null,
                immatriculation: null,
                marque: null,
                id: null
            };
        };
    });
