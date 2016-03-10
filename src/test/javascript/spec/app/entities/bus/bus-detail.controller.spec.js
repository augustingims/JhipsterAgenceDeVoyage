'use strict';

describe('Bus Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBus, MockVoyage;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBus = jasmine.createSpy('MockBus');
        MockVoyage = jasmine.createSpy('MockVoyage');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Bus': MockBus,
            'Voyage': MockVoyage
        };
        createController = function() {
            $injector.get('$controller')("BusDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:busUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
