package com.cloudapplication.services.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;
import com.cloudapplication.services.data.DataAccessInterface;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * Concrete implementation class of ITopicPostService interface
 * @author Trevor
 *
 */
public class TopicService implements ITopicService
{
	// DataAccessInterface for injecting our TopicDAO
	DataAccessInterface<TopicModel,CommentModel> topicDAO;
	/**
	 * Autowired method for setting the injected Topic DAO
	 * @param dao type ITopicDAO
	 */
	@Autowired
	@Qualifier("topic")
	public void setTopicDAO(DataAccessInterface<TopicModel,CommentModel> dao)
	{
		this.topicDAO = dao;
	}
	/**
	 * Overridden method for inserting a user's topic into the database
	 * 
	 * @param topic of type TopicModel
	 * @param userid of type String
	 * @return boolean object type
	 */
	@Override
	public TopicModel addPost(TopicModel topic, String userid) 
	{
		// returning the result of the create post method called on the topic dao
		return (TopicModel) topicDAO.createPost(topic, userid);
	}
	/**
	 * Method to get topics by category, returning a list of type TopicModel
	 * 
	 * @param category type String
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getTopicByCategory(String category)
	{
		// returning the result of grabbing all the posts from a category using topic dao
		return topicDAO.getPostByCategory(category);
	}
	/**
	 * Method to get all topics, returning a list of type TopicModel
	 * 
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getAllPosts()
	{
		// returning the result of grabbing all the posts using topic dao
		return topicDAO.getAllPosts();
	}
	/**
	 * Method to get most recent topics of each category, returning a list of type TopicModel
	 * 
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getMostRecentPosts()
	{
		// returning the result of grabbing the most recent posts using topic dao
		return topicDAO.getMostRecentPosts();
	}
	/**
	 * Method for getting a selected topic
	 * 
	 * @param id type String
	 * @return TopicModel
	 */
	@Override
	public TopicModel getTopic(String id)
	{
		// returning the result of grabbing the post using topic dao
		return (TopicModel) topicDAO.getTopic(id);
	}
	/**
	 * Method for updating a topic
	 * 
	 * @param topic type TopicModel
	 * @return TopicModel
	 */
	@Override
	public TopicModel updateTopic(TopicModel topic)
	{
		// returning the result of updating the post using topic dao
		return (TopicModel) topicDAO.updateTopic(topic);
	}
	/**
	 * Method for deleting the selected topic
	 * 
	 * @param topic type TopicModel
	 */
	@Override
	public void deleteTopic(TopicModel topic)
	{
		// deleting the post using topic dao
		topicDAO.deleteTopic(topic);
	}
	/**
	 * Method for adding a comment to a post
	 * 
	 * @param comment type CommentModel
	 * @param date type String
	 * @param postid type String
	 * @param userid type String
	 */
	@Override
	public void addComment(CommentModel comment, String date, String postid, String userid)
	{
		topicDAO.addComment(comment, date, postid, userid);
	}
	/**
	 * Method for getting all comments for a particular post
	 * 
	 * @param postid type String
	 * @return ListCommentModel
	 */
	@Override
	public List<CommentModel> getComments(String postid)
	{
		return topicDAO.getComments(postid);
	}
}
