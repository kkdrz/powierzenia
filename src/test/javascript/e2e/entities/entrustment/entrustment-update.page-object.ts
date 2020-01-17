import { element, by, ElementFinder } from 'protractor';

export default class EntrustmentUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.entrustment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  hoursInput: ElementFinder = element(by.css('input#entrustment-hours'));
  hoursMultiplierInput: ElementFinder = element(by.css('input#entrustment-hoursMultiplier'));
  entrustmentPlanSelect: ElementFinder = element(by.css('select#entrustment-entrustmentPlan'));
  courseClassSelect: ElementFinder = element(by.css('select#entrustment-courseClass'));
  teacherSelect: ElementFinder = element(by.css('select#entrustment-teacher'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setHoursInput(hours) {
    await this.hoursInput.sendKeys(hours);
  }

  async getHoursInput() {
    return this.hoursInput.getAttribute('value');
  }

  async setHoursMultiplierInput(hoursMultiplier) {
    await this.hoursMultiplierInput.sendKeys(hoursMultiplier);
  }

  async getHoursMultiplierInput() {
    return this.hoursMultiplierInput.getAttribute('value');
  }

  async entrustmentPlanSelectLastOption() {
    await this.entrustmentPlanSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async entrustmentPlanSelectOption(option) {
    await this.entrustmentPlanSelect.sendKeys(option);
  }

  getEntrustmentPlanSelect() {
    return this.entrustmentPlanSelect;
  }

  async getEntrustmentPlanSelectedOption() {
    return this.entrustmentPlanSelect.element(by.css('option:checked')).getText();
  }

  async courseClassSelectLastOption() {
    await this.courseClassSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async courseClassSelectOption(option) {
    await this.courseClassSelect.sendKeys(option);
  }

  getCourseClassSelect() {
    return this.courseClassSelect;
  }

  async getCourseClassSelectedOption() {
    return this.courseClassSelect.element(by.css('option:checked')).getText();
  }

  async teacherSelectLastOption() {
    await this.teacherSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async teacherSelectOption(option) {
    await this.teacherSelect.sendKeys(option);
  }

  getTeacherSelect() {
    return this.teacherSelect;
  }

  async getTeacherSelectedOption() {
    return this.teacherSelect.element(by.css('option:checked')).getText();
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
