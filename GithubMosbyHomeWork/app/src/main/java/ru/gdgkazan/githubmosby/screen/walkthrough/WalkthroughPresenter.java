package ru.gdgkazan.githubmosby.screen.walkthrough;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import ru.gdgkazan.githubmosby.utils.PreferenceUtils;
import ru.gdgkazan.githubmosby.widget.PageChangeViewPager;

/**
 * Created by DIMON on 08.09.2017.
 */

public class WalkthroughPresenter extends MvpBasePresenter<WalkthroughView> implements PageChangeViewPager.PagerStateListener {

    private static final int PAGES_COUNT = 3;
    private int mCurrentItem = 0;

    public WalkthroughPresenter() {}

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (!isViewAttached() || getView() == null) {
            return;
        }
        if (fromUser) {
            mCurrentItem = selectedPage;
            getView().showBenefit(mCurrentItem, isLastBenefit());
        }
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGES_COUNT - 1;
    }


    public void onButtonClick() {
        if (!isViewAttached() || getView() == null) {
            return;
        }
        if (isLastBenefit()) {
            PreferenceUtils.saveWalkthroughPassed();
            getView().openAuthScreen();
        } else {
            mCurrentItem++;
            getView().showBenefit(mCurrentItem, isLastBenefit());
        }
    }
}
