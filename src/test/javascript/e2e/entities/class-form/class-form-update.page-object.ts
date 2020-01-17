import { element, by, ElementFinder } from 'protractor';

export default class ClassFormUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.classForm.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  typeSelect: ElementFinder = element(by.css('select#class-form-type'));

  getPageTitle() {
    return this.pageTitle;
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
