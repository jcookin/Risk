package Game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Board extends BorderPane {
    GridPane gpColors = new GridPane();
    GridPane gpTroops = new GridPane();
    GridPane gpSelection = new GridPane();
    Rectangle[][] boardColors;
    Label[][] boardTroops;
    Rectangle[][] boardSelections = initSelection();
    StackPane sp = new StackPane();
    Color[][] colors;
    Integer[][] integerTroops;


    public Board(int turn, Color[][] colors){
        drawTitle(turn);
        drawBoard(colors);
        drawButtons();
    }

    public void drawTitle(int turn){
        String who = turn == 0 ? "Blue" : "Red";
        Label lb = new Label(who + " player's turn");
        setTop(lb);
        lb.setFont(Font.font("SansSerif", 30));
        setAlignment(lb, Pos.CENTER);
    }

    public void drawButtons(){
        HBox pn = new HBox();
        Button place = new Button("Place");
        Button attack = new Button("Attack");
        Button move = new Button("Move");
        Button endTurn = new Button("End Turn");
        TextField("Troop #");
        pn.getChildren().addAll(place, attack, endTurn, move);
        setBottom(pn);
    }

    private void TextField(String s) {
    }

    public void drawBoard(Color[][] colors) {
        boardColors = getColors(colors);
        boardTroops = getTroops();
        boardSelections = getSelections();

        for (int i = 0; i < boardColors.length; i++) {
            for (int j = 0; j < boardColors[i].length; j++) {
//                StackPane tmpStack = new StackPane();
//                tmpStack.getChildren().addAll(boardColors[i][j], boardTroops[i][j], boardSelections[i][j]);
//                gpColors.add(tmpStack, j, i);
                gpColors.add(boardColors[i][j], j, i);
                gpTroops.add(boardTroops[i][j], j, i);
                gpSelection.add(boardSelections[i][j], j, i);


                //TODO: align the gpTroops objs to center on the gpColor objs


            }
        }

        gpTroops.setAlignment(Pos.CENTER);
        gpTroops.setVgap(36);
        gpTroops.setHgap(41);
        sp.getChildren().addAll(gpColors, gpTroops, gpSelection);
        setCenter(sp);
    }

    private Rectangle[][] getSelections() {
        EventHandler<MouseEvent> clickedBox = e -> {
            Rectangle r = (Rectangle)e.getSource();
            int row = gpColors.getRowIndex(r);
            int col = gpColors.getColumnIndex(r);
            //give clicked result to something
            System.out.println("This box was clicked: " + row + ", " + col + "\nWith color Value: " + boardColors[row][col]);    //for testing purposes
            highlightSelection(row, col);
        };

        Rectangle[][] clickRect = new Rectangle[10][10];
        for (int i=0; i < colors.length; i++) {
            for (int j=0; j < colors[i].length; j++) {
                Rectangle r = new Rectangle(50, 50);
                r.setFill(Color.TRANSPARENT);
                r.setStroke(Color.TRANSPARENT);
                if (colors[i][j] != Color.BLACK) r.setOnMouseClicked(clickedBox);   //can't click black tiles
                clickRect[i][j] = r;
            }
        }
        return clickRect;
    }

    private Rectangle[][] getColors(Color[][] colors) {      //makes regions clickable
        Rectangle[][] coloredRects = new Rectangle[10][10];
        for (int i=0; i < colors.length; i++) {
            for (int j=0; j < colors[i].length; j++) {
                Rectangle r = new Rectangle(50, 50);
                r.setFill(colors[i][j]);
                if (colors[i][j] != Color.BLACK) r.setStroke(Color.ORANGE);
                else r.setStroke(Color.BLACK);
                coloredRects[i][j] = r;
            }
        }
        return coloredRects;
    }

    public Label[][] getTroops() {     //makes Integers Node objects to put in gpTroops to stack on gpColors
        Label[][] t = new Label[10][10];
        for (int i=0; i<integerTroops.length; i++) {
            for (int j=0; j<integerTroops[i].length; j++) {
                t[i][j] = new Label(Integer.toString(integerTroops[i][j]));
            }
        }
        return t;
    }


    public static Integer[][] initTroops() {
        Integer[][] troops = new Integer[10][10];
        for (int i=0; i<troops.length; i++) {
            for (int j=0; j<troops[i].length; j++) {
                troops[i][j] = 0;
            }
        }
        return troops;
    }   //integer troops initializer to 0;


    private void highlightSelection(int i, int j) {
        boardColors[i][j].setStroke(Color.DARKBLUE);
    }

    private Rectangle[][] initSelection() {
        Rectangle[][] r = new Rectangle[10][10];
        //i>r.length?
        for (Rectangle[] rectangles : r) {
            for (Rectangle rectangle : rectangles) {
                //rectangle.setStroke(Color.TRANSPARENT);
                //rectangle.setFill(Color.TRANSPARENT);
            }
        }
        return r;
    }
}
