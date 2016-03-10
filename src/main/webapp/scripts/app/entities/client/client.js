'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('client', {
                parent: 'entity',
                url: '/clients',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.client.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/clients.html',
                        controller: 'ClientController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('client.detail', {
                parent: 'entity',
                url: '/client/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.client.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/client-detail.html',
                        controller: 'ClientDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Client', function($stateParams, Client) {
                        return Client.get({id : $stateParams.id});
                    }]
                }
            })
            .state('client.new', {
                parent: 'client',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    num_cni: null,
                                    nom_client: null,
                                    prenom_client: null,
                                    profession: null,
                                    date_delivrance: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('client');
                    })
                }]
            })
            .state('client.edit', {
                parent: 'client',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/client/client-dialog.html',
                        controller: 'ClientDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Client', function(Client) {
                                return Client.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('client', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
