package com.socialteinc.socialate;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Francis on 10/29/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SumTest {
    @Test
    public void summarize() throws Exception {
        Sum sum = new Sum();
        assertEquals(sum.summarize("A wiki is run using wiki software, otherwise known as a wiki engine. " +
                        "A wiki engine is a type of content management system, but it differs from most other such systems, including blog software, " +
                        "in that the content is created without any defined owner or leader, and wikis have little implicit structure, " +
                        "allowing structure to emerge according to the needs of the users.[2] There are dozens of different wiki engines in use, " +
                        "both standalone and part of other software, such as bug tracking systems. Some wiki engines are open source, " +
                        "whereas others are proprietary. Some permit control over different functions (levels of access); for example, " +
                        "editing rights may permit changing, adding or removing material. Others may permit access without enforcing access control. " +
                        "Other rules may be imposed to organize content." +
                "The online encyclopedia project Wikipedia is by far the most popular wiki-based website, " +
                        "and is one of the most widely viewed sites of any kind in the world, having been ranked in the top ten since 2007.[3] " +
                        "Wikipedia is not a single wiki but rather a collection of hundreds of wikis, one for each language. " +
                        "There are tens of thousands of other wikis in use, both public and private, including wikis functioning as knowledge management resources, " +
                        "notetaking tools, community websites and intranets. The English-language Wikipedia has the largest collection of articles; as of September 2016, " +
                        "it had over five million articles. Ward Cunningham, the developer of the first wiki software")
		," A wiki is run using wiki software, otherwise known as a wiki engine.  " +
                        "A wiki engine is a type of content management system, but it differs from most other such systems, " +
                        "including blog software, in that the content is created without any defined owner or leader, and wikis have little implicit structure, " +
                        "allowing structure to emerge according to the needs of the users. [2] There are dozens of different wiki engines in use, " +
                        "both standalone and part of other software, such as bug tracking systems." );

    }

}