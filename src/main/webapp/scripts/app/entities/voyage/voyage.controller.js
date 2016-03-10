'use strict';

angular.module('agenceDeVoyageApp')
    .controller('VoyageController', function ($scope, Voyage, ParseLinks) {
        $scope.voyages = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Voyage.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.voyages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Voyage.get({id: id}, function(result) {
                $scope.voyage = result;
                $('#deleteVoyageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Voyage.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteVoyageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.voyage = {
                date: null,
                ville_depart: null,
                ville_arrivee: null,
                heure_depart: null,
                heure_arrivee: null,
                id: null
            };
        };
    });
