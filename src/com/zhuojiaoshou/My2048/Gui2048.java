package com.zhuojiaoshou.My2048;
/*
 * Name: Xiaoqi Jiang 
 * Login: cs8bwade 
 * Date: Febr 2, 2016
 * File: Gui2048.java 
 * Sources of Help: PPT of lecture and discussion, textbook
 * This file contains a Gui2048 class and an inner class. The Gui2048 class
 *  shows the score, the name of the game (2048), 
 * the tiles with values (and colors which change with values).  
 * It is also going to display “Game Over” when the game ends.
 */


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/*class name: Gui2048
 * purpose: This class shows the score, the name of the game (2048), 
 * the tiles with values (and colors which change with values). */

public class Gui2048 extends Application
{
   /////////////////////////////fields//////////////////////////
   private String outputBoard; // The filename for where to save the Board
   private Board board; // The 2048 Game Board

   private static final int TILE_WIDTH = 106;

   private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
   private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
   //(128, 256, 512)
   private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
   //(1024, 2048, Higher)

   // Fill colors for each of the Tile values
   private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
   private static final Color COLOR_2 = Color.rgb(238, 228, 218);
   private static final Color COLOR_4 = Color.rgb(237, 224, 200);
   private static final Color COLOR_8 = Color.rgb(242, 177, 121);
   private static final Color COLOR_16 = Color.rgb(245, 149, 99);
   private static final Color COLOR_32 = Color.rgb(246, 124, 95);
   private static final Color COLOR_64 = Color.rgb(246, 94, 59);
   private static final Color COLOR_128 = Color.rgb(237, 207, 114);
   private static final Color COLOR_256 = Color.rgb(237, 204, 97);
   private static final Color COLOR_512 = Color.rgb(237, 200, 80);
   private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
   private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
   private static final Color COLOR_OTHER = Color.BLACK;
   private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.5);

   private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
   // For tiles >= 8

   private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
   // For tiles < 8

   private GridPane pane;

   //My own Instance Variables 
   private int tile;
   private Rectangle[][] rectangle;
   private Text[][] text;
   private Text txt0;
   private Text txtScore;
   private int[][] grid;
   private StackPane pane0;
   private double title;



   /////////////////////////Methods//////////////////////////////////

   /* name: start
    * Method to initialize the pane for 2048 game and then repaint everytime
    * the pane changes
    * @param primaryStage - the original stage to put things on
    */
   @Override
      public void start(Stage primaryStage)
      {
  // Process Arguments and Initialize the Game Board
  processArgs(getParameters().getRaw().toArray(new String[0]));

  // Create the pane that will hold all of the visual objects
  pane = new GridPane();
  pane.setAlignment(Pos.CENTER);
  pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
  pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
  // Set the spacing between the Tiles
  pane.setHgap(15); 
  pane.setVgap(15);


  int size=board.GRID_SIZE;
  grid=new int[size][size];
  grid=board.getGrid();//copy the grid in the board 

  //add the title 2048 on the upper left corner
  txt0=new Text();
  txt0.setText("2048");
  txt0.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
  txt0.setFill(Color.BLACK);
  //add the score of the game
  txtScore=new Text();
  txtScore.setText("score: "+this.board.getScore());
  txtScore.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
  txtScore.setFill(Color.BLACK);
  
  //add these two to the pane
  pane.add(txt0,0,0,2,1);
  pane.add(txtScore,2,0,size-2,1);
  //place all the text in the middle 
  GridPane.setHalignment(txt0, HPos.CENTER);
  GridPane.setHalignment(txtScore, HPos.CENTER);

  //initialize the arrays of rectangles and text for the grid part
  rectangle = new Rectangle[size][size];
  text = new Text[size][size];
  
  title = pane.getHeight()-pane.getWidth();
  //set the rectangles and text based on the information in the grid one by one
  for(int row=0;row<size;row++){
     for(int col=0;col<size;col++){
        tile = grid[row][col];//get the numbers of the grid

        //create a rectangle for each number
        rectangle[row][col]=new Rectangle();
        rectangle[row][col].setWidth(TILE_WIDTH);
        rectangle[row][col].setHeight(TILE_WIDTH);
        rectangle[row][col].setFill(getColor(tile));
        //add the rectangle starting from the second row
        pane.add(rectangle[row][col],col,row+1);

        //create a text for each number
        text[row][col]=new Text();
        if(tile==0){
    text[row][col].setText("");//empty for zero
        }else{
    text[row][col].setText(Integer.toString(tile));
        }
        int txt=getSize(tile);//get the size of the txt
        text[row][col].setFont(Font.font("Times New Roman",FontWeight.BOLD,txt));
        text[row][col].setFill(getTextColor(tile));
        pane.add( text[row][col],col,row+1);
        GridPane.setHalignment( text[row][col], HPos.CENTER);
        
        /*rectangle[row][col].widthProperty().bind(pane.widthProperty().
            subtract(pane.getHgap()*(board.GRID_SIZE-1)).
            divide(board.GRID_SIZE));
          rectangle[row][col].heightProperty().bind(pane.heightProperty().
            subtract(title).subtract(pane.getVgap()*
              (board.GRID_SIZE)).
            divide(board.GRID_SIZE+1));   */  
        

     }
  }
 /* pane.vgapProperty().bind(pane.heightProperty().
       divide(23*size-3));
     pane.hgapProperty().bind(pane.widthProperty().
       divide(23*size-3));*/
        
  
  pane0=new StackPane();//create a stackpane
  pane0.getChildren().add(pane);//place the pane on the stackpane
  Scene scene = new Scene(pane0);

  scene.setOnKeyPressed(new myKeyHandler());//handle the game

  primaryStage.setTitle("Gui2048");
  primaryStage.setScene(scene);
  primaryStage.show();




      }

   /*class name: EventHandler.java
    * purpose: This class inner class can handle the commands from the player
    * and use keycode to get them*/
   private class myKeyHandler implements EventHandler<KeyEvent>  
   {     
      public void handle(KeyEvent e)   
      {
  if(e.getCode().equals(KeyCode.UP)){
     //if the move is available then move 
     if(board.canMove(Direction.UP)){
        board.move(Direction.UP);
        System.out.println("Moving Up");
        board.addRandomTile();}
        //check if the game is over
        if(board.isGameOver()){
    gameOver();
        }
  }else if(e.getCode().equals(KeyCode.DOWN)){
     //if the move is available then move  
     if(board.canMove(Direction.DOWN)){
        board.move(Direction.DOWN);
        System.out.println("Moving Down");
        board.addRandomTile();
     }
     //check if the game is over
     if(board.isGameOver()){
        gameOver();
     }
  }else if(e.getCode().equals(KeyCode.LEFT)){
     //if the move is available then move  
     if(board.canMove(Direction.LEFT)){
        board.move(Direction.LEFT);
        System.out.println("Moving Left");
        board.addRandomTile();}
        //check if the game is over
        if(board.isGameOver()){
    gameOver();
        }
  }else if(e.getCode().equals(KeyCode.RIGHT)){
     //if the move is available then move  
     if(board.canMove(Direction.RIGHT)){
        board.move(Direction.RIGHT);
        System.out.println("Moving Right");
        board.addRandomTile();}
        //check if the game is over
        if(board.isGameOver()){
    gameOver();
        }
        //rotate the board if pressing R
  }else if(e.getCode().equals(KeyCode.R)){
     board.rotate(true);
     System.out.println("Rotate 90 degrees clockwise"); 
     //save the board if pressing S
  }else if(e.getCode().equals(KeyCode.S)){  
     System.out.println("Saving Board to " + outputBoard);
     try {
        board.saveBoard(outputBoard);
     } catch (IOException yingjun) {
        System.out.println("saveBoard threw an Exception");
     }
  }

  paint();//repaint the pane after a move

      }
   }


   /*name :gameOver
    * Method to add rectangle and text to show the game is over
    */
   private void gameOver(){
      //create a rectangle as large as the pane  
      Rectangle rec = new Rectangle();
      rec.setFill(COLOR_GAME_OVER);
      rec.setWidth(pane.getWidth());
      rec.setHeight(pane.getHeight());

      //create a text for the game over message
      Text over = new Text();
      over.setText("Game Over!");
      over.setFont(Font.font("Impact", FontWeight.BOLD, 50));  
      over.setFill(Color.BLACK);

      //add to the stackpane
      pane0.getChildren().add(rec);
      pane0.getChildren().add(over);

   }   




   /*name :paint
    * Method to repaint the pane after a move
    */
   private void paint(){
      int size=board.GRID_SIZE;
      grid=board.getGrid();

      txtScore.setText("score: "+this.board.getScore());//update the score

      //reset all the rectangles and text after a move
      for(int row=0;row<size;row++){
  for(int col=0;col<size;col++){
     tile = grid[row][col];
     rectangle[row][col].setFill(getColor(tile));
     if(tile==0){
        text[row][col].setText("");
     }else{
        text[row][col].setText(Integer.toString(tile));
     }
     int txt=getSize(tile);
     text[row][col].setFont(Font.font("Times New Roman",FontWeight.BOLD,txt));
     text[row][col].setFill(getTextColor(tile));

  }
      }

   }


   /*name :getColor
    * Method to get the color of a rectangle 
    * @param: integer - the number to decide the color
    * @return: the color of a rectangle 
    */   
   private Color getColor(int num){
      if(num==0)
  return COLOR_EMPTY;
      if(num==2)
  return COLOR_2;
      if(num==4)
  return COLOR_4;
      if(num==8)
  return COLOR_8;
      if(num==16)
  return COLOR_16;
      if(num==32)
  return COLOR_32;
      if(num==64)
  return COLOR_64;
      if(num==128)
  return COLOR_128;
      if(num==256)
  return COLOR_256;
      if(num==512)
  return COLOR_512;
      if(num==1024)
  return COLOR_1024;
      if(num==2048)
  return COLOR_2048;

      return COLOR_OTHER;
   }

   /*name :getTextColor
    * Method to get the color of a text
    * @param: integer - the number to decide the color
    * @return: the color of a text
    */ 
   private Color getTextColor(int num){
      if(num<8){
  return COLOR_VALUE_DARK;
      }else{
  return COLOR_VALUE_LIGHT;
      }
   }

   /*name :getSize
    * Method to get the size of a text
    * @param: integer - the number to decide the size
    * @return: the color of a text 
    */   
   private int getSize(int num){
      if(num<128){
  return TEXT_SIZE_LOW;
      }else if(num<1024){
  return TEXT_SIZE_MID;
      }else{
  return TEXT_SIZE_HIGH;
      }
   }

   /** DO NOT EDIT BELOW */

   // The method used to process the command line arguments
   private void processArgs(String[] args)
   {
      String inputBoard = null;   // The filename for where to load the Board
      int boardSize = 0;          // The Size of the Board

      // Arguments must come in pairs
      if((args.length % 2) != 0)
      {
  printUsage();
  System.exit(-1);
      }

      // Process all the arguments 
      for(int i = 0; i < args.length; i += 2)
      {
  if(args[i].equals("-i"))
  {   // We are processing the argument that specifies
     // the input file to be used to set the board
     inputBoard = args[i + 1];
  }
  else if(args[i].equals("-o"))
  {   // We are processing the argument that specifies
     // the output file to be used to save the board
     outputBoard = args[i + 1];
  }
  else if(args[i].equals("-s"))
  {   // We are processing the argument that specifies
     // the size of the Board
     boardSize = Integer.parseInt(args[i + 1]);
  }
  else
  {   // Incorrect Argument 
     printUsage();
     System.exit(-1);
  }
      }

      // Set the default output file if none specified
      if(outputBoard == null)
  outputBoard = "2048.board";
      // Set the default Board size if none specified or less than 2
      if(boardSize < 2)
  boardSize = 4;

      // Initialize the Game Board
      try{
  if(inputBoard != null)
     board = new Board(inputBoard, new Random());
  else
     board = new Board(boardSize, new Random());
      }
      catch (Exception e)
      {
  System.out.println(e.getClass().getName() + 
        " was thrown while creating a " +
        "Board from file " + inputBoard);
  System.out.println("Either your Board(String, Random) " +
        "Constructor is broken or the file isn't " +
        "formated correctly");
  System.exit(-1);
      }
   }

   // Print the Usage Message 
   private static void printUsage()
   {
      System.out.println("Gui2048");
      System.out.println("Usage:  Gui2048 [-i|o file ...]");
      System.out.println();
      System.out.println("  Command line arguments come in pairs of the "+ 
     "form: <command> <argument>");
      System.out.println();
      System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
     "should be loaded");
      System.out.println();
      System.out.println("  -o [file]  -> Specifies a file that should be " + 
     "used to save the 2048 board");
      System.out.println("                If none specified then the " + 
     "default \"2048.board\" file will be used");  
      System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
     "board if an input file hasn't been"); 
      System.out.println("                specified.  If both -s and -i" + 
     "are used, then the size of the board"); 
      System.out.println("                will be determined by the input" +
     " file. The default size is 4.");
   }
}
