import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { CloudStoreComponent } from './cloudstore.component';
import { CloudStoreService } from './cloudstore.service';
import { cloudStoreRoute } from './cloudstore.route';
import {DirectoryComponent} from "../directory/directory.component";
import { FormsModule } from '@angular/forms';


const ENTITY_STATES = [...cloudStoreRoute];

@NgModule({
    imports: [CommonModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        FormsModule
    ],
    declarations: [DirectoryComponent, CloudStoreComponent],
    entryComponents: [DirectoryComponent],
    providers: [CloudStoreService]
})
export class CloudStoreModule {}
