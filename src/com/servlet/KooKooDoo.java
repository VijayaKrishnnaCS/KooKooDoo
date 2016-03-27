package com.servlet;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 	KooKooDoo authored by Vijaya Krishnna.
	Email: csvijayakrishnna@gmail.com
	KooKooDoo is a Rock Paper Scissors game played between the user & computer over KooKoo IVR platform
*/
@WebServlet(name = "KooKooDoo", urlPatterns = {"/VoilaBingo"})
public class KooKooDoo extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

		//parseXML(request, response);
		String event = (String) request.getParameter("event");

        //Response r = new Response(); //create kookoo Response Object
        String kookooResponseOutput = null; //r.getXML();

        Random ran = new Random();
        int computerChoice = ran.nextInt(3) + 1;
        if ((null != event) && event.equalsIgnoreCase("NewCall")) {
        	kookooResponseOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"     
						        	 + "<response sid=\"12345\">"   
						        	 + "<collectdtmf l=\"1\" t=\"#\" o=\"5000\">"    
						        	 + "<playtext>Welcome to KooKooDoo. The Rock Paper Scissors game against Computer. Enter 1 for Rock. Enter 2 for Paper. Enter 3 for Scissors. Enter 0 for exit.</playtext>"  
						        	 + "</collectdtmf>"     
						        	 + "</response>"; 
        }        
        else if ((null != event) && event.equalsIgnoreCase("GotDTMF")) {
        	String gameResult = "";
        	// Game Logic
        	// #1 Rock
        	// #2 Paper
        	// #3 Scissors
        	String userResponseStr = (String) request.getParameter("data");
        	userResponseStr.replace("#", "").substring(0,1);
        	int userResponse = Integer.parseInt(userResponseStr);
        	
        	if (userResponse != 0) {
	        	if(computerChoice == userResponse){
	        		gameResult = "Game Drawn. You and Computer chose the same option.";
	        	}
	        	else if(computerChoice == 1){
	        		if(userResponse ==2){
	            		gameResult = "Voila! You won. Computer chose Rock.";	
	        		} else {
	            		gameResult = "Computer won. Computer chose Rock.";
	        		}
	        	}
	        	else if(computerChoice == 2){
	        		if(userResponse == 1){
	            		gameResult = "Computer won. Computer chose Paper.";	
	        		} else {
	            		gameResult = "Voila! You won. Computer chose Paper.";
	        		}
	        	}
	        	else if(computerChoice == 3){
	        		if(userResponse ==1){
	            		gameResult = "Computer won. Computer chose Scissors.";	
	        		} else {
	            		gameResult = "Voila! You won. Computer chose Scissors.";
	        		}
	        	}
	        	String userChoiceString = null;
	        	if(userResponse == 1){
	        		userChoiceString = "You have chosen Rock. ";
	        	} else if(userResponse == 2){
	        		userChoiceString = "You have chosen Paper. ";
	        	} else if(userResponse == 3){
	        		userChoiceString = "You have chosen Scissors. ";
	        	} else {
	        		userChoiceString = "Exit";
	        	}
	        	if(!"Exit".equalsIgnoreCase(userChoiceString)){
	        	kookooResponseOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	            		+ "<Response>"
	        			+ "<collectdtmf l=\"1\" t=\"#\" o=\"5000\">"
	            		+ "<playtext>"
	            		+ userChoiceString
	            		+ gameResult
	            		+ " To play Enter 1 for Rock. Enter 2 for Paper. Enter 3 for Scissors. Enter 0 for exit."
	            		+ "</playtext>"
	            		+ "</collectdtmf>"
	            		+ "</Response>";
	        	} else {
	        		kookooResponseOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	                		+ "<Response>"
	                		+ "<hangup></hangup>"
	                		+ "</Response>";
	        	}
	        } else {
	        		kookooResponseOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	                		+ "<Response>"
	                		+ "<hangup></hangup>"
	                		+ "</Response>";
	        	}
        }         
        else {
        	kookooResponseOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            		+ "<Response>"
            		+ "<hangup></hangup>"
            		+ "</Response>";
              //r.addPlayText("call is disconnecting " );
            //r.addHangup();
        }

        
        response.setContentType("text/xml");

        try (PrintWriter out = response.getWriter()) {
            out.write(kookooResponseOutput);
        }
       
        //response.setContentType("text/xml");
        //response.getWriter().write(responseXML);
}

public void parseXML(HttpServletRequest request,
        HttpServletResponse response) throws IOException{
	URL url = null;
	PrintWriter printWriter = null;
	URLConnection urlConnection = null;
	BufferedInputStream bufferedInputStream = null;

	try {
	url = getServletContext().getResource("/KooKooDooResponse.xml");
	response.setContentType("text/xml");
	printWriter = response.getWriter();
	urlConnection = url.openConnection();
	urlConnection.connect();
	bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
	int byteOfDataRead = bufferedInputStream.read();
	
	while(byteOfDataRead != -1){
		printWriter.write(byteOfDataRead);
		byteOfDataRead = bufferedInputStream.read();
	}
	
	}
	catch(Exception ex) {
		
	}
	finally {
		if (printWriter != null)	printWriter.close();
		if (bufferedInputStream != null)	bufferedInputStream.close();
	}
}
}
