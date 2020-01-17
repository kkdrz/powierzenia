import { element, by, ElementFinder } from 'protractor';

export default class EntrustmentPlanUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.entrustmentPlan.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  academicYearInput: ElementFinder = element(by.css('input#entrustment-plan-academicYear'));
  semesterTypeSelect: ElementFinder = element(by.css('select#entrustment-plan-semesterType'));
  educationPlanSelect: ElementFinder = element(by.css('select#entrustment-plan-educationPlan'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAcademicYearInput(academicYear) {
    await this.academicYearInput.sendKeys(academicYear);
  }

  async getAcademicYearInput() {
    return this.academicYearInput.getAttribute('value');
  }

  async setSemesterTypeSelect(semesterType) {
    await this.semesterTypeSelect.sendKeys(semesterType);
  }

  async getSemesterTypeSelect() {
    return this.semesterTypeSelect.element(by.css('option:checked')).getText();
  }

  async semesterTypeSelectLastOption() {
    await this.semesterTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async educationPlanSelectLastOption() {
    await this.educationPlanSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async educationPlanSelectOption(option) {
    await this.educationPlanSelect.sendKeys(option);
  }

  getEducationPlanSelect() {
    return this.educationPlanSelect;
  }

  async getEducationPlanSelectedOption() {
    return this.educationPlanSelect.element(by.css('option:checked')).getText();
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
