package com.vn.smartclipboard;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.util.indexing.StorageException;
import org.jetbrains.annotations.NotNull;

public class CopyExecutor extends AbstractCopyPasteExecutor {

    @NotNull
    @Override
    GenerateDialog getSpecificGenerateDialog() {
        return getGenerateDialog(true);
    }

    @Override
    public void execute(AnActionEvent anActionEvent, SelectionModel selectionModel, String inputKey) throws StorageException {
        if(!DataStorage.storeData(inputKey, selectionModel.getSelectedText())) throw new StorageException("Storage failed");
        //Looks bad.

        selectionModel.removeSelection();
    }
}
