import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @ProjectName: sql log plugin
 * @Package: PACKAGE_NAME
 * @ClassName: ConsoleFilterProvider
 * @Author: ywc
 * @Description:
 * @Date: 2020/7/6 19:23
 * @Version: 1.0
 */
public final class ConsoleViewLogFilter implements ConsoleFilterProvider {


  @NotNull
  @Override
  public Filter[] getDefaultFilters(@NotNull Project project) {
    MyBatisLogFilter myBatisLogFilter = new MyBatisLogFilter(project);
    return new Filter[]{myBatisLogFilter};
  }
}
