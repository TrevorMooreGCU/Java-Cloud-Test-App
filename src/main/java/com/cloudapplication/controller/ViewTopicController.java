package com.cloudapplication.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;
import com.cloudapplication.services.business.ITopicService;

/**
 * Aaron Ross
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Trevor Moore
 * This is our own work.
 * 
 * ViewTopicController Class for viewing topic modules. Invoke using "/view"
 * @author Aaron
 * 
 */
@Controller
@RequestMapping("/view")
public class ViewTopicController {

	// Topic Service that is being injected and used to insert new topics into the database
	ITopicService topicService;
	/**
	 * Autowired method for setting the injected Topic Service
	 * @param service type ITopicPostService
	 */
	@Autowired
	public void setTopicService(ITopicService service)
	{
		this.topicService = service;
	}
	/**
	 * Simple method for getting all topics that returns a ModelAndView object with a Search and Topic models added.
	 * Only available for logged in users.
	 * Invoke using "/view/all" URI.
	 * 
	 * @return ModelAndView viewAllTopics.jsp
	 */
	@RequestMapping(path="/all", method=RequestMethod.GET)
	public ModelAndView getViewAllTopicsView() 
	{
		// instantiating ModelAndView object and specifying to return the "viewAllTopics" view
		ModelAndView mav = new ModelAndView("viewAllTopics");
		
		// adding a Search and Topic model arraylists to the model and view object
		mav.addObject("recent", new ArrayList<TopicModel>(topicService.getMostRecentPosts()));
		mav.addObject("topics", new ArrayList<TopicModel>(topicService.getAllPosts()));
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
	/**
	 * Simple method that gets the selected topic and returns a ModelAndView object with a Search and the Topic model added.
	 * Only available for logged in users.
	 * Invoke using "/view/topic" URI.
	 * 
	 * @param id type String
	 * @param response type HttpServletResponse
	 * @param session HttpSession
	 * @return ModelAndView viewTopic.jsp
	 * @throws IOException type ioexception
	 */
	@RequestMapping(path="/topic", method=RequestMethod.GET)
	public ModelAndView getViewTopicView(@RequestParam(value = "id", required = false) String id, HttpServletResponse response, HttpSession session) throws IOException 
	{
		// instantiating ModelAndView object and specifying to return the "viewTopic" view
		ModelAndView mav = new ModelAndView("viewTopic");
		
		// adding a Search model, Topic model selected, current users id, Comment model arraylist for the topic, and Comment model
		mav.addObject("topic", topicService.getTopic(id));
		mav.addObject("comments", topicService.getComments(id));
		mav.addObject("thecomment", new CommentModel());
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
	/**
	 * Simple method for getting a category of posts that returns a ModelAndView object with a Search and Topic models added.
	 * Only available for logged in users.
	 * Invoke using "/view/category" URI.
	 * 
	 * @param id type String
	 * @param response type HttpServletResponse
	 * @param session type HttpSession
	 * @return ModelAndView categoryView.jsp
	 * @throws IOException type ioexception
	 */
	@RequestMapping(path="/category", method=RequestMethod.GET)
	public ModelAndView getCategoryView(@RequestParam(value = "id", required = false) String id, HttpServletResponse response, HttpSession session) throws IOException 
	{
		// instantiating ModelAndView object and specifying to return the "categoryView" view
		ModelAndView mav = new ModelAndView("categoryView");
		
		// adding a SearchModel, category name, and TopicModel array list of the topics in that category
		mav.addObject("category", id);
		mav.addObject("topics", topicService.getTopicByCategory(id));
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
}


