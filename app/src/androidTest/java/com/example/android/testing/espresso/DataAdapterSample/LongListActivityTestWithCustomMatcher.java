package com.example.android.testing.espresso.DataAdapterSample;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.testing.espresso.DataAdapterSample.LongListMatchers.withItemContent;
import static com.example.android.testing.espresso.DataAdapterSample.LongListMatchers.withItemSize;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LongListActivityTestWithCustomMatcher {
    private static final String TEXT_ITEM_30 = "item: 30";
    private static final String TEXT_ITEM_30_SELECTED = "30";
    private static final String TEXT_ITEM_60 = "item: 60";
    // Match the last item by matching its text.
    private static final String LAST_ITEM_ID = "item: 99";

    @Before
    public void launchActivity() {
        ActivityScenario.launch(LongListActivity.class);
    }

    @Test
    public void testClickOnItem50() {
        // The text view "item: 50" may not exist if we haven't scrolled to it.
        // By using onData api we tell Espresso to look into the Adapter for an item matching
        // the matcher we provide it. Espresso will then bring that item into the view hierarchy
        // and we can click on it.

        onData(withItemContent("item: 50"))
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("50")));
    }

    @Test
    public void testClickOnSpecificChildOfRow60() {
        onData(withItemContent("item: 60"))
                .onChildView(withId(R.id.rowContentTextSize)) // resource id of second column from xml layout
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("60")));

        onView(withId(R.id.selection_column_value))
                .check(matches(withText("2")));
    }


    @Test
    public void testClickOnFirstAndFifthItemOfLength8() {
        onData(is(withItemSize(8)))
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("10")));

        onData(is(withItemSize(8)))
                .atPosition(4)
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("14")));
    }

    /*  @SuppressWarnings("unchecked")
    public void testClickFooter() {
        onData(isFooter())
                .perform(click());

        onView(withId(R.id.selection_row_value))
                .check(matches(withText("100")));
    }*/

    @SuppressWarnings("unchecked")
    public void testDataItemNotInAdapter(){
        onView(withId(R.id.list))
                .check(matches(not(withAdaptedData(withItemContent("item: 168")))));
    }

    private static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
                dataMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof AdapterView)) {
                    return false;
                }
                @SuppressWarnings("rawtypes")
                Adapter adapter = ((AdapterView) view).getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (dataMatcher.matches(adapter.getItem(i))) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

}
