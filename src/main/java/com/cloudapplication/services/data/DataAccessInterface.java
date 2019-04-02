package com.cloudapplication.services.data;

import java.util.List;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * DataAccessInterface abstract class to provide an interface for all DAOs
 * @author Trevor
 *
 */
public abstract class DataAccessInterface <T, M>
{
	/**
	 * Method for inserting a new post into the posts table
	 * 
	 * @param model type TopicModel
	 * @param user type CredentialModel (to get the users ID who posted it)
	 * @return model type TopicModel
	 */
	public T createPost(T model, String userid) {
		return null;
	}
	/**
	 * Method for grabbing every topic from a specific category
	 * 
	 * @param category type String
	 * @return ListTopicModel type
	 */
	public List<T> getPostByCategory(String category) {
		return null;
	}
	/**
	 * Method for grabbing all topics
	 * 
	 * @return ListTopicModel type
	 */
	public List<T> getAllPosts() {
		return null;
	}
	/**
	 * Method for grabbing most recent topic in each category
	 * 
	 * @return ListTopicModel type
	 */
	public List<T> getMostRecentPosts() {
		return null;
	}
	/**
	 * Method for grabbing the selected Post
	 * 
	 * @param id type String
	 * @return type TopipcModel
	 */
	public T getTopic(String id) {
		return null;
	}
	/**
	 * Method for updating post
	 * 
	 * @param topic type String
	 * @return type TopicModel
	 */
	public T updateTopic(T topic) {
		return null;
	}
	/**
	 * Method for deleting the selected topic
	 * 
	 * @param topic type TopicModel
	 */
	public void deleteTopic(T topic) {
	}
	/**
	 * Method for adding a comment to a post
	 * 
	 * @param comment type CommentModel
	 * @param date type String
	 * @param postid type String
	 * @param userid type String
	 */
	public void addComment(M comment, String date, String postid, String userid){
	}
	/**
	 * Method for getting all comments for a particular post
	 * 
	 * @param postid type String
	 * @return ListCommentModel
	 */
	public List<M> getComments(String postid){
		return null;
	}
}
