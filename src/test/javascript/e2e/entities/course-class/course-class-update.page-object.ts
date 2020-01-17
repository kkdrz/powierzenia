import { element, by, ElementFinder } from 'protractor';

export default class CourseClassUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.courseClass.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  hoursInput: ElementFinder = element(by.css('input#course-class-hours'));
  formSelect: ElementFinder = element(by.css('select#course-class-form'));
  courseSelect: ElementFinder = element(by.css('select#course-class-course'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setHoursInput(hours) {
    await this.hoursInput.sendKeys(hours);
  }

  async getHoursInput() {
    return this.hoursInput.getAttribute('value');
  }

  async setFormSelect(form) {
    await this.formSelect.sendKeys(form);
  }

  async getFormSelect() {
    return this.formSelect.element(by.css('option:checked')).getText();
  }

  async formSelectLastOption() {
    await this.formSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async courseSelectLastOption() {
    await this.courseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async courseSelectOption(option) {
    await this.courseSelect.sendKeys(option);
  }

  getCourseSelect() {
    return this.courseSelect;
  }

  async getCourseSelectedOption() {
    return this.courseSelect.element(by.css('option:checked')).getText();
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
