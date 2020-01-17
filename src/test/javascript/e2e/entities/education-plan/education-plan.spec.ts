import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EducationPlanComponentsPage, { EducationPlanDeleteDialog } from './education-plan.page-object';
import EducationPlanUpdatePage from './education-plan-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('EducationPlan e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let educationPlanComponentsPage: EducationPlanComponentsPage;
  let educationPlanUpdatePage: EducationPlanUpdatePage;
  let educationPlanDeleteDialog: EducationPlanDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load EducationPlans', async () => {
    await navBarPage.getEntityPage('education-plan');
    educationPlanComponentsPage = new EducationPlanComponentsPage();
    expect(await educationPlanComponentsPage.getTitle().getText()).to.match(/Education Plans/);
  });

  it('should load create EducationPlan page', async () => {
    await educationPlanComponentsPage.clickOnCreateButton();
    educationPlanUpdatePage = new EducationPlanUpdatePage();
    expect(await educationPlanUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.educationPlan.home.createOrEditLabel/);
    await educationPlanUpdatePage.cancel();
  });

  it('should create and save EducationPlans', async () => {
    async function createEducationPlan() {
      await educationPlanComponentsPage.clickOnCreateButton();
      await educationPlanUpdatePage.setStartAcademicYearInput('5');
      expect(await educationPlanUpdatePage.getStartAcademicYearInput()).to.eq('5');
      await educationPlanUpdatePage.specializationSelectLastOption();
      await educationPlanUpdatePage.studiesLevelSelectLastOption();
      await educationPlanUpdatePage.studiesTypeSelectLastOption();
      await educationPlanUpdatePage.fieldOfStudySelectLastOption();
      await waitUntilDisplayed(educationPlanUpdatePage.getSaveButton());
      await educationPlanUpdatePage.save();
      await waitUntilHidden(educationPlanUpdatePage.getSaveButton());
      expect(await educationPlanUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createEducationPlan();
    await educationPlanComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await educationPlanComponentsPage.countDeleteButtons();
    await createEducationPlan();
    await educationPlanComponentsPage.waitUntilLoaded();

    await educationPlanComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await educationPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last EducationPlan', async () => {
    await educationPlanComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await educationPlanComponentsPage.countDeleteButtons();
    await educationPlanComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    educationPlanDeleteDialog = new EducationPlanDeleteDialog();
    expect(await educationPlanDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.educationPlan.delete.question/);
    await educationPlanDeleteDialog.clickOnConfirmButton();

    await educationPlanComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await educationPlanComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
