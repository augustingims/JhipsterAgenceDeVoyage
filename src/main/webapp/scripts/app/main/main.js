'use strict';

angular.module('agenceDeVoyageApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/landing.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sign', {
                parent: 'site',
                url: '/sign',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/sign.html',
                        controller:'LoginController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('contacts', {
                parent: 'site',
                url: '/contacts',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/contacts.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('setpassword', {
                parent: 'site',
                url: '/setpassword',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/setpassword.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('transport', {
                parent: 'site',
                url: '/transport',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/transport.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('events', {
                parent: 'site',
                url: '/events',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/events.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('travel', {
                parent: 'site',
                url: '/travel',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/travel.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('infos', {
                parent: 'site',
                url: '/infos',
                data: {
                    authorities: ['ROLE_SECRETAIRE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/infosclient.html',
                        controller:'InfosController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('demande', {
                parent: 'site',
                url: '/demande',
                data: {
                    authorities: ['ROLE_CAISSIERE','ROLE_SECRETAIRE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/demandeticket.html',
                        controller:'DemandeController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('history', {
                parent: 'site',
                url: '/history',
                data: {
                    authorities: ['ROLE_CAISSIERE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/history.html',
                        controller:'DemandeController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('tickets', {
                parent: 'site',
                url: '/tickets/{id}/{name}',
                data: {
                    authorities: ['ROLE_CAISSIERE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/tickets.html',
                        controller:'CreationTicketController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('listesticket', {
                parent: 'site',
                url: '/listesticket',
                data: {
                    authorities: ['ROLE_CAISSIERE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/listesticket.html',
                        controller:'ListesticketController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('ok', {
                parent: 'site',
                url: '/ok',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/ok.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('reservation', {
                parent: 'site',
                url: '/reservation',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/reservation.html',
                        controller:'ReservationController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('reserver', {
                parent: 'site',
                url: '/reserver',
                data: {
                    authorities: ['ROLE_CAISSIERE']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/reserverticket.html',
                        controller:'DemandeController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('404', {
                parent: 'site',
                url: '/404',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/pages/404.html'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            }).state('info', {
                parent: 'tickets',
                url: '/info/{bus}',
                data: {
                    authorities: ['ROLE_CAISSIERE']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/pages/infos.html',
                        controller: 'CreationTicketController',
                        size: ''
                    }).result.then(function(result) {
                            $state.go('tickets', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });
    });
