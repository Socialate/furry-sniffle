package com.socialteinc.socialate;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class LoginLogicTest {

    @Test
    public void ValidEmailTest(){

        assertEquals(false, LoginActivity.isValidEmail("yakka"));
        assertEquals(true, LoginActivity.isValidEmail("sandile.cyber@gmail.com"));
        assertEquals(true, LoginActivity.isValidEmail("12@gmail.com"));
    }

}

