package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.support.annotation.StringRes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.githubmvp.test.TestReturnFalseValueStorage;
import ru.arturvasilov.githubmvp.test.TestReturnTrueValueStorage;
import ru.arturvasilov.githubmvp.test.TestWalkthroughPresenter;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {

    private TestWalkthroughPresenter mPresenter;
    private WalkthroughView mView;
    private WalkthroughPresenter mockPres;

    @Before
    public void setUp() throws Exception {
        mView = Mockito.mock(WalkthroughView.class);
        mockPres = Mockito.mock(WalkthroughPresenter.class);
        mPresenter = new TestWalkthroughPresenter(mView, mockPres);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testAuthStarted() throws Exception {
        KeyValueStorage keyValueStorage = new TestReturnTrueValueStorage();
        RepositoryProvider.setKeyValueStorage(keyValueStorage);

        mPresenter.init();

        Mockito.verify(mView).startAuth();
    }

    @Test
    public void testShowBenefits() throws Exception {
        KeyValueStorage keyValueStorage = new TestReturnFalseValueStorage();
        RepositoryProvider.setKeyValueStorage(keyValueStorage);


        mPresenter.init();

        Mockito.verify(mView).showBenefits(mPresenter.getBenefits());
    }

    @Test
    public void testAuthStartedWhileBenefitIsLast() {
        mPresenter.setmCurrentItem(2);

        RepositoryProvider.setKeyValueStorage(new TestReturnFalseValueStorage());

        mPresenter.onActionButtonClick();

        Mockito.verify(mView).startAuth();
    }

    @Test
    public void testIsScrollToNextBenefit () {
        mPresenter.setmCurrentItem(0);

        RepositoryProvider.setKeyValueStorage(new TestReturnFalseValueStorage());

        mPresenter.onActionButtonClick();

        Mockito.verify(mView).scrollToNextBenefit();
    }

    @Test
    public void testGetBenefits() {
        assertEquals(3, mPresenter.getBenefits().size());
    }

    @Test
    public void testIsLastBenefitCorrectTrue() {
        mPresenter.setmCurrentItem(2);

        assertTrue(mPresenter.isLastBenefit());
    }

    @Test
    public void testIsLastBenefitCorrectFalse() {
        mPresenter.setmCurrentItem(1);

        assertFalse(mPresenter.isLastBenefit());
    }

    @Test
    public void testBenefitTextShowed() {
        mPresenter.onPageChanged(0, true);

        Mockito.verify(mockPres).showBenefitText();
    }

    @Test
    public void testActionButtonTextShowed() {

        mPresenter.showBenefitText();


        @StringRes int buttonTextId = R.string.next_uppercase;

        Mockito.verify(mView).showActionButtonText(buttonTextId);
    }

    @Test
    public void testScreenScenario() {
        RepositoryProvider.setKeyValueStorage(new TestReturnFalseValueStorage());

        mPresenter.init();

        RepositoryProvider.setKeyValueStorage(new TestReturnTrueValueStorage());

        mPresenter.init();

        mPresenter.setmCurrentItem(0);

        RepositoryProvider.setKeyValueStorage(new TestReturnFalseValueStorage());

        mPresenter.onActionButtonClick();

        @StringRes int buttonTextId = R.string.next_uppercase;


        Mockito.verify(mView).startAuth();
        Mockito.verify(mView).showBenefits(mPresenter.getBenefits());
        Mockito.verify(mView).scrollToNextBenefit();
        Mockito.verify(mView, times(2)).showActionButtonText(buttonTextId);
        Mockito.verifyNoMoreInteractions(mView);
    }


    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
        RepositoryProvider.setGithubRepository(null);
    }


    /**
     * TODO : task
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter}
     *
     * Your test cases must have at least 6 small tests and 1 large test (for some interaction scenario for this screen)
     */
}
