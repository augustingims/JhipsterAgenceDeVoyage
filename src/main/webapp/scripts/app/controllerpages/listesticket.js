'use strict';

angular.module('agenceDeVoyageApp').controller('ListesticketController',

        function($scope,$http) {

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

        });
