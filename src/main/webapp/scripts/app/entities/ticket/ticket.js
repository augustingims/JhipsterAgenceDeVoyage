'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ticket', {
                parent: 'entity',
                url: '/ticket',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.ticket.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ticket/tickets.html',
                        controller: 'TicketController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ticket');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ticket.detail', {
                parent: 'entity',
                url: '/ticket/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'agenceDeVoyageApp.ticket.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ticket/ticket-detail.html',
                        controller: 'TicketDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ticket');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Ticket', function($stateParams, Ticket) {
                        return Ticket.get({id : $stateParams.id});
                    }]
                }
            })
            .state('ticket.new', {
                parent: 'ticket',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ticket/ticket-dialog.html',
                        controller: 'TicketDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nature: null,
                                    type: null,
                                    prix: null,
                                    numero: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('ticket', null, { reload: true });
                    }, function() {
                        $state.go('ticket');
                    })
                }]
            })
            .state('ticket.edit', {
                parent: 'ticket',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ticket/ticket-dialog.html',
                        controller: 'TicketDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Ticket', function(Ticket) {
                                return Ticket.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('ticket', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
