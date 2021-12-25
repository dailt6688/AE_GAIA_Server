package helper;

import io.ebean.Ebean;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;

import java.util.List;

public class RawQuery {
    public static String QUERY_PRODUCT = "SELECT a.id,a.type,a.name,a.link_icon,a.price,a.sale,a.is_new,a.number,a.unit" +
            " FROM product a ";

    public static String getRawQueryProductDetail(int productId) {
        String sql = "SELECT a.descriptions,b.name,b.address FROM product a INNER JOIN shop b ON a.shop = b.id WHERE a.id=" + productId;
        return sql;
    }

    public static List<SqlRow> getListResultQuery(String sql) {
        SqlQuery query = Ebean.createSqlQuery(sql);
        List<SqlRow> results = query.findList();
        return results;
    }

    public static SqlRow getResultQuery(String sql) {
        SqlQuery query = Ebean.createSqlQuery(sql);
        SqlRow result = query.findOne();
        return result;
    }

}
