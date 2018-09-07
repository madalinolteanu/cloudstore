/**
 * Created by Madalin on 9/3/2018.
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Routes = [
    {
        path: 'settings',
        component: SettingsComponent,
        data: {
            authorities: [],
            pageTitle: 'Settings Page'
        }
    },
    {
        path: 'dashboard/:reqType//:id',
        component: SettingsComponent,
        data: {
            authorities: [],
            pageTitle: 'Settings Page'
        }
    },
    {
        path: 'dashboard/:reqType/:wfUser/:id',
        component: SettingsComponent,
        data: {
            authorities: [],
            pageTitle: 'Banners'
        }
    }
];
