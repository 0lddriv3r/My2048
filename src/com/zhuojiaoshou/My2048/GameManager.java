package com.zhuojiaoshou.My2048;
//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/*
 * Name: Xiaoqi Jiang
 * Login: cs8bwade
 * Date: Jan 28, 2016
 * File: GameManager.java
 * Sources of Help: PPT of lecture and discussion, textbook
 * This file contains a class to manage the game: to move up, down, left, right 
 * and quit the game and it decides whether the input contril is available 
 * and when the game is over
 */

import java.util.*;
import java.io.*;

/*class name: GameManager.java
 * purpose: This class is to manage the game: to move up, down, left, right 
 * and quit the game and it decides whether the input contril is available 
 * and when the game is over*/

public class GameManager {
   // Instance variables
   private Board board; // The actual 2048 board
   private String outputFileName; // File to save the board to when exiting

   /////////////////////////////constructors//////////////////////////////
   /* Constructor that Generates new game
    * It takes an integer of gridsize, a string of the file name to save the 
    * board and a random*/
   public GameManager(int boardSize, String outputBoard, Random random) {
      //initialize the board with size and random
      this.board=new Board(boardSize, random);
      //initialize the string with the file name to save the board
      this.outputFileName=outputBoard; 
      System.out.println("Generating a New Board");

   }

   /* Constructor that  Load a saved game
    * It takes a string of the file name of the saved game, a string of the file name to 
    * save the board and a random*/
   public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
      //initialize the board with strings of input  and random
      this.board=new Board(inputBoard, random);
      //initialize the string with the file name to save the board
      this.outputFileName=outputBoard; 
      System.out.println("Loading Board from " + inputBoard);
   }

   /*name: play
    * Method to manage the game: to move up, down, left, right
    * and quit the game and it decides whether the input contril is available
    * and when the game is over*/ 
   public void play() throws IOException {
      //print controls and show the start board 
      printControls();
      int[][] grid = this.board.getGrid();
      System.out.println(this.board.toString());
      //create a scanner
      Scanner input=new Scanner(System.in);
      File save=new File("board.out");
      //Keep looping until the game is over
      while(!(this.board.isGameOver())){
	 //read a valid command
	 String s =input.next();
	 s=getValidInput(s);
	 //save and quit the game when input q
	 if(s.equals("q")){
	    this.board.saveBoard(this.outputFileName);
	    return;
	 }
	 //get the direction using the input string
	 Direction dir;
	 dir=getDirection(s);
	 //decide whether this move is available
	    if(this.board.canMove(dir)){
	    //move and add random tile when the move is available
	    this.board.move(dir);
	    this.board.addRandomTile();
	    //show the new board 
	    System.out.println(this.board.toString());
	    }
	 //print out the warning and print controls again 
	 //when the move is unavailable
	    else{ 
	    System.out.println("This move is invalid");
	    printControls();
	    }
      }
      //save board and print out information when the game is over
      this.board.saveBoard(this.outputFileName);
      System.out.println("Game Over!");
   }

   /*name:invalidInput
    * Method to decide whether the input command is valid
    * @param s-the string of the command
    * @return result - the boolean of whether the input is invalid*/
   private boolean invalidInput(String s){
      boolean a,b,c,d,e,f,result;
      a=s.equals("q");
      b=s.equals("h");
      c=s.equals("j");
      d=s.equals("k");
      e=s.equals("l");
      result=!(a||b||c||d||e);
      return result;
   }

   /*name:getValidInput
    * Method to loop until getting a valid input
    * @param s-the string of the command
    * @return s - the valid string of command*/
   private String getValidInput(String s){ 
      Scanner input=new Scanner(System.in);
      //keep looping until getting a valid input
      while(invalidInput(s)){
	 System.out.println("This is not a valid input");
	 //when the input is invalid print controls again
	 printControls();
	 s = input.next();
      }
      return s;
   }
   /*name:getDirection
    * Method to get the direction through the string
    * @param s-the string of the command
    * @return dir - corresponding Direction of the string*/
   private Direction getDirection(String s){
      Direction dir;
      //check which direction the string is related to 
      if(s.equals("k")){
	 dir=Direction.UP;
      }
      else if(s.equals("j")){
	 dir=Direction.DOWN;
      }
      else if(s.equals("h")){
	 dir=Direction.LEFT;
      }
      else if(s.equals("l")){
	 dir=Direction.RIGHT;
      }
      else{
	 return null;
      }
      //return the direction
      return dir;
   }

   /*name: printControls
    * Method to print the Controls for the Game*/
   private void printControls() {
      System.out.println("  Controls:");
      System.out.println("    k - Move Up");
      System.out.println("    j - Move Down");
      System.out.println("    h - Move Left");
      System.out.println("    l - Move Right");
      System.out.println("    q - Quit and Save Board");
      System.out.println();
   }
}
