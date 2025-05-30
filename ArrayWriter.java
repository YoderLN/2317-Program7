//***************************************************************
//
//  Developer:    Levi Yoder
//
//  Program #:    Seven
//
//  File Name:    ArrayWriter.java
//
//  Course:       ITSE 2317 Intermediate Java Programming
//
//  Due Date:     05/15/2025
//
//  Instructor:   Fred Kumi
//
//  Chapter:      23
//
//  Description:  modified version of source code from textbook
//                writes values to array, totals array contents
//                
//
//***************************************************************

import java.lang.Runnable;

public class ArrayWriter implements Runnable {
   private final SimpleArray sharedSimpleArray;
  

   public ArrayWriter(SimpleArray array) {
      sharedSimpleArray = array;
   }

   @Override
   public void run() {
      for (int i = 0; i < sharedSimpleArray.getArraySize(); i++) 
      {
    	  int num = (i % 10) +1 ; //generates number in cycle of 1-10
         sharedSimpleArray.add(num); //adds number to array
         
         
      } 
      //iterates through array to increment total with element value
      //instructions were unclear on whether to have this functionality separate from populating
      for (int i = 0; i < sharedSimpleArray.getArraySize(); i++) 
      {
    	  int num = sharedSimpleArray.getArrayAt(i);
    	  sharedSimpleArray.totalArrayIncrement(num); 
      }
      
   }
} 