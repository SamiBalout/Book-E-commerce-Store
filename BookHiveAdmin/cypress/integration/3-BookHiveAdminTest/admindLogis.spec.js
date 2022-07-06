const { closeSync } = require("fs")

describe('Admin Login', () => {
    it('Open Login Page', ()=> {
        cy.visit('https://bookhive-front-admin.herokuapp.com/admin/login')
    });

    it('Navigate to BookDashboard', ()=> {
        cy.visit('https://bookhive-front-admin.herokuapp.com/admin/bookdashboard')
        cy.get('.mat-button-wrapper').click()
        cy.get('#mat-input-0').type('william1')
        cy.get('#mat-input-1').type('adminpassword123')
    });

    it('Test Book Dashboard Functions', ()=> {
        cy.visit('https://bookhive-front-admin.herokuapp.com/admin/login')
        cy.get('#mat-input-0').type('william1')
        cy.get('#mat-input-1').type('adminpassword123')
        //Visit Book dashboard page
        cy.get('.mat-button-wrapper').click()

        //Update and Remove Buttons
        cy.get(':nth-child(5) > .cdk-column-update > .mat-focus-indicator > .mat-button-wrapper > .mat-icon')
        cy.get(':nth-child(5) > .cdk-column-remove > .mat-focus-indicator > .mat-button-wrapper > .mat-icon')
        cy.get(':nth-child(2) > .nav-link')

         //Add Book Expansion panel
        cy.get('#mat-expansion-panel-header-0').click()

        //Check Pageinator button
        cy.get('.mat-form-field-wrapper')
    });

    it('Nagivate to Customer Orders Page - Test Customer Orders Functions', ()=> {
        cy.visit('https://bookhive-front-admin.herokuapp.com/admin/login')
        cy.get('#mat-input-0').type('william1')
        cy.get('#mat-input-1').type('adminpassword123')

        cy.get('.mat-button-wrapper').click()
        cy.get(':nth-child(2) > .nav-link').click()

        //View Order Details Expansion panel
        cy.get('#mat-expansion-panel-header-1')

        //Check Status of order button
        cy.get(':nth-child(1) > .cdk-column-status > .mat-focus-indicator > .mat-button-wrapper')

        //Check Pageinator button
        cy.get('.mat-form-field-wrapper')

    });

    it('Test Logout Function', ()=> {
        cy.visit('https://bookhive-front-admin.herokuapp.com/admin/login')
        cy.get('#mat-input-0').type('william1')
        cy.get('#mat-input-1').type('adminpassword123')
        //Visit Book dashboard page
        cy.get('.mat-button-wrapper').click()

        //Logout function
        cy.get('.d-flex > .mat-focus-indicator').click()
        cy.get('.d-flex > .mat-focus-indicator')
 
    });


});