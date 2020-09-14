package com.fivos.thesuperherosquadmaker;

import com.fivos.thesuperherosquadmaker.util.MyStringUtils;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class MyStringUtilsTest {

    @Test
    public void isNullOrEmpty_nullInput_ReturnsTrue() {
        assertThat(MyStringUtils.isNoE(null)).isTrue();
    }

    @Test
    public void isNullOrEmpty_emptyInput_ReturnsTrue() {
        assertThat(MyStringUtils.isNoE("")).isTrue();
    }

    @Test
    public void isNullOrEmpty_notEmptyInput_ReturnsFalse() {
        assertThat(MyStringUtils.isNoE("This is a test")).isFalse();
    }

}
