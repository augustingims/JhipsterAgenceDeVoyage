'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bus', {
                parent: 'entity',
                url: '/buss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.bus.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bus/buss.html',
                        controller: 'BusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bus.detail', {
                parent: 'entity',
                url: '/bus/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.bus.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bus/bus-detail.html',
                        controller: 'BusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Bus', function($stateParams, Bus) {
                        return Bus.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bus.new', {
                parent: 'bus',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bus/bus-dialog.html',
                        controller: 'BusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom_bus: null,
                                    immatriculation: null,
                                    marque: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bus', null, { reload: true });
                    }, function() {
                        $state.go('bus');
                    })
                }]
            })
            .state('bus.edit', {
                parent: 'bus',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bus/bus-dialog.html',
                        controller: 'BusDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bus', function(Bus) {
                                return Bus.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bus', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
