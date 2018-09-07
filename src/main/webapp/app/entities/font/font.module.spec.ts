import { FontModule } from './font.module';

describe('FontModule', () => {
    let fontModule: FontModule;

    beforeEach(() => {
        fontModule = new FontModule();
    });

    it('should create an instance', () => {
        expect(fontModule).toBeTruthy();
    });
});
