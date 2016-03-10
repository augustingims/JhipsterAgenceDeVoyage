'use strict';

describe('Secretaire Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSecretaire, MockResponsable, MockClient;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSecretaire = jasmine.createSpy('MockSecretaire');
        MockResponsable = jasmine.createSpy('MockResponsable');
        MockClient = jasmine.createSpy('MockClient');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Secretaire': MockSecretaire,
            'Responsable': MockResponsable,
            'Client': MockClient
        };
        createController = function() {
            $injector.get('$controller')("SecretaireDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'agenceDeVoyageApp:secretaireUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
