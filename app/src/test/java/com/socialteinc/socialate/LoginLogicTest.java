package com.socialteinc.socialate;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.socialteinc.socialate.MainActivity;

@RunWith(MockitoJUnitRunner.class)
public class LoginLogicTest {

    @Test
    public void ValidEmailTest(){

        assertEquals(false, MainActivity.isValidEmail("yakka"));
        assertEquals(true, MainActivity.isValidEmail("sandile.cyber@gmail.com"));
        assertEquals(true, MainActivity.isValidEmail("12@gmail.com"));
    }

}

