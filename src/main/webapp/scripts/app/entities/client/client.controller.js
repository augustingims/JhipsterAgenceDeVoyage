'use strict';

angular.module('agenceDeVoyageApp')
    .controller('ClientController', function ($scope, Client, ParseLinks) {
        $scope.clients = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Client.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clients = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#deleteClientConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClientConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.client = {
                num_cni: null,
                nom_client: null,
                prenom_client: null,
                profession: null,
                date_delivrance: null,
                id: null
            };
        };
    });
