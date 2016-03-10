'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('chauffeur', {
                parent: 'entity',
                url: '/chauffeurs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.chauffeur.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/chauffeur/chauffeurs.html',
                        controller: 'ChauffeurController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('chauffeur');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('chauffeur.detail', {
                parent: 'entity',
                url: '/chauffeur/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.chauffeur.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/chauffeur/chauffeur-detail.html',
                        controller: 'ChauffeurDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('chauffeur');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Chauffeur', function($stateParams, Chauffeur) {
                        return Chauffeur.get({id : $stateParams.id});
                    }]
                }
            })
            .state('chauffeur.new', {
                parent: 'chauffeur',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/chauffeur/chauffeur-dialog.html',
                        controller: 'ChauffeurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom_chauffeur: null,
                                    prenom_chauffeur: null,
                                    tel_chauffeur: null,
                                    adresse_chauffeur: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('chauffeur', null, { reload: true });
                    }, function() {
                        $state.go('chauffeur');
                    })
                }]
            })
            .state('chauffeur.edit', {
                parent: 'chauffeur',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/chauffeur/chauffeur-dialog.html',
                        controller: 'ChauffeurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Chauffeur', function(Chauffeur) {
                                return Chauffeur.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('chauffeur', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
