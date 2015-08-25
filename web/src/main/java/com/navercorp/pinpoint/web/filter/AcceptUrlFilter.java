/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.web.filter;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import com.navercorp.pinpoint.common.bo.SpanBo;

/**
 * There are two kinds of URL. ( URL requested by user, URL requesting a backend server)
 * 
 * @author netspider
 * 
 */
public class AcceptUrlFilter implements URLPatternFilter {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String urlPattern;
    private final AntPathMatcher matcher = new AntPathMatcher();



    public AcceptUrlFilter(String urlPattern) {
        if (urlPattern == null) {
            throw new NullPointerException("urlPattern must not be null");
        }
        // TODO remove decode
        this.urlPattern = new String(Base64.decodeBase64(urlPattern), UTF8);
    }

    @Override
    public boolean accept(List<SpanBo> acceptSpanList) {
        for (SpanBo spanBo : acceptSpanList) {
            if (logger.isDebugEnabled()) {
                logger.debug("urlPattern:{} rpc:{}", urlPattern, spanBo.getRpc());
            }
            if (matcher.match(urlPattern, spanBo.getRpc())) {
                return ACCEPT;
            }
        }
        return REJECT;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AcceptUrlFilter{");
        sb.append("urlPattern='").append(urlPattern).append('\'');
        sb.append('}');
        return sb.toString();
    }
}