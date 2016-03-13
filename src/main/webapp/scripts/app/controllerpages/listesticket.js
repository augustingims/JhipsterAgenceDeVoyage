'use strict';

angular.module('agenceDeVoyageApp').controller('ListesticketController',

        function($scope,$http,$state,$timeout) {

            $scope.imprimes = [];

            $scope.loadAll = function() {
                $http.get('api/imprime').success(function (response) {
                    $scope.imprimes = response;
                }).error(function (reason) {
                    console.log(reason);
                });
            };
            $scope.loadAll()

            $scope.getRuban = function(k){
                if(k=='VIP'){
                return "price-ribbon "+"ribbon-red";
                }else{
                return "price-ribbon "+"ribbon-green";
                }
            };
            $scope.impression = function(id){

                $http.get("api/imprimer/"+id).success(function(response){
                    console.log("impression réussie");
                }).error(function(reason){
                    console.log(reason);
                });
                $state.go("ok");
                $timeout(function() {
                    $state.go("listesticket",{reload:true});
                }, 3000);
            }

        });
