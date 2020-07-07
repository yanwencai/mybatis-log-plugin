import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: sql log plugin
 * @Package: PACKAGE_NAME
 * @ClassName: TestAction
 * @Author: ywc
 * @Description:
 * @Date: 2020/7/6 17:29
 * @Version: 1.0
 */
public class MyBatisLogParseAction extends AnAction {


  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();

    String selectedText = e.getData(LangDataKeys.EDITOR).getCaretModel().getCurrentCaret().getSelectedText();
    Optional.ofNullable(selectedText).filter(s -> StringUtils.isNotBlank(selectedText)).ifPresent(s -> {
      List<String> sqlList = MyBatisLogParser.toSql(s);
      sqlList.forEach(sql ->MyBatisLogUtils.printFormat(project, sql));
    });


  }


}
