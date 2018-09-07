import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { LicSharedLibsModule, LicSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import {DeleteComponent} from "./modals/delete/delete-modal";
import {ShareComponent} from "./modals/share/share-modal";
import {MoveComponent} from "./modals/move/move-modal";

@NgModule({
    imports: [LicSharedLibsModule, LicSharedCommonModule],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DeleteComponent,
        ShareComponent,
        MoveComponent
    ],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [
        JhiLoginModalComponent,
        DeleteComponent,
        ShareComponent,
        MoveComponent
    ],
    exports: [
        LicSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DeleteComponent,
        ShareComponent,
        MoveComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LicSharedModule {}
