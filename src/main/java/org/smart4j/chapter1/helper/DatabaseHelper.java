package org.smart4j.chapter1.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.PropUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER;

    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    private static final BasicDataSource DATA_SOURCE;

    static {
        Properties config = PropUtil.loadProps("config.properties");
        DRIVER = PropUtil.getString(config, "jdbc.driver");
        URL = PropUtil.getString(config, "jdbc.url");
        USERNAME = PropUtil.getString(config, "jdbc.username");
        PASSWORD = PropUtil.getString(config, "jdbc.password");

        QUERY_RUNNER = new QueryRunner();

        CONNECTION_HOLDER = new ThreadLocal<>();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("获取数据库连接失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭Connection
     */
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("关闭Connection失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    /**
     * 通用实体列表查询方法
     *
     * @param entityClass 实体类
     * @param sql         sql语句
     * @param params      参数
     * @param <T>         泛型
     * @return 实体类列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("查询实体类列表失败", e);
            throw new RuntimeException(e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return entityList;
    }

    /**
     * 查询实体类对象
     *
     * @param entityClass 实体类
     * @param sql         sql语句
     * @param params      参数
     * @param <T>         泛型
     * @return 实体类对象
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("查询实体类失败", e);
            throw new RuntimeException(e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return entity;
    }

    /**
     * 执行负载查询
     *
     * @param sql    sql语句
     * @param params 泛型
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> list = null;
        try {
            Connection conn = getConnection();
            list = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("查询失败", e);
            throw new RuntimeException(e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return list;
    }

    /**
     * 执行操作
     *
     * @param sql    sql语句
     * @param params 泛型
     * @return 更新记录条数
     */
    public static Integer executeUpdate(String sql, Object... params) {
        Integer result = null;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("执行操作失败", e);
            throw new RuntimeException(e);
        } finally {
            DatabaseHelper.closeConnection();
        }
        return result;
    }

    /**
     * 插入实体对象
     *
     * @param entityClass 实体类
     * @param fieldMap    参数Map
     * @param <T>         泛型
     * @return 插入结果
     */
    public static <T> Boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("不能完成插入数据,fieldMap为空");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            columns.append(entry.getKey()).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + " VALUES " + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }


    /**
     * 修改实体对象
     *
     * @param entityClass 实体类
     * @param id          id
     * @param fieldMap    参数Map
     * @param <T>         泛型
     * @return 修改结果
     */
    public static <T> Boolean updateEntity(Class<T> entityClass, Long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("不能完成update数据,fieldMap为空");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            columns.append(entry.getKey()).append("=?, ");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), " WHERE id=?");
        sql += columns.toString();
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除记录
     *
     * @param entityClass 实体类
     * @param id          id
     * @param <T>         泛型
     * @return 修改结果
     */
    public static <T> Boolean deleteEntity(Class<T> entityClass, Long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 获取表名
     *
     * @param entityClass 实体类
     * @return 表明
     */
    public static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    /**
     * 执行sql文件
     *
     * @param filePath sql文件路径
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String sql = null;
        try {
            while ((sql = br.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("读取数据初始化文件失败", e);
        }
    }

    /**
     * 根据关键字查询
     *
     * @param entityClass 实体类
     * @param sql         sql语句，不可包含where子句
     * @param keyword     关键字
     * @param fields      要和关键字对比的字段名
     * @param <T>         泛型
     * @return List
     */
    public static <T> List<T> queryEntityListByKeyword(Class<T> entityClass, String sql, String keyword, String... fields) {
        List<T> list = null;
        try {
            Connection conn = getConnection();
            StringBuilder where = new StringBuilder(" WHERE ");
            for (String field : fields) {
                where.append(field).append(" LIKE %").append(keyword).append("% OR");
            }
            where.replace(where.lastIndexOf("OR"), where.length(), "");
            sql += where.toString();
            list = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass));
            return list;
        } catch (SQLException e) {
            LOGGER.error("根据关键字查询出错", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }
}
