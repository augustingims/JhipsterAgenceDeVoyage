'use strict';

describe('Caissiere Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCaissiere, MockTicket;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCaissiere = jasmine.createSpy('MockCaissiere');
        MockTicket = jasmine.createSpy('MockTicket');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Caissiere': MockCaissiere,
            'Ticket': MockTicket
        };
        createController = function() {
            $injector.get('$controller')("CaissiereDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:caissiereUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
