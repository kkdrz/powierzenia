import { element, by, ElementFinder } from 'protractor';

export default class KnowledgeAreaUpdatePage {
  pageTitle: ElementFinder = element(by.id('powierzeniaApp.knowledgeArea.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  typeInput: ElementFinder = element(by.css('input#knowledge-area-type'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTypeInput(type) {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput() {
    return this.typeInput.getAttribute('value');
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
