'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('caissiere', {
                parent: 'entity',
                url: '/caissieres',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.caissiere.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/caissiere/caissieres.html',
                        controller: 'CaissiereController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('caissiere');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('caissiere.detail', {
                parent: 'entity',
                url: '/caissiere/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.caissiere.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/caissiere/caissiere-detail.html',
                        controller: 'CaissiereDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('caissiere');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Caissiere', function($stateParams, Caissiere) {
                        return Caissiere.get({id : $stateParams.id});
                    }]
                }
            })
            .state('caissiere.new', {
                parent: 'caissiere',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/caissiere/caissiere-dialog.html',
                        controller: 'CaissiereDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom_caissiere: null,
                                    prenom_caissiere: null,
                                    tel_caissiere: null,
                                    adresse_caissiere: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('caissiere', null, { reload: true });
                    }, function() {
                        $state.go('caissiere');
                    })
                }]
            })
            .state('caissiere.edit', {
                parent: 'caissiere',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/caissiere/caissiere-dialog.html',
                        controller: 'CaissiereDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Caissiere', function(Caissiere) {
                                return Caissiere.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('caissiere', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
