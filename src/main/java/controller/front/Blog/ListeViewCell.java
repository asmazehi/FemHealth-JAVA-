package controller.front.Blog;

import javafx.scene.control.ListCell;

public class ListeViewCell extends ListCell<String>
{
    @Override
    public void updateItem(String item, boolean empty)
    {
        super.updateItem(item,empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item);
        }
    }
}