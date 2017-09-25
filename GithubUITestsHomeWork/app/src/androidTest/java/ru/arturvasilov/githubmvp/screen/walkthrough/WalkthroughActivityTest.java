package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.os.SystemClock;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class WalkthroughActivityTest {

    /**
     * TODO : task
     *
     * Write at least 5 tests for the {@link WalkthroughActivity} class
     * Test UI elements behaviour, new Activity starts and user actions
     */

    @Rule
    public final ActivityTestRule<WalkthroughActivity> mActivityRule = new ActivityTestRule<>(WalkthroughActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void testChangeTextPerFirstButtonClick() throws Exception {
        onView(withId(R.id.btn_walkthrough)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.btn_walkthrough)).check(matches(withText(R.string.next_uppercase)));
    }

    @Test
    public void testChangeTextPerSecondButtonClick() throws Exception {
        onView(withId(R.id.btn_walkthrough)).perform(click());

        onView(withId(R.id.btn_walkthrough)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.btn_walkthrough)).check(matches(withText(R.string.finish_uppercase)));
    }

    @Test
    public void testIsAuthScreenStarted() throws Exception {
        onView(withId(R.id.btn_walkthrough)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.btn_walkthrough)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.btn_walkthrough)).perform(click());

        Intents.intended(hasComponent(AuthActivity.class.getName()));
    }

    @Test
    public void testChangeTextPerFirstScroll() throws Exception {
        onView(withId(R.id.pager)).perform(swipeLeft());

        SystemClock.sleep(1000);

        onView(withId(R.id.btn_walkthrough)).check(matches(withText(R.string.next_uppercase)));
    }

    @Test
    public void testChangeTextPerSecondScroll() throws Exception {
        onView(withId(R.id.pager)).perform(swipeLeft());

        SystemClock.sleep(1000);

        onView(withId(R.id.pager)).perform(swipeLeft());

        SystemClock.sleep(1000);

        onView(withId(R.id.btn_walkthrough)).check(matches(withText(R.string.finish_uppercase)));
    }


    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
