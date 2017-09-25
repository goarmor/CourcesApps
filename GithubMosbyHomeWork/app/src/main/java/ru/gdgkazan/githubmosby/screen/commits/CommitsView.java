package ru.gdgkazan.githubmosby.screen.commits;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import ru.gdgkazan.githubmosby.content.Commit;
import ru.gdgkazan.githubmosby.screen.general.LoadingView;

/**
 * Created by DIMON on 08.09.2017.
 */

public interface CommitsView extends LoadingView, MvpView {

    void showCommits(List<Commit> commits);

    void showError();

}
