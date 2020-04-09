/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gn.global.factory;

import com.gn.GNAvatarView;
import com.gn.decorator.component.GNControl;
import com.gn.global.factory.AlertCell;
import com.jfoenix.controls.JFXBadge;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/04/2020
 */
public class BadgeNotification extends GNControl {

    private PopOver pop = new PopOver();

    public BadgeNotification(){
        this(null, null);
    }

    public BadgeNotification(String text, String subtitle) {
        super(text, subtitle);
    }

    @Override
    public Node icon() {
        return null;
    }

    @Override
    public Node status() {
        return null;
    }

    @Override
    public Node action() {

        JFXBadge badgeM = new JFXBadge();
        badgeM.setPrefSize(35, 20);
        badgeM.setPosition(Pos.TOP_RIGHT);
        badgeM.getStyleClass().addAll("icon", "icon-warning");
        badgeM.setText("12");

        StackPane control = new StackPane();
        control.setAlignment(Pos.CENTER);
        control.getStyleClass().add("icon-notification");
        control.setStyle("-fx-padding : 0px;");
        control.setPrefSize(30,20);
        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.getStyleClass().add("icon");
        icon.setGlyphName("BELL");
        icon.setSize("16");
        control.getChildren().add(icon);

        badgeM.setControl(control);

        badgeM.setOnMouseClicked(event -> {
            openNotification();
        });

        return badgeM;
    }

    private void openNotification() {
        if (!pop.isShowing()) {
            GNAvatarView avatar1 = new GNAvatarView();
            GNAvatarView avatar2 = new GNAvatarView();
            GNAvatarView avatar3 = new GNAvatarView();

            avatar1.setImage(new Image(getClass().getResource("/com/gn/module/media/warning-35.png").toExternalForm()));
            avatar2.setImage(new Image(getClass().getResource("/com/gn/module/media/error-35.png").toExternalForm()));
            avatar3.setImage(new Image(getClass().getResource("/com/gn/module/media/notification-35.png").toExternalForm()));

            ObservableList<AlertCell> list = FXCollections.observableArrayList(
                    new AlertCell(avatar1, "Warning", "Lorem ipsum", "24 minutes ago"),
                    new AlertCell(avatar2, "Error", "Lorem ipsum", "today"),
                    new AlertCell(avatar3, "Notification", "Lorem", "3 seconds ago")
            );

            list.forEach(e -> {
                e.setOnMouseClicked( event -> pop.hide() );
                e.setCursor(Cursor.HAND);
            } );

            Separator top = new Separator();
            Separator bottom = new Separator();

            Label message = new Label("You have " + list.size() + " notifications");
            GridPane title = new GridPane();
           ;

            title.setAlignment(Pos.CENTER);
            title.add(message, 0, 0);
            title.setMinHeight(40);
//            title.add(count, 1, 0);
//            GridPane.setHalignment(count, HPos.RIGHT);

            ListView<AlertCell> listView = new ListView<>();
            int fixedCell = 50;
            listView.setStyle("-fx-fixed-cell-size : " + fixedCell + "px;");

            listView.setFixedCellSize(50);

            listView.getItems().addAll(list);
            listView.getStyleClass().add("border-0");

            Button btn = new Button("Read all messages");
            btn.getStyleClass().add("btn-flat");

            VBox root = new VBox(title, top, listView, bottom, btn);
            root.setAlignment(Pos.CENTER);

            double height = list.size() > 4 ? 300 : (list.size() * fixedCell) + title.getMinHeight() + 35; // 35 is equal min height in all app more padding

            root.setPrefSize(300, height);
            title.setPrefWidth(root.getPrefWidth());
//            count.setPrefWidth(root.getPrefWidth());
            message.setPrefWidth(root.getPrefWidth());
//            count.setAlignment(Pos.CENTER_RIGHT);
            title.setPadding(new Insets(0, 25, 0, 25));
            btn.setPrefWidth(root.getPrefWidth());

            listView.getStylesheets().add(getClass().getResource("/com/gn/theme/css/custom-scroll.css").toExternalForm());
            pop.getRoot().getStylesheets().add(getClass().getResource("/com/gn/theme/css/poplight.css").toExternalForm());
            pop.setContentNode(root);
            pop.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
            pop.setArrowIndent(0);
            pop.setArrowSize(0);
            pop.setCloseButtonEnabled(false);
            pop.setHeaderAlwaysVisible(false);
            pop.setCornerRadius(0);

            pop.show(this);

        } else {
            pop.hide();
        }
    }
}
