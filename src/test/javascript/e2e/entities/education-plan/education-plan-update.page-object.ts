import { element, by, ElementFinder } from 'protractor';

export default class EducationPlanUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.educationPlan.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  startAcademicYearInput: ElementFinder = element(by.css('input#education-plan-startAcademicYear'));
  specializationSelect: ElementFinder = element(by.css('select#education-plan-specialization'));
  studiesLevelSelect: ElementFinder = element(by.css('select#education-plan-studiesLevel'));
  studiesTypeSelect: ElementFinder = element(by.css('select#education-plan-studiesType'));
  fieldOfStudySelect: ElementFinder = element(by.css('select#education-plan-fieldOfStudy'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setStartAcademicYearInput(startAcademicYear) {
    await this.startAcademicYearInput.sendKeys(startAcademicYear);
  }

  async getStartAcademicYearInput() {
    return this.startAcademicYearInput.getAttribute('value');
  }

  async setSpecializationSelect(specialization) {
    await this.specializationSelect.sendKeys(specialization);
  }

  async getSpecializationSelect() {
    return this.specializationSelect.element(by.css('option:checked')).getText();
  }

  async specializationSelectLastOption() {
    await this.specializationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setStudiesLevelSelect(studiesLevel) {
    await this.studiesLevelSelect.sendKeys(studiesLevel);
  }

  async getStudiesLevelSelect() {
    return this.studiesLevelSelect.element(by.css('option:checked')).getText();
  }

  async studiesLevelSelectLastOption() {
    await this.studiesLevelSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setStudiesTypeSelect(studiesType) {
    await this.studiesTypeSelect.sendKeys(studiesType);
  }

  async getStudiesTypeSelect() {
    return this.studiesTypeSelect.element(by.css('option:checked')).getText();
  }

  async studiesTypeSelectLastOption() {
    await this.studiesTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async fieldOfStudySelectLastOption() {
    await this.fieldOfStudySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async fieldOfStudySelectOption(option) {
    await this.fieldOfStudySelect.sendKeys(option);
  }

  getFieldOfStudySelect() {
    return this.fieldOfStudySelect;
  }

  async getFieldOfStudySelectedOption() {
    return this.fieldOfStudySelect.element(by.css('option:checked')).getText();
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
