package com.theblind.privatenotes.action;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupListener;
import com.intellij.openapi.ui.popup.LightweightWindowEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import com.theblind.privatenotes.core.util.IdeaApiUtil;
import com.theblind.privatenotes.core.util.PrivateNotesUtil;
import com.theblind.privatenotes.ui.PrivateNotesEditorForm;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 *换行
 */
public class WrapNoteIntentionAction extends BaseIntentionAction {

    public WrapNoteIntentionAction() {
        super("[Note] 换行");
    }


    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        try {
            VirtualFile virtualFile = psiFile.getVirtualFile();
            return noteFileService.noteExist(virtualFile.getCanonicalPath(),
                    editor.getDocument().getLineNumber(editor.getCaretModel().getOffset()), IdeaApiUtil.getBytes(virtualFile));
        } catch (Exception e) {
            PrivateNotesUtil.errLog(e, project);
        }
        return false;
    }


    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        PrivateNotesEditorForm remarkEditor = new PrivateNotesEditorForm();

        JEditorPane editorPane = remarkEditor.getEditorPane1();
        Integer selLineNumber = IdeaApiUtil.getSelLineNumber(editor);
        VirtualFile virtualFile = psiFile.getVirtualFile();
        try {
            noteFileService.wrapNote(virtualFile.getCanonicalPath(), selLineNumber
                    , IdeaApiUtil.getBytes(virtualFile));
        } catch (Exception e) {
            PrivateNotesUtil.errLog(e, project);
        }
        editorPane.requestFocus();

    }
}