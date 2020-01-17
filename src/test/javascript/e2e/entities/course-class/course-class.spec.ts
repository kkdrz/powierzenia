import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CourseClassComponentsPage, { CourseClassDeleteDialog } from './course-class.page-object';
import CourseClassUpdatePage from './course-class-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('CourseClass e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let courseClassComponentsPage: CourseClassComponentsPage;
  let courseClassUpdatePage: CourseClassUpdatePage;
  let courseClassDeleteDialog: CourseClassDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load CourseClasses', async () => {
    await navBarPage.getEntityPage('course-class');
    courseClassComponentsPage = new CourseClassComponentsPage();
    expect(await courseClassComponentsPage.getTitle().getText()).to.match(/Course Classes/);
  });

  it('should load create CourseClass page', async () => {
    await courseClassComponentsPage.clickOnCreateButton();
    courseClassUpdatePage = new CourseClassUpdatePage();
    expect(await courseClassUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.courseClass.home.createOrEditLabel/);
    await courseClassUpdatePage.cancel();
  });

  it('should create and save CourseClasses', async () => {
    async function createCourseClass() {
      await courseClassComponentsPage.clickOnCreateButton();
      await courseClassUpdatePage.setHoursInput('5');
      expect(await courseClassUpdatePage.getHoursInput()).to.eq('5');
      await courseClassUpdatePage.formSelectLastOption();
      await courseClassUpdatePage.courseSelectLastOption();
      await waitUntilDisplayed(courseClassUpdatePage.getSaveButton());
      await courseClassUpdatePage.save();
      await waitUntilHidden(courseClassUpdatePage.getSaveButton());
      expect(await courseClassUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createCourseClass();
    await courseClassComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await courseClassComponentsPage.countDeleteButtons();
    await createCourseClass();
    await courseClassComponentsPage.waitUntilLoaded();

    await courseClassComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await courseClassComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last CourseClass', async () => {
    await courseClassComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await courseClassComponentsPage.countDeleteButtons();
    await courseClassComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    courseClassDeleteDialog = new CourseClassDeleteDialog();
    expect(await courseClassDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.courseClass.delete.question/);
    await courseClassDeleteDialog.clickOnConfirmButton();

    await courseClassComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await courseClassComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
