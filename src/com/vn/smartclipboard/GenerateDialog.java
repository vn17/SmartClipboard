package com.vn.smartclipboard;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GenerateDialog extends DialogWrapper {

    //    final LabeledComponent<JPanel> component;
//    final String selection;
    private final String inputKey;
//    private CollectionListModel<PsiField> myField;

    GenerateDialog(Project project, String selection, boolean isCopy) {
        super(project);
//        this.selection = selection;
//        setTitle("Test");
//        myField = new CollectionListModel<>();
//        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(new JList());
//        JPanel panel = decorator.createPanel();
//
//        component = LabeledComponent.create(panel, "Hello IJ");
        String message;
        if (isCopy) {
            message = "Enter the key with which you would like to store this selection";
        } else message = "Enter the key for which you want to retrieve the stored value";
        inputKey = Messages.showInputDialog(message, "SmartClipboard", null, null, getInputValidator(isCopy));
    }

    String getInputKey() {
        return inputKey;
    }

    private InputValidator getInputValidator(boolean isCopy) {
        if (isCopy) {
            return new InputValidatorEx() {
                @Nullable
                @Override
                public String getErrorText(String s) {
                    if (s.length() == 0) {
                        return "Key cannot be empty";
                    }
                    if (DataStorage.allSlotsTaken()) {
                        return "Maximum limit for number of slots reached";
                    } else return DataStorage.keyExists(s) ? "Key '" + s + "' already in use" : null;
                }

                @Override
                public boolean checkInput(String s) {
                    return !DataStorage.keyExists(s);
                }

                @Override
                public boolean canClose(String s) {
                    return true;
                }
            };
        } else {
            return new InputValidatorEx() {
                @Nullable
                @Override
                public String getErrorText(String s) {
                    if (s.length() == 0) {
                        return "Key cannot be empty";
                    }
                    return checkInput(s) ? null : "Key '" + s + "' does not exist";
                }

                @Override
                public boolean checkInput(String s) {
                    return DataStorage.keyExists(s);
                }

                @Override
                public boolean canClose(String s) {
                    return true;
                }
            };
        }
    }

    @Nullable
    @Override
//    protected JComponent createCenterPanel() {
//        return component;
//    }
    protected JComponent createCenterPanel() {
        return null;
    }
}
