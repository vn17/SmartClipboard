package com.vn.smartclipboard;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.util.indexing.StorageException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCopyPasteExecutor extends AnAction {
    private SelectionModel selection;

    private AnActionEvent actionEvent;


    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        //Is this method never called?
         }

    @Override
    public void update(AnActionEvent anActionEvent) {
        actionEvent = anActionEvent;
        selection = getSelection(anActionEvent);
        GenerateDialog dialog = getSpecificGenerateDialog();
        String inputKey = dialog.getInputKey();
        if (StringUtils.isNotBlank(inputKey)) {
            try {
                execute(anActionEvent, selection, inputKey);
            } catch (StorageException e) {
                //TODO: Handle this better?
                e.printStackTrace();
            }
        }
    }

    @NotNull
    abstract GenerateDialog getSpecificGenerateDialog();

    GenerateDialog getGenerateDialog(boolean isCopy) {
        return new GenerateDialog(getEventProject(actionEvent), selection.getSelectedText(), isCopy);
    }

    private SelectionModel getSelection(AnActionEvent anActionEvent) {
        Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        return editor.getSelectionModel();
    }

    public abstract void execute(AnActionEvent anActionEvent, SelectionModel selectionModel, String inputKey) throws StorageException;
    //TODO: Remove unnecessary parameters
}
