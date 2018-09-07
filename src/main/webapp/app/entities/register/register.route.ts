/**
 * Created by Madalin on 9/3/2018.
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { RegisterComponent } from './register.component';

export const registerRoute: Routes = [
    {
        path: 'register',
        component: RegisterComponent,
        data: {
            authorities: [],
            pageTitle: 'Register Page'
        }
    }
];
