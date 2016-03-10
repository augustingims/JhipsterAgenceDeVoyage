'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('voyage', {
                parent: 'entity',
                url: '/voyages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.voyage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/voyage/voyages.html',
                        controller: 'VoyageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('voyage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('voyage.detail', {
                parent: 'entity',
                url: '/voyage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.voyage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/voyage/voyage-detail.html',
                        controller: 'VoyageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('voyage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Voyage', function($stateParams, Voyage) {
                        return Voyage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('voyage.new', {
                parent: 'voyage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/voyage/voyage-dialog.html',
                        controller: 'VoyageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    ville_depart: null,
                                    ville_arrivee: null,
                                    heure_depart: null,
                                    heure_arrivee: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('voyage', null, { reload: true });
                    }, function() {
                        $state.go('voyage');
                    })
                }]
            })
            .state('voyage.edit', {
                parent: 'voyage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/voyage/voyage-dialog.html',
                        controller: 'VoyageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Voyage', function(Voyage) {
                                return Voyage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('voyage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
