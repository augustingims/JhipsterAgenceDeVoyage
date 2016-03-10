'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('responsable', {
                parent: 'entity',
                url: '/responsables',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.responsable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/responsable/responsables.html',
                        controller: 'ResponsableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('responsable');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('responsable.detail', {
                parent: 'entity',
                url: '/responsable/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.responsable.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/responsable/responsable-detail.html',
                        controller: 'ResponsableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('responsable');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Responsable', function($stateParams, Responsable) {
                        return Responsable.get({id : $stateParams.id});
                    }]
                }
            })
            .state('responsable.new', {
                parent: 'responsable',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/responsable/responsable-dialog.html',
                        controller: 'ResponsableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom_responsable: null,
                                    prenom_responsable: null,
                                    tel_responsable: null,
                                    adresse_responsable: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('responsable', null, { reload: true });
                    }, function() {
                        $state.go('responsable');
                    })
                }]
            })
            .state('responsable.edit', {
                parent: 'responsable',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/responsable/responsable-dialog.html',
                        controller: 'ResponsableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Responsable', function(Responsable) {
                                return Responsable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('responsable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
