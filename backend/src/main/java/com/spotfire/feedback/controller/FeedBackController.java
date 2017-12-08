package com.spotfire.feedback.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spotfire.feedback.model.Customer;
import com.spotfire.feedback.model.Feedback;
import com.spotfire.feedback.model.FeedbackDTO;
import com.spotfire.feedback.repository.CustomerRepository;
import com.spotfire.feedback.repository.FeedbackRepository;

@RestController
@RequestMapping("/feedbackapi")
public class FeedBackController {

    @Autowired
	FeedbackRepository repository;
    
    @Autowired
    CustomerRepository custRepository;
       
	@RequestMapping(value = "/create/{custId}", method = RequestMethod.POST)
    public String createFeedback(@PathVariable long custId, @RequestParam(name = "image", required = false) MultipartFile feedbackImage, @RequestBody Feedback feedbackrequest) throws IOException{
		
		Customer cust = custRepository.findById(custId);
		
		String type = feedbackrequest.getFeedbackType();
		String text = feedbackrequest.getFeedbackText();
		byte[] imageFile;
		Feedback fd;
		if(feedbackImage !=null) {
			imageFile= feedbackImage.getBytes();		
			 fd = new Feedback(text,type,cust,imageFile);
		}else {
			 fd = new Feedback(text,type,cust);
		}
		
		repository.save(fd);		
		return "Done";
		
   }      
   
}
