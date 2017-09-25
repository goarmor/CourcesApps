package ru.arturvasilov.githubmvp.screen.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesView;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoriesPresenterTest {

    private RepositoriesPresenter repositoriesPresenter;
    private RepositoriesView repositoriesView;

    @Test
    public void testCreated() throws Exception {
        assertNotNull(repositoriesPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(repositoriesView);
    }

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();

        repositoriesView = Mockito.mock(RepositoriesView.class);

        repositoriesPresenter = new RepositoriesPresenter(lifecycleHandler, repositoriesView);
    }


    @Test
    public void testShowLoadind() throws Exception {
        RepositoryProvider.setGithubRepository(new TestGithubRepository());

        repositoriesPresenter.init();

        Mockito.verify(repositoriesView).showLoading();
    }


    @Test
    public void testShowCommits() throws Exception {
        Repository rep = new Repository();

        repositoriesPresenter.onItemClick(rep);

        Mockito.verify(repositoriesView).showCommits(rep);
    }

    /**
     * TODO : task
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter}
     *
     * Your test cases must have at least 3 tests
     */
}
