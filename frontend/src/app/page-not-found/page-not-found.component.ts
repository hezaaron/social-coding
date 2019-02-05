import { Component, OnInit } from '@angular/core';

@Component( {
	selector: 'app-page-not-found',
	template: `
  	<p>
			404 - Sorry page not found.
		</p>
  `
} )
export class PageNotFoundComponent implements OnInit {

	constructor() { }

	ngOnInit() {
	}

}
