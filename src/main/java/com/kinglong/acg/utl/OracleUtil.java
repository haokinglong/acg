package com.kinglong.acg.utl;

import cn.hutool.core.util.StrUtil;
import com.kinglong.acg.consts.ColumnDataType;
import com.kinglong.acg.consts.CoreEnum;
import com.kinglong.acg.consts.QueryOperationEnum;
import com.kinglong.acg.consts.YesOrNo;
import com.kinglong.acg.core.MetaColumn;
import com.kinglong.acg.core.MetaDatabase;
import com.kinglong.acg.core.MetaTable;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * oracle工具类
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
public class OracleUtil {

    static {
        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用以查询表字段的固定sql语句
     */
    private static final String QUERY_COLUMNS_SQL = "SELECT column_name, data_type, character_maximum_length, is_nullable, column_comment, column_key FROM information_schema.columns WHERE table_name =? AND table_schema =?";

    /**
     * 获取表
     *
     * @param database 数据库实体类
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<MetaTable> getDatabase(MetaDatabase database) throws SQLException, ClassNotFoundException {
        List<MetaTable> resultTables = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(database.getUrl() + database.getSchema(), database.getUsername(), database.getPassword())) {
            List<MetaTable> specifyTables = database.getMetaTables();
            String sql = getExecuteSqlFromTables(specifyTables);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, database.getSchema());
            if (!CollectionUtils.isEmpty(specifyTables)) {
                for (int i = 2; i <= specifyTables.size() + 1; i++) {
                    pstmt.setString(i, specifyTables.get(i - 2).getName().toLowerCase());
                }
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                buildMetaTables(rs, resultTables, database.getUrl(), database.getSchema(), database.getUsername(), database.getPassword());
            }
        }

        return resultTables;
    }

    /**
     * 获取表下所有的字段
     *
     * @param url      数据库连接地址
     * @param username 用户名
     * @param password 密码
     * @param table    要查询的表名
     * @return
     * @throws SQLException
     */
    public static Map<CoreEnum, Object> listColumns(String url, String schema, String username, String password, MetaTable metaTable, String table) throws SQLException {
        HashMap<CoreEnum, Object> result = new HashMap<>(4);
        StringBuilder queryParamsStr = new StringBuilder();
        StringBuilder queryParamsExcludeTypeStr = new StringBuilder();

        try (Connection conn = DriverManager.getConnection(url + schema, username, password); PreparedStatement pstmt = conn.prepareStatement(QUERY_COLUMNS_SQL)) {
            pstmt.setString(1, table);
            pstmt.setString(2, schema);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<MetaColumn> metaColumns = new ArrayList<>();
                while (rs.next()) {
                    buildMetaColumns(rs, metaTable, metaColumns, queryParamsStr, queryParamsExcludeTypeStr);
                }

                result.put(CoreEnum.COLUMN_LIST, metaColumns);
                result.put(CoreEnum.QUERY_PARAMS_STR, queryParamsStr.toString());
                result.put(CoreEnum.QUERY_PARAMS_EXCLUDE_TYPE_STR, queryParamsExcludeTypeStr.toString());

                return result;
            }
        }
    }

    /**
     * 根据元数据表生成执行的sql语句
     *
     * @param tables 指定的要生成的元数据表
     * @return String
     */
    private static String getExecuteSqlFromTables(List<MetaTable> tables) {
        StringBuilder sql = new StringBuilder();
        //如果没有指定需要生成的表,则默认会查询该用户下所有的表,并生成代码
        if (!CollectionUtils.isEmpty(tables)) {
            sql.append("SELECT DISTINCT table_name,table_comment FROM information_schema.tables WHERE table_schema =? AND table_name IN (");

            for (int i = 0; i < tables.size(); i++) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else {
            sql.append("SELECT DISTINCT table_name,table_comment FROM information_schema.tables WHERE table_name =? AND table_schema =?");
        }

        return sql.toString();
    }

    /**
     * 构建元数据表
     *
     * @param rs           结果集
     * @param resultTables 元数据表列表
     * @param url          数据库连接
     * @param schema       实例名
     * @param username     用户名
     * @param password     密码
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void buildMetaTables(ResultSet rs, List<MetaTable> resultTables, String url, String schema, String username, String password) throws SQLException, ClassNotFoundException {
        MetaTable metaTable = new MetaTable();
        String tableName = rs.getString(CoreEnum.TABLE_NAME.getValue());
        String cmt = rs.getString(CoreEnum.TABLE_COMMENT.getValue());
        if (StrUtil.isBlank(cmt)) {
            throw new IllegalArgumentException(tableName + " comments is not null");
        }

        metaTable.setCmt(cmt);
        metaTable.setName(tableName);

        Map<CoreEnum, Object> map = listColumns(url, schema, username, password, metaTable, rs.getString(CoreEnum.TABLE_NAME.getValue()));

        metaTable.setMetaColumns((List<MetaColumn>) map.get(CoreEnum.COLUMN_LIST));
        metaTable.setQueryParamsStr((String) map.get(CoreEnum.QUERY_PARAMS_STR));
        metaTable.setQueryParamsExcludeTypeStr((String) map.get(CoreEnum.QUERY_PARAMS_EXCLUDE_TYPE_STR));

        resultTables.add(metaTable);
    }

    /**
     * 构建元数据列
     *
     * @param rs                        结果集
     * @param metaColumns               字段列
     * @param queryParamsStr            用于拼接实现类里的page接口参数,有参数类型的字符串 {@see MetaTable#queryParamsStr}
     * @param queryParamsExcludeTypeStr 用于拼接各层page接口访问的参数签名字段,为排除参数类型的拼接字符串 {@see MetaTable#queryParamsExcludeTypeStr}
     * @throws SQLException
     */
    private static void buildMetaColumns(ResultSet rs, MetaTable metaTable, List<MetaColumn> metaColumns, StringBuilder queryParamsStr, StringBuilder queryParamsExcludeTypeStr) throws SQLException {
        String dataTypeName = rs.getString(CoreEnum.DATA_TYPE.getValue());
        ColumnDataType dataType = ColumnDataType.getColumnDataType(dataTypeName);
        if (null == dataType) {
            throw new RuntimeException("Unsupported DataType : " + dataTypeName);
        }
        String cmt = rs.getString(CoreEnum.COMMENTS.getValue());
        String columnName = rs.getString(CoreEnum.COLUMN_NAME.getValue());
        if (StrUtil.isBlank(cmt)) {
            throw new IllegalArgumentException(columnName + " comments is not null");
        }

        MetaColumn metaColumn = new MetaColumn();
        metaColumn.setName(columnName);
        metaColumn.setDataType(dataType);
        metaColumn.setDataLength(rs.getInt(CoreEnum.DATA_LENGTH.getValue()));
        metaColumn.setNullable(YesOrNo.getByValue(rs.getString(CoreEnum.NULLABLE.getValue())));
        metaColumn.setIsPrimaryKey(CoreEnum.PRIMARY_KEY.getValue().equals(rs.getString(CoreEnum.COLUMN_KEY.getValue())) ? YesOrNo.YES : YesOrNo.NO);
        metaColumn.setCmt(cmt.substring(0, cmt.length() - cmt.indexOf('(') - 1));
        metaColumn.setOperation(getQueryOperation(cmt));

        //因为范围查询是需要两个字段，所以这里单独处理
        if (StrUtil.contains(cmt, QueryOperationEnum.B.getValue())) {
            queryParamsStr.append(metaColumn.getDataTypeStr()).append(" ").append(metaColumn.getCamelName()).append("Start, ").append(metaColumn.getDataTypeStr()).append(" ").append(metaColumn.getCamelName()).append("End, ");
            queryParamsExcludeTypeStr.append(metaColumn.getCamelName()).append("Start, ").append(metaColumn.getCamelName()).append("End, ");
        } else if (StrUtil.containsAny(cmt, QueryOperationEnum.A.getValue(), QueryOperationEnum.L.getValue(), QueryOperationEnum.R.getValue(), QueryOperationEnum.Q.getValue())) {
            queryParamsStr.append(metaColumn.getDataTypeStr()).append(" ").append(metaColumn.getCamelName()).append(", ");
            queryParamsExcludeTypeStr.append(metaColumn.getCamelName()).append(", ");
        }
        if (CoreEnum.PRIMARY_KEY.getValue().equals(rs.getString(CoreEnum.COLUMN_KEY.getValue()))) {
            metaTable.setPrimaryKey(metaColumn.getName());
            metaTable.setPrimaryKeyType(dataType);
            metaTable.setPrimaryKeyCmt(metaColumn.getCmt());
        }

        metaColumns.add(metaColumn);
    }

    /**
     * 根据注释获取该字段的操作类型
     * <p>
     * {@link QueryOperationEnum}
     * </p>
     *
     * @param operation 字段注释
     * @return QueryOperationEnum
     */
    private static QueryOperationEnum getQueryOperation(String operation) {
        if (operation.contains(QueryOperationEnum.Q.getValue())) {
            return QueryOperationEnum.Q;
        }
        if (operation.contains(QueryOperationEnum.B.getValue())) {
            return QueryOperationEnum.B;
        }
        if (operation.contains(QueryOperationEnum.L.getValue())) {
            return QueryOperationEnum.L;
        }
        if (operation.contains(QueryOperationEnum.R.getValue())) {
            return QueryOperationEnum.R;
        }
        if (operation.contains(QueryOperationEnum.A.getValue())) {
            return QueryOperationEnum.A;
        }

        return QueryOperationEnum.N;
    }
}