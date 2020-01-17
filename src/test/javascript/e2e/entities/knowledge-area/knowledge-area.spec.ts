import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import KnowledgeAreaComponentsPage, { KnowledgeAreaDeleteDialog } from './knowledge-area.page-object';
import KnowledgeAreaUpdatePage from './knowledge-area-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('KnowledgeArea e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let knowledgeAreaComponentsPage: KnowledgeAreaComponentsPage;
  let knowledgeAreaUpdatePage: KnowledgeAreaUpdatePage;
  let knowledgeAreaDeleteDialog: KnowledgeAreaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  it('should load KnowledgeAreas', async () => {
    await navBarPage.getEntityPage('knowledge-area');
    knowledgeAreaComponentsPage = new KnowledgeAreaComponentsPage();
    expect(await knowledgeAreaComponentsPage.getTitle().getText()).to.match(/Knowledge Areas/);
  });

  it('should load create KnowledgeArea page', async () => {
    await knowledgeAreaComponentsPage.clickOnCreateButton();
    knowledgeAreaUpdatePage = new KnowledgeAreaUpdatePage();
    expect(await knowledgeAreaUpdatePage.getPageTitle().getAttribute('id')).to.match(/powierzeniaApp.knowledgeArea.home.createOrEditLabel/);
    await knowledgeAreaUpdatePage.cancel();
  });

  it('should create and save KnowledgeAreas', async () => {
    async function createKnowledgeArea() {
      await knowledgeAreaComponentsPage.clickOnCreateButton();
      await knowledgeAreaUpdatePage.setTypeInput('type');
      expect(await knowledgeAreaUpdatePage.getTypeInput()).to.match(/type/);
      await waitUntilDisplayed(knowledgeAreaUpdatePage.getSaveButton());
      await knowledgeAreaUpdatePage.save();
      await waitUntilHidden(knowledgeAreaUpdatePage.getSaveButton());
      expect(await knowledgeAreaUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createKnowledgeArea();
    await knowledgeAreaComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await knowledgeAreaComponentsPage.countDeleteButtons();
    await createKnowledgeArea();
    await knowledgeAreaComponentsPage.waitUntilLoaded();

    await knowledgeAreaComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await knowledgeAreaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last KnowledgeArea', async () => {
    await knowledgeAreaComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await knowledgeAreaComponentsPage.countDeleteButtons();
    await knowledgeAreaComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    knowledgeAreaDeleteDialog = new KnowledgeAreaDeleteDialog();
    expect(await knowledgeAreaDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/powierzeniaApp.knowledgeArea.delete.question/);
    await knowledgeAreaDeleteDialog.clickOnConfirmButton();

    await knowledgeAreaComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await knowledgeAreaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
