package ru.arturvasilov.githubmvp.test;

import android.support.annotation.NonNull;

import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;

/**
 * Created by DIMON on 18.09.2017.
 */

public class TestWalkthroughPresenter extends WalkthroughPresenter {

    private WalkthroughPresenter mMockitoPresenter;


    public TestWalkthroughPresenter(@NonNull WalkthroughView walkthroughView, WalkthroughPresenter mMockitoPresenter) {
        super(walkthroughView);
        this.mMockitoPresenter = mMockitoPresenter;
    }

    public void onPageChanged(int selectedPage, boolean fromUser) {
        super.onPageChanged(selectedPage, fromUser);
        if (fromUser) {
            mMockitoPresenter.showBenefitText();
        }
    }

    public void setmCurrentItem(int mCurrentItem) {
        super.mCurrentItem = mCurrentItem;
    }

}
