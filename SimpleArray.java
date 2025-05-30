//***************************************************************
//
//  Developer:    Levi Yoder
//
//  Program #:    Seven
//
//  File Name:    SimpleArray.java
//
//  Course:       ITSE 2317 Intermediate Java Programming
//
//  Due Date:     05/15/2025
//
//  Instructor:   Fred Kumi
//
//  Chapter:      23
//
//  Description:  Modified version of source code from textbook
//                
//                
//
//***************************************************************
import java.util.Arrays;

public class SimpleArray {
   private final int[] array; // the shared integer array
   private int writeIndex = 0; // index of next element to be written
   private int totalArray = 0;

   // construct a SimpleArray of a given size
   public SimpleArray(int size) {
      array = new int[size];
   } 

   // add a value to the shared array
   public synchronized void add(int value) {
      int position = writeIndex; // store the write index

     
      array[position] = value;
      ++writeIndex; // increment index of element to be written next
      
   } 
   //adds passed value to totalArray attribute
   public void totalArrayIncrement(int value)
   {
	   totalArray += value;
   }
   //getters for class attributes
   public int getArraySize()
   {
	   return array.length;
   }
   public int getTotalArray()
   {
	   return totalArray;
   }
   
   public int getArrayAt(int position)
   {
	   return array[position];
   }
   
   // used for outputting the contents of the shared integer array
   @Override
   public synchronized String toString() {
      return Arrays.toString(array);
   } 
}