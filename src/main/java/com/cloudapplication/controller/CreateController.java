package com.cloudapplication.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;
import com.cloudapplication.services.business.ITopicService;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * CreateController Class for creating our product module. Invoke with /create
 * @author Trevor
 * 
 */
@Controller
@RequestMapping("/create")
public class CreateController 
{
	// Topic Service that is being injected and used to insert new topics into the database
	ITopicService topicService;
	/**
	 * Autowired method for setting the injected Topic Post Service
	 * @param service type ITopicPostService
	 */
	@Autowired
	public void setTopicService(ITopicService service)
	{
		this.topicService = service;
	}
	/**
	 * Simple method that returns a ModelAndView object with a Search and Topic model, and string of dattime format added for returning the create topic view.
	 * Only available for logged in users.
	 * Invoke using "/create/socialize" URI.
	 * 
	 * @return ModelAndView createTopic.jsp
	 */
	@RequestMapping(path="/socialize", method=RequestMethod.GET)
	public ModelAndView getCreateTopicView() 
	{
		// instantiating ModelAndView object and specifying to return the "createTopic" view
		ModelAndView mav = new ModelAndView("createTopic");
		
		// getting the current time and setting it 
		DateTimeFormatter sdfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
	    String date = sdfDate.format(now);
		
		// adding a Search and Topic model objects to the ModelAndView
		mav.addObject("topic", new TopicModel());
		mav.addObject("date", date);
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
	/**
	 * Simple method that posts the new topic and returns a ModelAndView object with a Search and the Topic model added.
	 * Only available for logged in users.
	 * Invoke using "/create/posttopic" URI.
	 * 
	 * @param topic of type TopicModel
	 * @param result of type BindingResult
	 * @param session of type HttpSession
	 * @return ModelAndView viewTopic.jsp
	 */
	@RequestMapping(path="/posttopic", method=RequestMethod.POST)
	public ModelAndView postTopic(@Valid @ModelAttribute("topic") TopicModel topic, BindingResult result, HttpSession session)
	{
		// check if the model has data validation errors
		if(result.hasErrors())
		{
			// if it has errors:
			// instantiating ModelAndView object and specifying to return the "createTopic" view
			ModelAndView mav = new ModelAndView("createTopic");
			
			// adding a Search and Topic model object to the ModelAndView
			mav.addObject("topic", topic);
			
			// returning ModelAndView object with all models needed attached
			return mav;
		}
		
		// inserting the new post into the db using injected service
		TopicModel tp = topicService.addPost(topic, "24");
		
		// instantiating view to view the newly posted topic
		ModelAndView mav = new ModelAndView("viewTopic");
		
		// adding the needed models for the view
		mav.addObject("topic", tp);
		mav.addObject("comments", topicService.getComments(tp.getId()));
		mav.addObject("thecomment", new CommentModel());
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
	/**
	 * Method for posting a new comment to a topic.
	 * Only available for logged in users.
	 * Invoke using "/create/addComment" URI.
	 * 
	 * @param id type String
	 * @param comment type CommentModel
	 * @param result type BindingResult
	 * @param session type HttpSession
	 * @return ModelAndView viewTopic.jsp
	 */
	@RequestMapping(path="/addcomment", method=RequestMethod.POST)
	public ModelAndView addComment(@RequestParam(value = "id", required = false) String id, @Valid @ModelAttribute("thecomment") CommentModel comment, BindingResult result, HttpSession session)
	{
		// check if the model has data validation errors
		if(result.hasErrors())
		{
			// instantiating ModelAndView object and specifying to return the "viewTopic" view
			ModelAndView mav = new ModelAndView("viewTopic");
			
			// adding a SearchModel, TopicModel, the CommenttModel, the users id, all comments for the topic
			mav.addObject("topic", topicService.getTopic(id));
			mav.addObject("comments", topicService.getComments(id));
			mav.addObject("thecomment", comment);
			
			// returning ModelAndView object with all models needed attached
			return mav;
		}
		
		// getting the current time and setting it 
		DateTimeFormatter sdfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
	    String date = sdfDate.format(now);
		
		// inserting the new comment on the post into the db using injected service
		topicService.addComment(comment, date, id, "24");
		
		// instantiating ModelAndView object and specifying to return the "viewTopic" view
		ModelAndView mav = new ModelAndView("viewTopic");
		
		// adding a SearchModel, TopicModel, CommenttModel, the users id, all comments for the topic
		mav.addObject("topic", topicService.getTopic(id));
		mav.addObject("comments", topicService.getComments(id));
		mav.addObject("thecomment", new CommentModel());
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
}
