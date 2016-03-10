'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('secretaire', {
                parent: 'entity',
                url: '/secretaires',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.secretaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/secretaire/secretaires.html',
                        controller: 'SecretaireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('secretaire');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('secretaire.detail', {
                parent: 'entity',
                url: '/secretaire/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.secretaire.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/secretaire/secretaire-detail.html',
                        controller: 'SecretaireDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('secretaire');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Secretaire', function($stateParams, Secretaire) {
                        return Secretaire.get({id : $stateParams.id});
                    }]
                }
            })
            .state('secretaire.new', {
                parent: 'secretaire',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/secretaire/secretaire-dialog.html',
                        controller: 'SecretaireDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom_secretaire: null,
                                    prenom_secretaire: null,
                                    tel_secretaire: null,
                                    adresse_secretaire: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('secretaire', null, { reload: true });
                    }, function() {
                        $state.go('secretaire');
                    })
                }]
            })
            .state('secretaire.edit', {
                parent: 'secretaire',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/secretaire/secretaire-dialog.html',
                        controller: 'SecretaireDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Secretaire', function(Secretaire) {
                                return Secretaire.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('secretaire', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
