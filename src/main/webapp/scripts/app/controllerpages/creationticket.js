'use strict';

angular.module('agenceDeVoyageApp').controller('CreationTicketController',

        function($scope,Client,Ticket,Voyage,$stateParams,$http,$timeout,$state,Imprime) {

        $scope.name_client = $stateParams.name;
        $scope.namebus = $stateParams.bus;
        $scope.allbus = [];
        $scope.allchauffeur = [];
        $scope.ticketclient = {
            "id": null,
            "nature": null,
            "nom_caissiere": null,
            "numero": null,
            "prix": null,
            "type": null
        };
        $scope.voyageclient = {
            "date": null,
            "heure_arrivee": null,
            "heure_depart": null,
            "id": null,
            "nom_bus": null,
            "nom_chauffeur": null,
            "ville_arrivee": null,
            "ville_depart": null
        };
        $scope.imprime =  {
                "client": null,
                "date": null,
                "heure_depart": null,
                "id": null,
                "nature": null,
                "nom_bus": null,
                "numero": null,
                "prix": null,
                "ville_arrivee": null,
                "ville_depart": null
            };
        $scope.loadAll = function(){
            $http.get("api/getAllChauffeur").success(function(response){
                $scope.allchauffeur = response;
            }).error(function(reason){
                console.log(reason);
            });
            $http.get("api/getAllBus").success(function(response){
                $scope.allbus = response;
            }).error(function(reason){
                console.log(reason);
            })
        };
        $scope.loadAll();
        $scope.setTicket = function(){
            $http.put("api/setTicket/"+$stateParams.id).success(function(response){
                console.log("set ticket OK");
                $scope.$emit('agenceDeVoyageApp:removeinfos', response);
            }).error(function(reason){
                console.log(reason);
            })
        };
            var onSaveAdFinished = function (result) {
                $scope.setTicket();
                $state.go("ok");
                $timeout(function() {
                    $state.go("listesticket",{reload:true});
                }, 3000);
                $scope.ticketclient = {};
            };

            var onSaveImgFinished = function (result) {
                $scope.$emit('agenceDeVoyageApp:voyage', result);
                $scope.voyageclient = {};
            };
            var onSaveFinishedImprime = function (result) {
                $scope.$emit('agenceDeVoyageApp:imprime', result);
            };

            $scope.save = function () {

                if ($scope.ticketclient.id != null && $scope.voyageclient.id != null) {
                    Ticket.update($scope.ticketclient, onSaveAdFinished);
                    Voyage.update($scope.voyageclient, onSaveImgFinished);
                } else {
                    $scope.imprime.client = $stateParams.name;
                    $scope.imprime.date = $scope.voyageclient.date;
                    $scope.imprime.heure_depart = $scope.voyageclient.heure_depart;
                    $scope.imprime.nature = $scope.ticketclient.nature;
                    $scope.imprime.nom_bus = $scope.voyageclient.nom_bus;
                    $scope.imprime.numero = $scope.ticketclient.numero;
                    $scope.imprime.prix = $scope.ticketclient.prix;
                    $scope.imprime.ville_arrivee = $scope.voyageclient.date;
                    $scope.imprime.ville_depart = $scope.voyageclient.date;
                    Imprime.save($scope.imprime,onSaveFinishedImprime);
                    Ticket.save($scope.ticketclient, onSaveAdFinished);
                    Voyage.save($scope.voyageclient, onSaveImgFinished);

                }


            };
            $scope.place = 0;
            $scope.limit = function(nombus){
                $http.get("api/getplace/"+nombus).success(function(response){
                    $scope.place = response;
                    $scope.buss = {bus:nombus};
                    if($scope.place < 2){
                        console.log("Place libre");
                    }else{
                        $state.go('info',$scope.buss,{ reload: true });
                    }
                }).error(function(reason){
                    console.log(reason);
                });
            }

        });
