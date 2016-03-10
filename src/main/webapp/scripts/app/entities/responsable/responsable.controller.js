'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ResponsableController', function ($scope, Responsable, ParseLinks) {
        $scope.responsables = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Responsable.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.responsables = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Responsable.get({id: id}, function(result) {
                $scope.responsable = result;
                $('#deleteResponsableConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Responsable.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteResponsableConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.responsable = {
                nom_responsable: null,
                prenom_responsable: null,
                tel_responsable: null,
                adresse_responsable: null,
                id: null
            };
        };
    });
