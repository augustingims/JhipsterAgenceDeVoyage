'use strict';

describe('Ticket Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTicket, MockClient, MockCaissiere, MockVoyage;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTicket = jasmine.createSpy('MockTicket');
        MockClient = jasmine.createSpy('MockClient');
        MockCaissiere = jasmine.createSpy('MockCaissiere');
        MockVoyage = jasmine.createSpy('MockVoyage');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Ticket': MockTicket,
            'Client': MockClient,
            'Caissiere': MockCaissiere,
            'Voyage': MockVoyage
        };
        createController = function() {
            $injector.get('$controller')("TicketDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:ticketUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
