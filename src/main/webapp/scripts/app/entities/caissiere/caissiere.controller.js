'use strict';

angular.module('agenceDeVoyageApp')
    .controller('CaissiereController', function ($scope, Caissiere, ParseLinks) {
        $scope.caissieres = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Caissiere.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.caissieres = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Caissiere.get({id: id}, function(result) {
                $scope.caissiere = result;
                $('#deleteCaissiereConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Caissiere.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCaissiereConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.caissiere = {
                nom_caissiere: null,
                prenom_caissiere: null,
                tel_caissiere: null,
                adresse_caissiere: null,
                id: null
            };
        };
    });
