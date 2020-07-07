import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ProjectName: sql log plugin
 * @Package: PACKAGE_NAME
 * @ClassName: MySqlFilter
 * @Author: ywc
 * @Description:
 * @Date: 2020/7/6 19:18
 * @Version: 1.0
 */
public class MyBatisLogFilter implements Filter {

  private Project project;


  public MyBatisLogFilter(Project project) {
    this.project = project;
  }


  @Nullable
  @Override
  public Result applyFilter(@NotNull String paramString, int paramInt) {

    if ((!paramString.contains(MyBatisLogParser.SQL_PREFIX)
        && !paramString.contains(MyBatisLogParser.PARAM_PREFIX)) || StringUtils.isBlank(paramString)) {
      return null;
    }
    List<String> sqlList = MyBatisLogParser.toSql(paramString);

    if (CollectionUtils.isNotEmpty(sqlList)) {
      sqlList.forEach(sql -> MyBatisLogUtils.printFormat(project, sql));
    }
    return null;
  }
}
