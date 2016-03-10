'use strict';

angular.module('agenceDeVoyageApp')
    .controller('SecretaireController', function ($scope, Secretaire, ParseLinks) {
        $scope.secretaires = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Secretaire.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.secretaires = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Secretaire.get({id: id}, function(result) {
                $scope.secretaire = result;
                $('#deleteSecretaireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Secretaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSecretaireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.secretaire = {
                nom_secretaire: null,
                prenom_secretaire: null,
                tel_secretaire: null,
                adresse_secretaire: null,
                id: null
            };
        };
    });
