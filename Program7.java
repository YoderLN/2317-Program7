//***************************************************************
//
//  Developer:    Levi Yoder
//
//  Program #:    Seven
//
//  File Name:    Program7.java
//
//  Course:       ITSE 2317 Intermediate Java Programming
//
//  Due Date:     05/15/2025
//
//  Instructor:   Fred Kumi
//
//  Chapter:      23
//
//  Description:  Driver class to fulfil task of creating arrays
//                with 15 million elements, using pre-determined
//                values to populate arrays, and then displaying
//                array content totals and execution time
//                
//
//***************************************************************

import java.util.concurrent.Executors;
import java.time.Instant;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Program7 {
	
	//***************************************************************
	//
	//  Method:       main
	// 
	//  Description:  The main method of the program
	//
	//  Parameters:   A String Array
	//
	//  Returns:      N/A 
	//
	//**************************************************************
   public static void main(String[] arg) {
      //given size requirement for program
	  final int SIZE = 15_000_000;
	  
      Program7 obj = new Program7();
      obj.developerInfo();
      
      //obtaining and displaying cores available on system
      int coreCount = Runtime.getRuntime().availableProcessors();
	  System.out.println("Cores available: " + coreCount);
	  
	  //commented out section for testing data consistency
	  //Observation is that initial tests are far slower than later ones
	  //Theory is the initial loading of classes slows first iterations
	  
//	  for(int i = 1; i <= 10; i++) 
//	  {
//      System.out.println("\n\nTrial number: " + i);
//      //multi-threaded happens here
//      Instant parallelStart = Instant.now();
//	  obj.multiThreadedArray(SIZE, coreCount);
//	  Instant parallelEnd = Instant.now();
//	  
//	  
//
//		//single threaded happens here
//	  Instant executeStart = Instant.now();
//	  int singleResult = obj.singleThreadedArray(SIZE);
//	  Instant executeEnd = Instant.now();
//	  System.out.println("Single-Threaded result: " + singleResult);
//	  
//	  
//	  obj.compareTimes(parallelStart, parallelEnd, executeStart, executeEnd);
//	  }
	  	  
	  
	  	//commented out reverse order, left for easy testing
//		//multi-threaded happens here
//    Instant parallelStart = Instant.now();
//	  obj.multiThreadedArray(SIZE, coreCount);
//	  Instant parallelEnd = Instant.now();
//	  
//		//single threaded happens here
//	  Instant executeStart = Instant.now();
//	  int singleResult = obj.singleThreadedArray(SIZE);
//	  Instant executeEnd = Instant.now();
//	  System.out.println("Single-Threaded result: " + singleResult);
//	  
//    
//	  
//	  obj.compareTimes(parallelStart, parallelEnd, executeStart, executeEnd);
	  
	  
	    
		//single threaded happens here
	  Instant executeStart = Instant.now();
	  int singleResult = obj.singleThreadedArray(SIZE);
	  Instant executeEnd = Instant.now();
	  System.out.println("Single-Threaded result: " + singleResult);
	  
      //multi-threaded happens here
      Instant parallelStart = Instant.now();
	  obj.multiThreadedArray(SIZE, coreCount);
	  Instant parallelEnd = Instant.now();
	  
	  obj.compareTimes(parallelStart, parallelEnd, executeStart, executeEnd);
   }
 //***************************************************************
   //
   //  Method:       multiThreadedArray
   // 
   //  Description:  populates array and then sums using
   //                multiple threads
   //
   //  Parameters:   constant array size for task
   //
   //  Returns:      N/A 
   //
   //**************************************************************
   public void multiThreadedArray(int SIZE, int coresToUse)
   {
	    
	   SimpleArray sharedSimpleArray = new SimpleArray(SIZE);
	   ExecutorService multi = Executors.newFixedThreadPool(coresToUse);
	   ArrayWriter writer1= new ArrayWriter(sharedSimpleArray);
	   
	   multi.execute(writer1);
	   multi.shutdown();
	   try 
	   		{
	         // wait 1 minute for both writers to finish executing
	         boolean tasksEnded =                                     
	            multi.awaitTermination(1, TimeUnit.MINUTES);

	         if (tasksEnded) 
	         	{
	        	 System.out.println("Multi-Threaded completed succesfully.");
	        	 System.out.println("Multi-Threaded result: " + sharedSimpleArray.getTotalArray());
	         	}   
	         else 
	         	{
	        	 System.out.println(
	               "Timed out while waiting for tasks to finish.");
	         	} 
	   		} 
	      catch (InterruptedException ex) 
	   		{
	         ex.printStackTrace();
	   		}	
	   
	   //return sharedSimpleArray.getTotalArray();
	   
   }
 //***************************************************************
   //
   //  Method:       singleThreadedArray
   // 
   //  Description:  populates array and then sums contents
   //                using single thread
   //
   //  Parameters:   constant array size for task
   //
   //  Returns:      N/A 
   //
   //**************************************************************
   public int singleThreadedArray(int SIZE)
   {
	   SimpleArray simpleArray = new SimpleArray(SIZE);
	   ExecutorService single = Executors.newFixedThreadPool(1);
	   ArrayWriter writer2 = new ArrayWriter(simpleArray);

	   single.execute(writer2);
	   single.shutdown();
	   
	   try 
	   		{
	         // wait 1 minute for both writers to finish executing
	         boolean tasksEnded =                                     
	            single.awaitTermination(1, TimeUnit.MINUTES);

	         if (tasksEnded) 
	         {
	        	 System.out.println("Single-Threaded completed successfully."); 
	         }   
	         else 
	         	{
	        	 System.out.println(
	        			 "Timed out while waiting for tasks to finish.");
	         	} 
	   		} 
	      catch (InterruptedException ex) 
	   		{
	         ex.printStackTrace();
	   		}
	   return simpleArray.getTotalArray(); 
   }
 //***************************************************************
   //
   //  Method:       compareTimes
   // 
   //  Description:  compares time taken to execute tasks
   //
   //  Parameters:   Instants representing start and endpoints for both
   //                multi-threaded and single-threaded functions
   //
   //  Returns:      N/A 
   //
   //**************************************************************
   public void compareTimes(Instant parallelStart, Instant parallelEnd, 
		   Instant executeStart, Instant executeEnd)
   {
	   
	   long multiTime = Duration.between(parallelStart, parallelEnd).toMillis();
	   long singleTime = Duration.between(executeStart, executeEnd).toMillis(); 
	   
	   System.out.printf("%n%s: %d ms%n%s: %d ms%n", 
			   "Multi-Threaded time to complete", multiTime,
			   "Single-Threaded time to complete", singleTime);
	   
	   if (multiTime < singleTime)
	   {
		   System.out.printf("%nMulti-threaded faster by %d ms.", (singleTime - multiTime));
	   }
	   
	   else if (singleTime < multiTime)
	   {
		   System.out.printf("%nSingle-threaded faster by %d ms.", (multiTime - singleTime));
	   }
	   
	   else
	   {
		   System.out.println("Both executed in the same amount of time.");
	   }
	   
   }
   
   //***************************************************************
   //
   //  Method:       developerInfo
   // 
   //  Description:  The developer information method of the program
   //
   //  Parameters:   None
   //
   //  Returns:      N/A 
   //
   //**************************************************************
   public void developerInfo()
   {
      System.out.println("Name:     Levi Yoder");
      System.out.println("Course:   ITSE 2317 Intermediate Java Programming");
      System.out.println("Program:  Seven");
	  System.out.println("Due Date: 05/15/2025\n");
   } // End of developerInfo
}
