import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import FieldOfStudyComponentsPage, { FieldOfStudyDeleteDialog } from './field-of-study.page-object';
import FieldOfStudyUpdatePage from './field-of-study-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('FieldOfStudy e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fieldOfStudyComponentsPage: FieldOfStudyComponentsPage;
  let fieldOfStudyUpdatePage: FieldOfStudyUpdatePage;
  let fieldOfStudyDeleteDialog: FieldOfStudyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load FieldOfStudies', async () => {
    await navBarPage.getEntityPage('field-of-study');
    fieldOfStudyComponentsPage = new FieldOfStudyComponentsPage();
    expect(await fieldOfStudyComponentsPage.getTitle().getText()).to.match(/Field Of Studies/);
  });

  it('should load create FieldOfStudy page', async () => {
    await fieldOfStudyComponentsPage.clickOnCreateButton();
    fieldOfStudyUpdatePage = new FieldOfStudyUpdatePage();
    expect(await fieldOfStudyUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.fieldOfStudy.home.createOrEditLabel/);
    await fieldOfStudyUpdatePage.cancel();
  });

  it('should create and save FieldOfStudies', async () => {
    async function createFieldOfStudy() {
      await fieldOfStudyComponentsPage.clickOnCreateButton();
      await fieldOfStudyUpdatePage.setNameInput('name');
      expect(await fieldOfStudyUpdatePage.getNameInput()).to.match(/name/);
      await waitUntilDisplayed(fieldOfStudyUpdatePage.getSaveButton());
      await fieldOfStudyUpdatePage.save();
      await waitUntilHidden(fieldOfStudyUpdatePage.getSaveButton());
      expect(await fieldOfStudyUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createFieldOfStudy();
    await fieldOfStudyComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await fieldOfStudyComponentsPage.countDeleteButtons();
    await createFieldOfStudy();
    await fieldOfStudyComponentsPage.waitUntilLoaded();

    await fieldOfStudyComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await fieldOfStudyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last FieldOfStudy', async () => {
    await fieldOfStudyComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await fieldOfStudyComponentsPage.countDeleteButtons();
    await fieldOfStudyComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    fieldOfStudyDeleteDialog = new FieldOfStudyDeleteDialog();
    expect(await fieldOfStudyDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.fieldOfStudy.delete.question/);
    await fieldOfStudyDeleteDialog.clickOnConfirmButton();

    await fieldOfStudyComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await fieldOfStudyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
