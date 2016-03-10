'use strict';

describe('Voyage Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockVoyage, MockTicket, MockBus, MockChauffeur;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockVoyage = jasmine.createSpy('MockVoyage');
        MockTicket = jasmine.createSpy('MockTicket');
        MockBus = jasmine.createSpy('MockBus');
        MockChauffeur = jasmine.createSpy('MockChauffeur');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Voyage': MockVoyage,
            'Ticket': MockTicket,
            'Bus': MockBus,
            'Chauffeur': MockChauffeur
        };
        createController = function() {
            $injector.get('$controller')("VoyageDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:voyageUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
