'use strict';

describe('Responsable Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockResponsable, MockSecretaire;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockResponsable = jasmine.createSpy('MockResponsable');
        MockSecretaire = jasmine.createSpy('MockSecretaire');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Responsable': MockResponsable,
            'Secretaire': MockSecretaire
        };
        createController = function() {
            $injector.get('$controller')("ResponsableDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:responsableUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
