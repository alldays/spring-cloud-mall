package com.kuqi.mall.web.core.converter;

import cn.hutool.core.util.NumberUtil;
import com.kuqi.mall.web.core.utils.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 时间戳转换为LocalDateTime
 *
 * @Author iloveoverfly
 * @Date 2021/1/15 18:01
 **/
public class String2LocalDateTime implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String mills) {

        if (StringUtils.isEmpty(mills) || !NumberUtil.isNumber(mills)) {
            return null;
        }
        return DateUtils.getDateTimeOfTimestamp(Long.parseLong(mills));
    }
}
