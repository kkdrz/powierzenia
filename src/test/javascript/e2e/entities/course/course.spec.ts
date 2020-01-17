import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CourseComponentsPage, { CourseDeleteDialog } from './course.page-object';
import CourseUpdatePage from './course-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Course e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let courseComponentsPage: CourseComponentsPage;
  let courseUpdatePage: CourseUpdatePage;
  let courseDeleteDialog: CourseDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Courses', async () => {
    await navBarPage.getEntityPage('course');
    courseComponentsPage = new CourseComponentsPage();
    expect(await courseComponentsPage.getTitle().getText()).to.match(/Courses/);
  });

  it('should load create Course page', async () => {
    await courseComponentsPage.clickOnCreateButton();
    courseUpdatePage = new CourseUpdatePage();
    expect(await courseUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.course.home.createOrEditLabel/);
    await courseUpdatePage.cancel();
  });

  it('should create and save Courses', async () => {
    async function createCourse() {
      await courseComponentsPage.clickOnCreateButton();
      await courseUpdatePage.setNameInput('name');
      expect(await courseUpdatePage.getNameInput()).to.match(/name/);
      await courseUpdatePage.setCodeInput('code');
      expect(await courseUpdatePage.getCodeInput()).to.match(/code/);
      // courseUpdatePage.tagsSelectLastOption();
      await courseUpdatePage.educationPlanSelectLastOption();
      await waitUntilDisplayed(courseUpdatePage.getSaveButton());
      await courseUpdatePage.save();
      await waitUntilHidden(courseUpdatePage.getSaveButton());
      expect(await courseUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createCourse();
    await courseComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await courseComponentsPage.countDeleteButtons();
    await createCourse();
    await courseComponentsPage.waitUntilLoaded();

    await courseComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await courseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Course', async () => {
    await courseComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await courseComponentsPage.countDeleteButtons();
    await courseComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    courseDeleteDialog = new CourseDeleteDialog();
    expect(await courseDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.course.delete.question/);
    await courseDeleteDialog.clickOnConfirmButton();

    await courseComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await courseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
