
package seedu.address.logic.commands;

import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.UndoRedoCommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.UndoRedoCommandTestUtil.clearCache;

import org.junit.After;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.testutil.ModelGenerator;


public class UndoCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private UndoCommand undoCommand = new UndoCommand();
    private String messageSuccess = UndoCommand.MESSAGE_SUCCESS;
    private String messageFailure = UndoCommand.MESSAGE_FAILURE;

    @Test
    public void execute_defaultStateHasNothingToUndo() {
        Model model = ModelGenerator.getDefaultModel();
        assertCommandFailure(undoCommand, model, commandHistory, messageFailure);
    }

    @Test
    public void execute_singleUndo() {
        Model model = ModelGenerator.getModelWithOneTransformation();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 2);
    }

    @Test
    public void execute_successiveUndo() {
        Model model = ModelGenerator.getModelWithTwoTransformations();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 1, 3);
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 3);
    }

    @Test
    public void execute_successiveUndoWithPurge() {
        Model model = ModelGenerator.getModelWithThreeTransformations();
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 2, 4);
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 1, 4);
        assertCommandSuccess(undoCommand, model, commandHistory, messageSuccess, 0, 4);
        Model purgedModel = ModelGenerator.executeATransformation(model);
        assertCommandSuccess(undoCommand, purgedModel, commandHistory, messageSuccess, 0, 2);
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}

