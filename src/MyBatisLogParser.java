import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: sql log plugin
 * @Package: PACKAGE_NAME
 * @ClassName: LogParser
 * @Author: ywc
 * @Description:
 * @Date: 2020/7/6 20:05
 * @Version: 1.0
 */
public class MyBatisLogParser {

  public static final String SQL_PREFIX = "Preparing:";
  public static final String PARAM_PREFIX = "Parameters:";
  public static final String EXCLUDE_SQL = "Preparing: INSERT INTO";
  static String logStr = "";


  public static List<String> toSql(String str) {
    try {
      return parseLog(str);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("sql日志解析错误", e);
    }
  }


  private static List<String> parseLog(String str) {

    if (StringUtils.isBlank(logStr) && !StringUtils.containsIgnoreCase(str, EXCLUDE_SQL) && str.contains(SQL_PREFIX)) {
      logStr = logStr + str;
    }

    if (logStr.contains(SQL_PREFIX) && str.contains(PARAM_PREFIX)) {
      logStr = logStr + str;
    }

    if (!logStr.contains(SQL_PREFIX) || !logStr.contains(PARAM_PREFIX)) {
      return null;
    }


    List<String> sqlList = new ArrayList<>();
    String sql = null, paramStr = null;
    List<String> list = Splitter.on("\n").omitEmptyStrings().splitToList(logStr);

    for (String s : list) {
      String sqlNeedParams = StringUtils.substringAfter(s, SQL_PREFIX);
      sql = StringUtils.isNotBlank(sql) ? sql : StringUtils.isBlank(sqlNeedParams) ? s : sqlNeedParams;
      paramStr = StringUtils.isBlank(paramStr) ? StringUtils.substringAfter(s, PARAM_PREFIX) : paramStr;
      if (StringUtils.isNotBlank(sql) && StringUtils.isNotBlank(paramStr)) {
        List<String> paramList = Splitter.on(",").omitEmptyStrings().splitToList(paramStr);
        for (String param : paramList) {
          String pType = StringUtils.substringBetween(param, "(", ")");
          param = param.trim().replace("(" + pType + ")", "");
          String targetStr = null;
          if ("String".equals(pType) || "Timestamp".equals(pType)) {
            targetStr = "'" + param + "'";
          } else if ("Integer".equals(pType) || "Long".equals(pType) || "Double".equals(pType) || "Float".equals(pType)) {
            targetStr = param;
          } else {
            targetStr = param;
          }
          sql = sql.replaceFirst("\\?", targetStr);
        }
        paramStr = null;
        sqlList.add(sql);
      }
    }
    logStr = "";
    return sqlList;
  }

}
