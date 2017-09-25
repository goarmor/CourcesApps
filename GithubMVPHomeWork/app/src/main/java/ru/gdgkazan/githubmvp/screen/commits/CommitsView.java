package ru.gdgkazan.githubmvp.screen.commits;

import java.util.List;

import ru.gdgkazan.githubmvp.content.Commit;
import ru.gdgkazan.githubmvp.screen.general.LoadingView;

/**
 * Created by DIMON on 08.09.2017.
 */

public interface CommitsView extends LoadingView {

    void showCommits(List<Commit> commits);

    void showError();

}
