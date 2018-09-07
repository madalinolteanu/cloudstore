/**
 * Created by Madalin on 9/3/2018.
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';
import { CloudStoreComponent } from './cloudstore.component';
import { JhiPaginationUtil } from 'ng-jhipster';

export const cloudStoreRoute: Routes = [
    {
        path: 'dashboard',
        component: CloudStoreComponent,
        data: {
            authorities: [],
            pageTitle: 'CloudStore'
        }
    }
];
