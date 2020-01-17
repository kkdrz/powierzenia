import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TeacherComponentsPage, { TeacherDeleteDialog } from './teacher.page-object';
import TeacherUpdatePage from './teacher-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Teacher e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let teacherComponentsPage: TeacherComponentsPage;
  let teacherUpdatePage: TeacherUpdatePage;
  let teacherDeleteDialog: TeacherDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load Teachers', async () => {
    await navBarPage.getEntityPage('teacher');
    teacherComponentsPage = new TeacherComponentsPage();
    expect(await teacherComponentsPage.getTitle().getText()).to.match(/Teachers/);
  });

  it('should load create Teacher page', async () => {
    await teacherComponentsPage.clickOnCreateButton();
    teacherUpdatePage = new TeacherUpdatePage();
    expect(await teacherUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.teacher.home.createOrEditLabel/);
    await teacherUpdatePage.cancel();
  });

  it('should create and save Teachers', async () => {
    async function createTeacher() {
      await teacherComponentsPage.clickOnCreateButton();
      await teacherUpdatePage.setFirstNameInput('firstName');
      expect(await teacherUpdatePage.getFirstNameInput()).to.match(/firstName/);
      await teacherUpdatePage.setLastNameInput('lastName');
      expect(await teacherUpdatePage.getLastNameInput()).to.match(/lastName/);
      await teacherUpdatePage.setEmailInput('email');
      expect(await teacherUpdatePage.getEmailInput()).to.match(/email/);
      await teacherUpdatePage.setHourLimitInput('5');
      expect(await teacherUpdatePage.getHourLimitInput()).to.eq('5');
      await teacherUpdatePage.setPensumInput('5');
      expect(await teacherUpdatePage.getPensumInput()).to.eq('5');
      const selectedAgreedToAdditionalPensum = await teacherUpdatePage.getAgreedToAdditionalPensumInput().isSelected();
      if (selectedAgreedToAdditionalPensum) {
        await teacherUpdatePage.getAgreedToAdditionalPensumInput().click();
        expect(await teacherUpdatePage.getAgreedToAdditionalPensumInput().isSelected()).to.be.false;
      } else {
        await teacherUpdatePage.getAgreedToAdditionalPensumInput().click();
        expect(await teacherUpdatePage.getAgreedToAdditionalPensumInput().isSelected()).to.be.true;
      }
      await teacherUpdatePage.typeSelectLastOption();
      // teacherUpdatePage.allowedClassFormsSelectLastOption();
      // teacherUpdatePage.knowledgeAreasSelectLastOption();
      // teacherUpdatePage.preferedCoursesSelectLastOption();
      await waitUntilDisplayed(teacherUpdatePage.getSaveButton());
      await teacherUpdatePage.save();
      await waitUntilHidden(teacherUpdatePage.getSaveButton());
      expect(await teacherUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createTeacher();
    await teacherComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await teacherComponentsPage.countDeleteButtons();
    await createTeacher();
    await teacherComponentsPage.waitUntilLoaded();

    await teacherComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await teacherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Teacher', async () => {
    await teacherComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await teacherComponentsPage.countDeleteButtons();
    await teacherComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    teacherDeleteDialog = new TeacherDeleteDialog();
    expect(await teacherDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.teacher.delete.question/);
    await teacherDeleteDialog.clickOnConfirmButton();

    await teacherComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await teacherComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
