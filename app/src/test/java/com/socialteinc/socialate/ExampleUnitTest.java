package com.socialteinc.socialate;

import android.view.View;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Mock
    MainActivity m;
    View v;
    @Test
    public void addition_isCorrect() throws Exception {
//        MainActivity m = new MainActivity();
        assertEquals(false, m.isConnected());
        m.checkProfileExist();
        m.setVisibility(0);
        m.onAddEntertainment(v);
    }
}