package com.cloudapplication.services.business;

import java.util.List;

import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * ITopicPostService interface for the TopicPostService
 * @author Trevor
 *
 */
public interface ITopicService 
{
	/**
	 * Method for inserting a user's topic into the database
	 * 
	 * @param topic of type TopicModel
	 * @param user of type CredentialModel
	 * @return TopicModel object type
	 */
	public TopicModel addPost(TopicModel topic, String userid);
	/**
	 * Method for getting all topics from a specific category
	 * 
	 * @param category type String
	 * @return type ListTopicModel
	 */
	public List<TopicModel> getTopicByCategory(String category);
	/**
	 * Method for getting all topics
	 * 
	 * @return type ListTopicModel
	 */
	public List<TopicModel> getAllPosts();
	/**
	 * Method for getting most recent topics of each category
	 * 
	 * @return type ListTopicModel
	 */
	public List<TopicModel> getMostRecentPosts();
	/**
	 * Method for getting the selected Post
	 * 
	 * @param id type String
	 * @return type TopicModel
	 */
	public TopicModel getTopic(String id);
	/**
	 * Method for updating post
	 * 
	 * @param topic type String
	 * @return type TopicModel
	 */
	public TopicModel updateTopic(TopicModel topic);
	/**
	 * Method for deleting the selected topic
	 * 
	 * @param topic type TopicModel
	 */
	public void deleteTopic(TopicModel topic);
	/**
	 * Method for adding a comment to a post
	 * 
	 * @param comment type CommentModel
	 * @param date type String
	 * @param postid type String
	 * @param userid type String
	 */
	public void addComment(CommentModel comment, String date, String postid, String userid);
	/**
	 * Method for getting all comments for a particular post
	 * 
	 * @param postid type String
	 * @return ListCommentModel
	 */
	public List<CommentModel> getComments(String postid);
}
