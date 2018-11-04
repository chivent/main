package seedu.address.logic.commands.layer;

//@author j-lum
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;



/**
 * Handles the repositioning of Layers.
 */

public class LayerSwapCommand extends LayerCommand {
    private static final String TYPE = COMMAND_WORD + " swap";
    public static final String MESSAGE_USAGE = "Usage of layer swap: "
            + "\n- " + TYPE + " [TO] [FROM]: " + "Swaps the order of two distinct layers"
            + "\n\tExample: " + TYPE + " 1 3, swaps the order of the 1st and 3rd layer in the canvas.";

    private static final String OUTPUT_SUCCESS = "Layers %d and %d are now swapped.";
    private static final String OUTPUT_FAILURE = "Invalid index(es) provided!";

    private static final Logger logger = LogsCenter.getLogger(LayerSwapCommand.class);


    public LayerSwapCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) {
        String[] argumentArray = args.trim().split(" ", 2);

        int to;
        int from;
        Index toIndex;
        Index fromIndex;

        try {
            to = Integer.parseInt(argumentArray[0]);
            from = Integer.parseInt((argumentArray.length > 1) ? argumentArray[1] : null);
            if (to <= 0 || to > model.getCanvas().getLayers().size()
                    || from <= 0 || from > model.getCanvas().getLayers().size() || to == from) {
                throw new NumberFormatException(OUTPUT_FAILURE);
            }
            toIndex = Index.fromOneBased(to);
            fromIndex = Index.fromOneBased(from);

            model.getCanvas().swapLayer(toIndex, fromIndex);
        } catch (NumberFormatException e) {
            return new CommandResult(OUTPUT_FAILURE);
        } catch (IllegalOperationException e) {
            return new CommandResult("Unable to swap layers!");
        }

        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(String.format(OUTPUT_SUCCESS,
                model.getCanvas().getWidth(), model.getCanvas().getHeight()));
    }
}
