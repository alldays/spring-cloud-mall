package com.kuqi.mall.mybatis.core.handler;

import com.kuqi.mall.mybatis.core.utils.Encryptors;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 对称加密解密 Handler
 *
 * @Author iloveoverfly
 * @LocalDateTime 2020/7/29 9:20
 **/
public class EncryptorsHandler extends BaseTypeHandler<String> {

    private static final String KEY = "com.kuqi.mall";
    private final Encryptors encryptors;

    public EncryptorsHandler() {
        this.encryptors = new Encryptors(KEY);
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, StringUtils.isBlank(parameter) ? null : encryptors.encrypt(parameter));
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String encryptedData;
        return StringUtils.isNotBlank(encryptedData = rs.getString(columnName)) ? encryptors.decrypt(encryptedData) : null;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String encryptedData;
        return StringUtils.isNotBlank(encryptedData = rs.getString(columnIndex)) ? encryptors.decrypt(encryptedData) : null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String encryptedData;
        return StringUtils.isNotBlank(encryptedData = cs.getString(columnIndex)) ? encryptors.decrypt(encryptedData) : null;
    }
}
