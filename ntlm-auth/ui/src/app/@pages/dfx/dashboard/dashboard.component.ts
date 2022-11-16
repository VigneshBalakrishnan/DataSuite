import { Component, OnInit } from '@angular/core';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { DfxUrl } from 'src/app/routes/name.routes';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.view.html',
})
export class DashboardComponent implements OnInit {
  config: any;
  public anlyticsURL: SafeResourceUrl;
  constructor(
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
    this.anlyticsURL = this.sanitizer.bypassSecurityTrustResourceUrl(DfxUrl.analyticsAppURL);
  }
}
