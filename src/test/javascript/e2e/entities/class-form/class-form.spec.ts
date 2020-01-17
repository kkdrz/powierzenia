import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ClassFormComponentsPage, { ClassFormDeleteDialog } from './class-form.page-object';
import ClassFormUpdatePage from './class-form-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('ClassForm e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let classFormComponentsPage: ClassFormComponentsPage;
  let classFormUpdatePage: ClassFormUpdatePage;
  let classFormDeleteDialog: ClassFormDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load ClassForms', async () => {
    await navBarPage.getEntityPage('class-form');
    classFormComponentsPage = new ClassFormComponentsPage();
    expect(await classFormComponentsPage.getTitle().getText()).to.match(/Class Forms/);
  });

  it('should load create ClassForm page', async () => {
    await classFormComponentsPage.clickOnCreateButton();
    classFormUpdatePage = new ClassFormUpdatePage();
    expect(await classFormUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.classForm.home.createOrEditLabel/);
    await classFormUpdatePage.cancel();
  });

  it('should create and save ClassForms', async () => {
    async function createClassForm() {
      await classFormComponentsPage.clickOnCreateButton();
      await classFormUpdatePage.typeSelectLastOption();
      await waitUntilDisplayed(classFormUpdatePage.getSaveButton());
      await classFormUpdatePage.save();
      await waitUntilHidden(classFormUpdatePage.getSaveButton());
      expect(await classFormUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createClassForm();
    await classFormComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await classFormComponentsPage.countDeleteButtons();
    await createClassForm();
    await classFormComponentsPage.waitUntilLoaded();

    await classFormComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await classFormComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last ClassForm', async () => {
    await classFormComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await classFormComponentsPage.countDeleteButtons();
    await classFormComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    classFormDeleteDialog = new ClassFormDeleteDialog();
    expect(await classFormDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.classForm.delete.question/);
    await classFormDeleteDialog.clickOnConfirmButton();

    await classFormComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await classFormComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
