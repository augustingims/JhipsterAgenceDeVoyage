'use strict';

describe('Chauffeur Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockChauffeur, MockVoyage;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockChauffeur = jasmine.createSpy('MockChauffeur');
        MockVoyage = jasmine.createSpy('MockVoyage');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Chauffeur': MockChauffeur,
            'Voyage': MockVoyage
        };
        createController = function() {
            $injector.get('$controller')("ChauffeurDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:chauffeurUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
