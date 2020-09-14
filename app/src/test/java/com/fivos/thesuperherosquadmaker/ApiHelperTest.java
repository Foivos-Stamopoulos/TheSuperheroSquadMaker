package com.fivos.thesuperherosquadmaker;

import com.fivos.thesuperherosquadmaker.api.ApiHelper;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class ApiHelperTest {

    @Test
    public void hash_nonNullTimestamp_ReturnsNonNull() {
        String timeStamp = ApiHelper.getTimeStamp();
        assertThat(ApiHelper.getHash(timeStamp)).isNotNull();
    }

    @Test
    public void hash_nullTimestamp_ReturnsNonNull() {
        assertThat(ApiHelper.getHash(null)).isNotNull();
    }

}
