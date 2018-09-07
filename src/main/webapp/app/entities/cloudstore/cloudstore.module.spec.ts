import { CloudStoreModule } from './cloudstore.module';

describe('CloudStoreModule', () => {
    let cloudstoreModule: CloudStoreModule;

    beforeEach(() => {
        cloudstoreModule = new CloudStoreModule();
    });

    it('should create an instance', () => {
        expect(cloudstoreModule).toBeTruthy();
    });
});
