package sample;

import org.junit.Test;

public class HogeTest {

    @Test
    public void name() throws Exception {
        HogeAssert.assertThat(new Hoge()).hasAge(0);
    }
}