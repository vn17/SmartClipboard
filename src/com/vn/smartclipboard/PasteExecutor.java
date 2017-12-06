package com.vn.smartclipboard;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class PasteExecutor extends AbstractCopyPasteExecutor {

    @NotNull
    @Override
    GenerateDialog getSpecificGenerateDialog() {
        return getGenerateDialog(false);
    }

    @Override
    public void execute(AnActionEvent anActionEvent, SelectionModel selectionModel, String inputKey) {
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = anActionEvent.getRequiredData(CommonDataKeys.PROJECT);
        //Access document, caret, and selection
        final Document document = editor.getDocument();
        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();

        Runnable runnable = () -> document.replaceString(start, end, DataStorage.retrieveAndRemoveData(inputKey));

        //Making the replacement
        WriteCommandAction.runWriteCommandAction(project, runnable);
        selectionModel.removeSelection();
    }
}
