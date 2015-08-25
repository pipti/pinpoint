package com.navercorp.pinpoint.web.filter;

import com.navercorp.pinpoint.common.bo.SpanBo;
import org.apache.hadoop.hbase.util.Base64;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author emeroad
 */
public class AcceptUrlFilterTest {
    private static final String UTF8 = "UTF8";

    @Test
    public void acceptTest_1() {

        AcceptUrlFilter filter = new AcceptUrlFilter(encode("/**/*"));
        SpanBo spanBo = new SpanBo();
        spanBo.setRpc("/test");
        Assert.assertTrue(filter.accept(Arrays.asList(spanBo)));

    }

    @Test
    public void acceptTest_2() {

        AcceptUrlFilter filter = new AcceptUrlFilter(encode("/abc/*"));
        SpanBo spanBo = new SpanBo();
        spanBo.setRpc("/test");
        Assert.assertFalse(filter.accept(Arrays.asList(spanBo)));

    }

    private String encode(String value) {
        try {
            return Base64.encodeBytes(value.getBytes(UTF8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}