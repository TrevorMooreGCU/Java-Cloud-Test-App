package com.cloudapplication.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
 * UpdateController Class for updating products. Invoke with "/update"
 * @author Trevor
 * 
 */
@Controller
@RequestMapping("/update")
public class UpdateController 
{
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
	 * Simple method that returns a ModelAndView object with a Search and Topic model selected to update added.
	 * Only available for logged in users.
	 * Invoke using "/update/topic" URI.
	 * @param id type String
	 * @param response HttpServletResponse
	 * @return ModelAndView updateTopic.jsp
	 * @throws IOException type ioexception
	 */
	@RequestMapping(path="/topic", method=RequestMethod.GET)
	public ModelAndView getUpdateTopicView(@RequestParam(value = "id", required = false) String id, HttpServletResponse response) throws IOException 
	{
		// instantiating ModelAndView object and specifying to return the "updateTopic" view
		ModelAndView mav = new ModelAndView("updateTopic");
		
		// adding a Search and Topic model objects to the ModelAndView
		mav.addObject("topic", topicService.getTopic(id));
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
	/**
	 * Simple method for updating posts that returns a ModelAndView object with a Search and Topic model added.
	 * Only available for logged in users.
	 * Invoke using "/update/updatetopic" URI.
	 * @param topic type TopicModel
	 * @param result type BindingResult
	 * @param session type HttpSession
	 * @return ModelAndView viewTopic.jsp
	 */
	@RequestMapping(path="/updatetopic", method=RequestMethod.POST)
	public ModelAndView updateTopic(@Valid @ModelAttribute("topic") TopicModel topic, BindingResult result, HttpSession session)
	{
		// check if the model has data validation errors
		if(result.hasErrors())
		{
			// if it has errors:
			// instantiating ModelAndView object and specifying to return the "updateTopic" view
			ModelAndView mav = new ModelAndView("updateTopic");
			
			// adding a Search and Topic model object to the ModelAndView
			mav.addObject("topic", topic);
			
			// returning ModelAndView object with all models needed attached
			return mav;
		}

		// instantiating ModelAndView object and specifying to return the "viewTopic" view
		ModelAndView mav = new ModelAndView("viewTopic");
		
		// adding a Search and Topic model objects to the ModelAndView
		mav.addObject("topic", topicService.updateTopic(topic));
		mav.addObject("comments", topicService.getComments(topic.getId()));
		mav.addObject("thecomment", new CommentModel());
		
		// returning ModelAndView object with all models needed attached
		return mav;
	}
}

