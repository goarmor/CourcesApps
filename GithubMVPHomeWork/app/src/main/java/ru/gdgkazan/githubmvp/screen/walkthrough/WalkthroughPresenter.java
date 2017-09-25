package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.utils.PreferenceUtils;
import ru.gdgkazan.githubmvp.widget.PageChangeViewPager;

/**
 * Created by DIMON on 08.09.2017.
 */

public class WalkthroughPresenter implements PageChangeViewPager.PagerStateListener {

    private static final int PAGES_COUNT = 3;
    private int mCurrentItem = 0;

    private final LifecycleHandler mLifecycleHandler;
    private final WalkthroughView mView;

    public WalkthroughPresenter(@NonNull LifecycleHandler lifecycleHandler,
                                 @NonNull WalkthroughView view) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
    }

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            mCurrentItem = selectedPage;
            mView.showBenefit(mCurrentItem, isLastBenefit());
        }
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGES_COUNT - 1;
    }


    public void onButtonClick() {
        if (isLastBenefit()) {
            PreferenceUtils.saveWalkthroughPassed();
            mView.openAuthScreen();
        } else {
            mCurrentItem++;
            mView.showBenefit(mCurrentItem, isLastBenefit());
        }
    }
}
