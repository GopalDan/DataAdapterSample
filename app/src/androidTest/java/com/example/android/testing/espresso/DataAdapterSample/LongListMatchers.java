package com.example.android.testing.espresso.DataAdapterSample;

import org.hamcrest.Matcher;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.matcher.BoundedMatcher;
import java.util.Map;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by Gopal on 7/20/2020.
 */

public final class LongListMatchers {
    private LongListMatchers() { }
    /**
     * Creates a matcher against the text stored in R.id.item_content. This text is roughly
     * "item: $row_number".
     */
    public static Matcher<Object> withItemContent(String expectedText) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }

    /**
     * Creates a matcher against the text stored in R.id.item_content. This text is roughly
     * "item: $row_number".
     */
    @SuppressWarnings("rawtypes")
    public static Matcher<Object> withItemContent(final Matcher<String> itemTextMatcher) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkNotNull(itemTextMatcher);
        return new BoundedMatcher<Object, Map>(Map.class) {
            @Override
            public boolean matchesSafely(Map map) {
                return hasEntry(equalTo("STR"), itemTextMatcher).matches(map);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with item content: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }

    /**
     * Creates a matcher against the text stored in R.id.item_size. This text is the size of the text
     * printed in R.id.item_content.
     */
    public static Matcher<Object> withItemSize(int itemSize) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkArgument(itemSize > -1);
        return withItemSize(equalTo(itemSize));
    }

    /**
     * Creates a matcher against the text stored in R.id.item_size. This text is the size of the text
     * printed in R.id.item_content.
     */
    @SuppressWarnings("rawtypes")
    public static Matcher<Object> withItemSize(final Matcher<Integer> itemSizeMatcher) {
        // use preconditions to fail fast when a test is creating an invalid matcher.
        checkNotNull(itemSizeMatcher);
        return new BoundedMatcher<Object, Map>(Map.class) {
            @Override
            public boolean matchesSafely(Map map) {
                return hasEntry(equalTo("LEN"), itemSizeMatcher).matches(map);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with item size: ");
                itemSizeMatcher.describeTo(description);
            }
        };
    }

    /**
     * Creates a matcher against the footer of this list view.
     */
    /*@SuppressWarnings("unchecked")
    public static Matcher<? extends Object> isFooter() {
        // This depends on LongListActivity.FOOTER being passed as data in the addFooterView method.
        return is(LongListActivity.FOOTER);
    }*/

}
