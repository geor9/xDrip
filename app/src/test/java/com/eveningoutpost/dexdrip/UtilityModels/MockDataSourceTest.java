package com.eveningoutpost.dexdrip.UtilityModels;

/**
 * Created by jamorham on 10/10/2017.
 */

import com.eveningoutpost.dexdrip.Services.TransmitterRawData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class MockDataSourceTest {

    static final int RAW_LOWER_BOUND = 50000;
    static final int RAW_UPPER_BOUND = 150000;


    private static void log(String msg) {
        System.out.println(msg);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test_getFakeWifiData() throws Exception {
        final String str = MockDataSource.getFakeWifiData();
        log("Mock Data: " + str);

        assertThat("Data not null", str != null, is(true));
        assertThat("Marker found", str.contains("RelativeTime"), is(true));

        final Gson gson = new GsonBuilder().create();
        final TransmitterRawData trd = gson.fromJson(str, TransmitterRawData.class);
        log(trd.toTableString());

        assertThat("Sane Raw", trd.getRawValue() < RAW_UPPER_BOUND && trd.getRawValue() > RAW_LOWER_BOUND, is(true));
        assertThat("Sane Filtered", trd.getFilteredValue() < RAW_UPPER_BOUND && trd.getFilteredValue() > RAW_LOWER_BOUND, is(true));

    }
}