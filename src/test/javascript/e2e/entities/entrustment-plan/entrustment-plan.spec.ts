import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EntrustmentPlanComponentsPage, { EntrustmentPlanDeleteDialog } from './entrustment-plan.page-object';
import EntrustmentPlanUpdatePage from './entrustment-plan-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('EntrustmentPlan e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let entrustmentPlanComponentsPage: EntrustmentPlanComponentsPage;
  let entrustmentPlanUpdatePage: EntrustmentPlanUpdatePage;
  let entrustmentPlanDeleteDialog: EntrustmentPlanDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load EntrustmentPlans', async () => {
    await navBarPage.getEntityPage('entrustment-plan');
    entrustmentPlanComponentsPage = new EntrustmentPlanComponentsPage();
    expect(await entrustmentPlanComponentsPage.getTitle().getText()).to.match(/Entrustment Plans/);
  });

  it('should load create EntrustmentPlan page', async () => {
    await entrustmentPlanComponentsPage.clickOnCreateButton();
    entrustmentPlanUpdatePage = new EntrustmentPlanUpdatePage();
    expect(await entrustmentPlanUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /powierzeniaApp.entrustmentPlan.home.createOrEditLabel/
    );
    await entrustmentPlanUpdatePage.cancel();
  });

  it('should create and save EntrustmentPlans', async () => {
    async function createEntrustmentPlan() {
      await entrustmentPlanComponentsPage.clickOnCreateButton();
      await entrustmentPlanUpdatePage.setAcademicYearInput('5');
      expect(await entrustmentPlanUpdatePage.getAcademicYearInput()).to.eq('5');
      await entrustmentPlanUpdatePage.semesterTypeSelectLastOption();
      await entrustmentPlanUpdatePage.educationPlanSelectLastOption();
      await waitUntilDisplayed(entrustmentPlanUpdatePage.getSaveButton());
      await entrustmentPlanUpdatePage.save();
      await waitUntilHidden(entrustmentPlanUpdatePage.getSaveButton());
      expect(await entrustmentPlanUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createEntrustmentPlan();
    await entrustmentPlanComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await entrustmentPlanComponentsPage.countDeleteButtons();
    await createEntrustmentPlan();
    await entrustmentPlanComponentsPage.waitUntilLoaded();

    await entrustmentPlanComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await entrustmentPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last EntrustmentPlan', async () => {
    await entrustmentPlanComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await entrustmentPlanComponentsPage.countDeleteButtons();
    await entrustmentPlanComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    entrustmentPlanDeleteDialog = new EntrustmentPlanDeleteDialog();
    expect(await entrustmentPlanDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /powierzeniaApp.entrustmentPlan.delete.question/
    );
    await entrustmentPlanDeleteDialog.clickOnConfirmButton();

    await entrustmentPlanComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await entrustmentPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
