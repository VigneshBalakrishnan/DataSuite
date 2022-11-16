import { Component, OnInit } from '@angular/core';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';
import { DfxUrl } from 'src/app/routes/name.routes';
@Component({
  selector: 'app-overview',
  templateUrl: './overview.view.html'
})
export class OverviewComponent implements OnInit {
  config: any;
  public analyticsURL: SafeResourceUrl;

  constructor(
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
    this.analyticsURL = this.sanitizer.bypassSecurityTrustResourceUrl(DfxUrl.analyticsAppURL);
  }
}
