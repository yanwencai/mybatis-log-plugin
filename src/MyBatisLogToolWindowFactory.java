// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyBatisLogToolWindowFactory implements ToolWindowFactory {


  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    SimpleToolWindowPanel simpleToolWindowPanel = new SimpleToolWindowPanel(false, true);
    ConsoleViewImpl consoleView = new ConsoleViewImpl(project, true);
    MyBatisLogUtils.consoleViewMap.put(project.getBasePath(), consoleView);
    simpleToolWindowPanel.setContent(consoleView.getComponent());
    ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    Content content = contentFactory.createContent(simpleToolWindowPanel, "", false);
    toolWindow.getContentManager().addContent(content);
  }
}
