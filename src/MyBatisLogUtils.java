import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: sql log plugin
 * @Package: PACKAGE_NAME
 * @ClassName: Container
 * @Author: ywc
 * @Description: 容器
 * @Date: 2020/7/7 9:11
 * @Version: 1.0
 */
public class MyBatisLogUtils {

  private static final Logger logger = LoggerFactory.getLogger(MyBatisLogUtils.class);

  public static Map<String, ConsoleView> consoleViewMap = new HashMap<>();

  static ConsoleViewContentType greenConsoleViewContentType;

  public static void print(Project project, String sql) {
    ConsoleView consoleView = consoleViewMap.get(project.getBasePath());
    if (consoleView != null) {
      consoleView.print(sql, ConsoleViewContentType.ERROR_OUTPUT);
      consoleView.print("\n --------------------------------------------------------------------------------------------\n", green());
    } else {
      logger.warn("consoleView未初始化");
    }
  }

  public static void printFormat(Project project, String sql) {
    print(project, SqlFormatter.format(sql));
  }

  public static ConsoleViewContentType green() {
    if (greenConsoleViewContentType == null) {
      greenConsoleViewContentType = new ConsoleViewContentType("styleName", new TextAttributes(JBColor.GREEN, null, null, null, 0));
    }
    return greenConsoleViewContentType;
  }

}
