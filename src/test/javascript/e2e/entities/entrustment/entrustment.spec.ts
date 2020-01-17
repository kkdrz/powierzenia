import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EntrustmentComponentsPage, { EntrustmentDeleteDialog } from './entrustment.page-object';
import EntrustmentUpdatePage from './entrustment-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Entrustment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let entrustmentComponentsPage: EntrustmentComponentsPage;
  let entrustmentUpdatePage: EntrustmentUpdatePage;
  let entrustmentDeleteDialog: EntrustmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Entrustments', async () => {
    await navBarPage.getEntityPage('entrustment');
    entrustmentComponentsPage = new EntrustmentComponentsPage();
    expect(await entrustmentComponentsPage.getTitle().getText()).to.match(/Entrustments/);
  });

  it('should load create Entrustment page', async () => {
    await entrustmentComponentsPage.clickOnCreateButton();
    entrustmentUpdatePage = new EntrustmentUpdatePage();
    expect(await entrustmentUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.entrustment.home.createOrEditLabel/);
    await entrustmentUpdatePage.cancel();
  });

  it('should create and save Entrustments', async () => {
    async function createEntrustment() {
      await entrustmentComponentsPage.clickOnCreateButton();
      await entrustmentUpdatePage.setHoursInput('5');
      expect(await entrustmentUpdatePage.getHoursInput()).to.eq('5');
      await entrustmentUpdatePage.setHoursMultiplierInput('5');
      expect(await entrustmentUpdatePage.getHoursMultiplierInput()).to.eq('5');
      await entrustmentUpdatePage.entrustmentPlanSelectLastOption();
      await entrustmentUpdatePage.courseClassSelectLastOption();
      await entrustmentUpdatePage.teacherSelectLastOption();
      await waitUntilDisplayed(entrustmentUpdatePage.getSaveButton());
      await entrustmentUpdatePage.save();
      await waitUntilHidden(entrustmentUpdatePage.getSaveButton());
      expect(await entrustmentUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createEntrustment();
    await entrustmentComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await entrustmentComponentsPage.countDeleteButtons();
    await createEntrustment();
    await entrustmentComponentsPage.waitUntilLoaded();

    await entrustmentComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await entrustmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Entrustment', async () => {
    await entrustmentComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await entrustmentComponentsPage.countDeleteButtons();
    await entrustmentComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    entrustmentDeleteDialog = new EntrustmentDeleteDialog();
    expect(await entrustmentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.entrustment.delete.question/);
    await entrustmentDeleteDialog.clickOnConfirmButton();

    await entrustmentComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await entrustmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
