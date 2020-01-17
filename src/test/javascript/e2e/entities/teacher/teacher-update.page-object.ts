import { element, by, ElementFinder } from 'protractor';

export default class TeacherUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.teacher.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  firstNameInput: ElementFinder = element(by.css('input#teacher-firstName'));
  lastNameInput: ElementFinder = element(by.css('input#teacher-lastName'));
  emailInput: ElementFinder = element(by.css('input#teacher-email'));
  hourLimitInput: ElementFinder = element(by.css('input#teacher-hourLimit'));
  pensumInput: ElementFinder = element(by.css('input#teacher-pensum'));
  agreedToAdditionalPensumInput: ElementFinder = element(by.css('input#teacher-agreedToAdditionalPensum'));
  typeSelect: ElementFinder = element(by.css('select#teacher-type'));
  allowedClassFormsSelect: ElementFinder = element(by.css('select#teacher-allowedClassForms'));
  knowledgeAreasSelect: ElementFinder = element(by.css('select#teacher-knowledgeAreas'));
  preferedCoursesSelect: ElementFinder = element(by.css('select#teacher-preferedCourses'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setFirstNameInput(firstName) {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput() {
    return this.firstNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName) {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput() {
    return this.lastNameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
  }

  async setHourLimitInput(hourLimit) {
    await this.hourLimitInput.sendKeys(hourLimit);
  }

  async getHourLimitInput() {
    return this.hourLimitInput.getAttribute('value');
  }

  async setPensumInput(pensum) {
    await this.pensumInput.sendKeys(pensum);
  }

  async getPensumInput() {
    return this.pensumInput.getAttribute('value');
  }

  getAgreedToAdditionalPensumInput() {
    return this.agreedToAdditionalPensumInput;
  }
  async setTypeSelect(type) {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect() {
    return this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption() {
    await this.typeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async allowedClassFormsSelectLastOption() {
    await this.allowedClassFormsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async allowedClassFormsSelectOption(option) {
    await this.allowedClassFormsSelect.sendKeys(option);
  }

  getAllowedClassFormsSelect() {
    return this.allowedClassFormsSelect;
  }

  async getAllowedClassFormsSelectedOption() {
    return this.allowedClassFormsSelect.element(by.css('option:checked')).getText();
  }

  async knowledgeAreasSelectLastOption() {
    await this.knowledgeAreasSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async knowledgeAreasSelectOption(option) {
    await this.knowledgeAreasSelect.sendKeys(option);
  }

  getKnowledgeAreasSelect() {
    return this.knowledgeAreasSelect;
  }

  async getKnowledgeAreasSelectedOption() {
    return this.knowledgeAreasSelect.element(by.css('option:checked')).getText();
  }

  async preferedCoursesSelectLastOption() {
    await this.preferedCoursesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async preferedCoursesSelectOption(option) {
    await this.preferedCoursesSelect.sendKeys(option);
  }

  getPreferedCoursesSelect() {
    return this.preferedCoursesSelect;
  }

  async getPreferedCoursesSelectedOption() {
    return this.preferedCoursesSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
