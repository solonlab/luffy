package org.noear.luffy.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.Utils;
import org.noear.wood.DbContext;
import org.noear.wood.DbDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author noear 2022/4/27 created
 */
public class DsUtils {

    public static DbContext getDb(Properties prop, boolean pool) {
        if (prop.size() < 4) {
            throw new RuntimeException("Data source configuration error!");
        }

        String url = prop.getProperty("url");

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        String schema = prop.getProperty("schema");

        return new DbContext(getDs(prop, pool), schema);
    }

    public static DataSource getDs(Properties prop, boolean pool) {
        String url = prop.getProperty("url");

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (pool) {
            HikariDataSource source = new HikariDataSource();

            Utils.injectProperties(source, prop);

            if (TextUtils.isNotEmpty(url)) {
                source.setJdbcUrl(url);
            }

            if (TextUtils.isNotEmpty(url)) {
                source.setJdbcUrl(url);
            }

            return source;
        } else {
            String driverClassName = prop.getProperty("driverClassName");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");

            if (TextUtils.isNotEmpty(driverClassName)) {
                try {
                    Class.forName(driverClassName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return new DbDataSource(url, username, password);
        }
    }
}