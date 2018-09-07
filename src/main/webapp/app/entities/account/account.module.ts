import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountComponent } from './account.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
    imports: [CommonModule, HttpClientModule],
    declarations: [AccountComponent]
})
export class AccountModule {}
