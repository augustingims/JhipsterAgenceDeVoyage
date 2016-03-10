'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ChauffeurController', function ($scope, Chauffeur, ParseLinks) {
        $scope.chauffeurs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Chauffeur.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.chauffeurs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Chauffeur.get({id: id}, function(result) {
                $scope.chauffeur = result;
                $('#deleteChauffeurConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Chauffeur.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteChauffeurConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.chauffeur = {
                nom_chauffeur: null,
                prenom_chauffeur: null,
                tel_chauffeur: null,
                adresse_chauffeur: null,
                id: null
            };
        };
    });
