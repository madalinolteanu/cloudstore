/**
 * Created by Madalin on 9/3/2018.
 */
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { LoginComponent } from './login.component';

export const loginRoute: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        data: {
            authorities: [],
            pageTitle: 'Login Page'
        }
    }
];
