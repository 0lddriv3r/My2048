package com.zhuojiaoshou.My2048;
//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/* 
 * Name: Xiaoqi Jiang 
 * Login: cs8bwade 
 * Date: Febr 2, 2016
 * File: Board.java 
 * Sources of Help: PPT of lecture and discussion, textbook
 * This file contains one class which provises board object for the game 
 * and it has a few methods to control the moves, save the board, add random 
 * tiles to the board and decide whether the game is over
 */


import java.util.*;
import java.io.*;


/*class name: Board.java
 * purpose: This class provises board object for the game 
 * and it has a few methods to control the moves, save the board, add random 
 * tiles to the board and decide whether the game is over*/
public class Board {
   //some final variables to decide the number and value of tiles and grid size
   public final int NUM_START_TILES = 2;
   public final int TWO_PROBABILITY = 90;
   public final int GRID_SIZE;

   /////////////////////////////fields//////////////////////////
   private final Random random;
   private int[][] grid;
   private int score;

   ///////////////////// constructors //////////////////////////////////

   /*
    *constructor that takes an integer and a random
    */
   public Board(int boardSize, Random random) {
      this.random = random; 
      GRID_SIZE = boardSize; 
      this.score=0;
      //initialize the 2D array
      this.grid=new int[boardSize][boardSize];
      //add random tiles to the start board
      for(int i=0;i<NUM_START_TILES;i++){
	 this.addRandomTile();
      }

   }

   /*
    * Constructor that takes an input board string and a random
    */
   public Board(String inputBoard, Random random) throws IOException {
      this.random = random; 
      //create a new file and read from the input file to get the board
      Scanner input = new Scanner(new File(inputBoard)); 
      //read the size and score
      GRID_SIZE = input.nextInt(); 
      this.score=input.nextInt();
      //create a new 2D array to save the input integers 
      int[][] grid0=new int[GRID_SIZE][GRID_SIZE];
      for(int i=0;i<grid0.length;i++){
	 for(int j=0;j<grid0[i].length;j++){
	    grid0[i][j]=input.nextInt();
	 }
      }
      //make the grid in the object to point to the new input array
      this.grid=grid0;
   }

   /////////////////////////Methods//////////////////////////////////

   /* name: saveBoard
    * Method to save the current board to a file
    * @param outputBoard - the string of the file to save the board
    */
   public void saveBoard(String outputBoard) throws IOException {
      //create a printwriter to write into the file
      PrintWriter writer=new PrintWriter(outputBoard);
      //write the size and score
      writer.println(GRID_SIZE);
      writer.println(this.score);
      //use a loop to write the array in the file
      for(int i=0;i<this.grid.length;i++){
	 for(int j=0;j<this.grid[i].length;j++){
	    writer.print(this.grid[i][j]+" ");
	 }
	 //print a new line
	 writer.println();
      }
      //close the writer
      writer.close();
   }


   /*name :addRandomTile
    * Method to add a random tile to an open spot on the game board
    */
   public void addRandomTile() {
      //create an integer to count all available tiles
      int count=0;
      for(int i=0;i<this.grid.length;i++){
	 for(int j=0;j<this.grid[i].length;j++){
	    if(this.grid[i][j]==0){
	       count++;
	    }
	 }
      }
      //return without change if there is no available tile
      if(count==0){
	 return;
      }
      //get a random int called location between 0 and count-1
      int location = this.random.nextInt(count);
      //get a random int called value between 0 and 99
      int value = this.random.nextInt(100);
      //recount of the empty spaces 
      count=0;
      for(int i=0;i<this.grid.length;i++){
	 for(int j=0;j<this.grid[i].length;j++){
	    if(this.grid[i][j]==0){
	       count++;
	       //When hit the i’th empty spot, place new tile
	       if((count-1)==location){
		  //if “value” is <TWO_PROBABILITY, place a 2 else place a 4 
		  if(value<TWO_PROBABILITY){
		     this.grid[i][j]=2;
		  }  
		  else{
		     this.grid[i][j]=4;
		  }
	       }
	    }
	 }
      }
   }

   /*name : rotate
    * Method to rotates the board by 90 degrees clockwise or counter-clockwise
    * @param rotateClockwise - the boolean to decide whether to 
    *                          rotate the board clockwise ,
    *                          else rotates counter-clockwise
    */
   public void rotate(boolean rotateClockwise) {
      //create a new array to save the board after rotation
      int[][] rotate=new int[GRID_SIZE][GRID_SIZE];
      //decide whether to rotate the board clockwise or counter-clockwise
      if(rotateClockwise){
	 //rotate the board clockwise 
	 for(int i=0;i<this.grid.length;i++){
	    for(int j=0;j<this.grid[i].length;j++){
	       rotate[j][GRID_SIZE-i-1]=this.grid[i][j];
	    }
	 }
      }
      //rotate counter-clockwise
      else{
	 for(int i=0;i<this.grid.length;i++){
	    for(int j=0;j<this.grid[i].length;j++){
	       rotate[GRID_SIZE-j-1][i]=this.grid[i][j];
	    }
	 }
      }
      //Make the grid of object point to the new array
      this.grid=rotate;
   }

   //Complete this method ONLY if you want to attempt at getting the extra credit
   //Returns true if the file to be read is in the correct format, else return
   //false
   public static boolean isInputFileCorrectFormat(String inputFile) {
      //The try and catch block are used to handle any exceptions
      //Do not worry about the details, just write all your conditions inside the
      //try block
      try {
	 //write your code to check for all conditions and return true if it satisfies
	 //all conditions else return false
	 return true;
      } catch (Exception e) {
	 return false;
      }
   }

   /*name : move
    * Method to achieve a move depending on the input direction
    * @param direction - the input Direction to decide the move 
    * @return boolean - whether the move is available
    */
   public boolean move(Direction direction) {
      if(direction.equals(Direction.UP)){
	 //using helper method to move up
	 this.moveUp();
	 return this.canMoveUp();
      }
      if(direction.equals(Direction.DOWN)){
	 //using helper method to move down
	 this.moveDown();
	 return this.canMoveDown();
      }
      if(direction.equals(Direction.LEFT)){
	 //using helper method to move left
	 this.moveLeft();
	 return this.canMoveLeft();
      }
      if(direction.equals(Direction.RIGHT)){
	 //using helper method to move right
	 this.moveRight();
	 return this.canMoveRight();
      }
      //this.saveBoard("board.out");
      return false;
   }


   /*name : isGameOver
    * Method to decide whether the game is over
    * @return boolean - whether the game is over
    */
   public boolean isGameOver() {
      //when move of any direction is available return false
      if(canMoveUp())
	 return false;
      if(canMoveDown())
	 return false;
      if(canMoveLeft())
	 return false;
      if(canMoveRight())
	 return false;
      //else return true
      return true;
   }

   /*name : canMove
    * Method to decide whether a move is available
    * @param direction - Direction to check its availability
    * @return boolean - whether the move is available
    */
   public boolean canMove(Direction direction) {
      if(direction.equals(Direction.UP)){
	 //Using helper method to check the availability
	 return this.canMoveUp();
      }
      if(direction.equals(Direction.DOWN)){
	 //Using helper method to check the availability
	 return this.canMoveDown();
      }
      if(direction.equals(Direction.LEFT)){
	 //Using helper method to check the availability
	 return this.canMoveLeft();
      }
      if(direction.equals(Direction.RIGHT)){
	 //Using helper method to check the availability
	 return this.canMoveRight();
      }
      return false;
   }

   /*name : moveUp
    * Method to move up
    */
   private void moveUp(){
      for(int j=0;j<GRID_SIZE;j++){
	 //put all non-zero number in an arrayList in each column
	 ArrayList<Integer> up=new ArrayList<Integer>(); 
	 for(int i=0;i<GRID_SIZE;i++){
	    if(grid[i][j]!=0){
	       up.add(grid[i][j]);
	    }
	 }
	 //go through the whole column 
	 for(int i=0;i<up.size()-1;i++){
	    //add identical numbers together and remove one
	    if(up.get(i).equals(up.get(i+1))){
	       this.score+=up.get(i)*2;
	       up.set(i,up.get(i)*2);
	       up.remove(i+1);
	    }
	 }
	 //put the ArrayList back to the grid
	 for(int i=0;i<up.size();i++){
	    grid[i][j]=up.get(i);
	 }
	 for(int i=up.size();i<GRID_SIZE;i++){
	    grid[i][j]=0;
	 }
      }
   }

   /*name : moveDown
    * Method to move down
    */      
   private void moveDown(){
      for(int j=0;j<GRID_SIZE;j++){
	 //put all non-zero number in an arrayList in each column
	 ArrayList<Integer> down=new ArrayList<Integer>();
	 for(int i=GRID_SIZE-1;i>=0;i--){
	    if(grid[i][j]!=0){
	       down.add(grid[i][j]);
	    }
	 }
	 //go through the whole column 
	 for(int i=0;i<down.size()-1;i++){
	    //add identical numbers together and remove one
	    if(down.get(i).equals(down.get(i+1))){
	       this.score+=down.get(i)*2;
	       down.set(i,down.get(i)*2);
	       down.remove(i+1);
	    }
	 }
	 //put the ArrayList back to the grid
	 for(int i=0;i<down.size();i++){
	    grid[GRID_SIZE-i-1][j]=down.get(i);
	 }
	 for(int i=0;i<GRID_SIZE-down.size();i++){
	    grid[i][j]=0;
	 }
      }
   } 

   /*name : moveLeft
    * Method to move left
    */ 
   private void moveLeft(){
      for(int i=0;i<GRID_SIZE;i++){
	 //put all non-zero number in an arrayList in each row
	 ArrayList<Integer> left=new ArrayList<Integer>();
	 for(int j=0;j<GRID_SIZE;j++){
	    if(grid[i][j]!=0){
	       left.add(grid[i][j]);
	    }
	 }
	 //go through the whole row 
	 for(int j=0;j<left.size()-1;j++){
	    //add identical numbers together and remove one
	    if(left.get(j).equals(left.get(j+1))){
	       this.score+=left.get(j)*2;
	       left.set(j,left.get(j)*2);
	       left.remove(j+1);
	    }
	 }
	 //put the ArrayList back to the grid
	 for(int j=0;j<left.size();j++){
	    grid[i][j]=left.get(j);
	 }
	 for(int j=left.size();j<GRID_SIZE;j++){
	    grid[i][j]=0;
	 }
      }
   } 

   /*name : moveRight
    * Method to move right
    */ 
   private void moveRight(){
      for(int i=0;i<GRID_SIZE;i++){
	 //put all non-zero number in an arrayList in each row
	 ArrayList<Integer> right=new ArrayList<Integer>();
	 for(int j=GRID_SIZE-1;j>=0;j--){
	    if(grid[i][j]!=0){
	       right.add(grid[i][j]);
	    }
	 }
	 //go through the whole row
	 for(int j=0;j<right.size()-1;j++){
	    //add identical numbers together and remove one
	    if(right.get(j).equals(right.get(j+1))){
	       this.score+=right.get(j)*2;
	       right.set(j,right.get(j)*2);
	       right.remove(j+1);
	    }
	 }
	 //put the ArrayList back to the grid
	 for(int j=0;j<right.size();j++){
	    grid[i][GRID_SIZE-j-1]=right.get(j);
	 }
	 for(int j=0;j<GRID_SIZE-right.size();j++){
	    grid[i][j]=0;
	 }
      }
   }

   /*name : canMoveUp
    * Method to decide whether an up move is available
    * @return boolean-whether the move is available
    */ 
   private boolean canMoveUp(){
      for(int j=0;j<GRID_SIZE;j++){
	 for(int i=0;i<GRID_SIZE-1;i++){
	    //return true if there are empty space between numbers
	    if(grid[i][j]==0){
	       for(int index=i+1;index<GRID_SIZE;index++){
		  if(grid[index][j]!=0){
		     return true;
		  }
	       }
	    }
	    //return true if there are identical numbers
	    if(grid[i][j]==grid[i+1][j]&&grid[i][j]!=0){
	       return true;
	    }
	 }
      }
      return false;
   }

   /*name : canMoveDown
    * Method to decide whether a down move is available
    * @return boolean-whether the move is available
    */ 
   private boolean canMoveDown(){
      for(int j=0;j<GRID_SIZE;j++){
	 for(int i=GRID_SIZE-1;i>0;i--){
	    //return true if there are empty space between numbers
	    if(grid[i][j]==0){
	       for(int index=i-1;index>=0;index--){
		  if(grid[index][j]!=0){
		     return true;
		  }
	       }
	    }
	    //return true if there are identical numbers
	    if(grid[i][j]==grid[i-1][j]&&grid[i][j]!=0){
	       return true;
	    }
	 }
      }
      return false;
   }

   /*name : canMoveLeft
    * Method to decide whether a left move is available
    * @return boolean-whether the move is available
    */ 
   private boolean canMoveLeft(){
      for(int i=0;i<GRID_SIZE;i++){
	 for(int j=0;j<GRID_SIZE-1;j++){
	    //return true if there are empty space between numbers
	    if(grid[i][j]==0){
	       for(int index=j+1;index<GRID_SIZE;index++){
		  if(grid[i][index]!=0){
		     return true;
		  }
	       }
	    }
	    //return true if there are identical numbers
	    if(grid[i][j]==grid[i][j+1]&&grid[i][j]!=0){
	       return true;
	    }
	 }
      }
      return false;
   }

   /*name : canMoveRight
    * Method to decide whether a right move is available
    * @return boolean-whether the move is available
    */ 
   private boolean canMoveRight(){
      for(int i=0;i<GRID_SIZE;i++){
	 for(int j=GRID_SIZE-1;j>0;j--){
	    //return true if there are empty space between numbers
	    if(grid[i][j]==0){
	       for(int index=j-1;index>=0;index--){
		  if(grid[i][index]!=0){
		     return true;
		  }
	       }
	    }
	    //return true if there are identical numbers
	    if(grid[i][j]==grid[i][j-1]&&grid[i][j]!=0){
	       return true;
	    }
	 }
      }
      return false;
   }

   /* public void undo() throws IOException{
      Board savedBoard=new Board("board.out", this.random);
      if(this.equals(savedBoard)){
      return;
      }
      else{
      this.grid = savedBoard.grid;
      this.score = savedBoard.score;
      }
      }

      private boolean equals(Board another){
      if(this.grid!=another.grid){
      return false;
      }
      if(this.score!=another.score){
      return false;
      }
      return true;
      }*/

   // Return the reference to the 2048 Grid
   public int[][] getGrid() {
      return grid;
   }

   // Return the score
   public int getScore() {
      return score;
   }

   @Override
      public String toString() {
	 StringBuilder outputString = new StringBuilder();
	 outputString.append(String.format("Score: %d\n", score));
	 for (int row = 0; row < GRID_SIZE; row++) {
	    for (int column = 0; column < GRID_SIZE; column++)
	       outputString.append(grid[row][column] == 0 ? "    -" :
		     String.format("%5d", grid[row][column]));

	    outputString.append("\n");
	 }
	 return outputString.toString();
      }
}
