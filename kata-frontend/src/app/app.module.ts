import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenubarModule } from 'primeng/menubar';
import { CardModule } from 'primeng/card';
import { NgOptimizedImage, registerLocaleData } from '@angular/common';
import { SplitterModule } from "primeng/splitter";
import { ButtonModule } from 'primeng/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { SessionComponent } from './pages/session/session.component';
import { KataMenuComponent } from './pages/menu/kata-menu.component';
import { HttpClientModule } from '@angular/common/http';
import localeFr from '@angular/common/locales/fr';
registerLocaleData(localeFr);

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SessionComponent,
    KataMenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    CardModule,
    NgOptimizedImage,
    SplitterModule,
    ButtonModule,
    FormsModule,
    PasswordModule,
    ReactiveFormsModule,
    InputTextModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers:[{ provide: LOCALE_ID,useValue: 'fr-FR'}],
  bootstrap: [AppComponent]
})
export class AppModule { }
