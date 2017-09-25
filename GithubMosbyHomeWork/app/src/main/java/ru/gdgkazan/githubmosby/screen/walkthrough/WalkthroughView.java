package ru.gdgkazan.githubmosby.screen.walkthrough;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by DIMON on 07.09.2017.
 */

public interface WalkthroughView extends MvpView {

    void showBenefit(int index, boolean isLastBenefit);

    void openAuthScreen();


}
