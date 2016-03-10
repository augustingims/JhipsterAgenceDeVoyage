'use strict';

angular.module('agenceDeVoyageApp')
    .controller('NavbarController', function ($scope, $location, $http, $rootScope,$state, Auth, Principal, ENV,$interval) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        $rootScope.$on('agenceDeVoyageApp:removeinfos', function(event, response) {
            $scope.nbresitem -= 1;
        });

        $rootScope.$on('agenceDeVoyageApp:infosvide', function(event, response) {
            $scope.nbresitem = 0;
        });

        $rootScope.$on('agenceDeVoyageApp:infosclient', function(event, response) {
            $scope.nbresitem += 1;
        });
        $interval(function () {
            $http.get('api/nbreclients').success(function(response){
                $scope.nbresitem = response;
            }).error(function(reason){
                console.log(reason);
            });
        },1000);

        $rootScope.$on('agenceDeVoyageApp:clientReservation', function(event, response) {
            $scope.nbrereserver += 1;
        });

        $interval(function () {
            $http.get('api/nbrereserver').success(function(response){
                $scope.nbrereserver = response;
            }).error(function(reason){
                console.log(reason);
            });
        },1000);

        var $listItems = $( '#mainmenu > ul > li' ),
            $menuItems = $listItems.children( 'a' ),
            $body = $( 'div' ),
            current = -1;

        var init = function() {
            $menuItems.on( 'click', open );
            $listItems.on( 'click', function( event ) { event.stopPropagation(); } );
        };

        var open = function ( event ) {

            var $item = $( event.currentTarget ).parent( 'li.has-submenu' ),
                idx = $item.index();
            if($item.length != 0){
                if( current !== -1 ) {
                    $listItems.eq( current ).removeClass( 'mainmenu-open' );
                }

                if( current === idx ) {
                    $item.removeClass( 'mainmenu-open' );
                    current = -1;
                }
                else {
                    $item.addClass( 'mainmenu-open' );
                    current = idx;
                    $body.off( 'click' ).on( 'click', close );
                }
                return false;
            }
            else window.location = $item.find('a').attr('href');
        }

        var close = function ( event ) {
            $listItems.eq( current ).removeClass( 'mainmenu-open' );
            current = -1;
        }
        init();
    });
