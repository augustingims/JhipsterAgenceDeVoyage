'use strict';

angular.module('agenceDeVoyageApp').controller('DemandeController',

        function($scope,$http) {
            $scope.clients = [];
            $scope.reserver = [];
            $scope.listes = [];
            $scope.loadAll = function() {
               $http.get("api/getallclients").success(function(response){
                   $scope.clients = response;
               }).error(function(reason){
                   console.log(reason);
               });
                $http.get("api/getallclientsreserver").success(function(response){
                   $scope.reserver = response;
               }).error(function(reason){
                   console.log(reason);
               });
                $http.get("api/getallclientsticket").success(function(response){
                   $scope.listes = response;
               }).error(function(reason){
                   console.log(reason);
               })
            };
            $scope.loadAll();

        });
