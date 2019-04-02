package com.cloudapplication.services.data;

import java.util.ArrayList;
import java.util.List;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * TopicDAO extending DataAccessInterface abstract class and therefore inheriting all its methods. For talking with the database concerning all things "topics"
 * @author Trevor
 *
 */
public class TopicDAO extends DataAccessInterface<TopicModel,CommentModel>
{
	// Injecting DataSource and JdbcTemplate
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	/**
	 * Autowired method for setting the DataSource and JdbcTemplate in order to connect to the db
	 * @param dataSource type DataSource
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * Overridden method for inserting a new post into the posts table
	 * 
	 * @param topic type TopicModel
	 * @param user type CredentialModel (to get the users ID who posted it)
	 * @return TopicModel
	 */
	@Override
	public TopicModel createPost(TopicModel topic, String userid) 
	{
		// defining all our queries
		// n1euzrfjibaye0bl
		// second query for inserting the topic into the posts table
		String query2 = "INSERT INTO posts(USERID, TITLE, CATEGORY, BODY, CREATIONDATE) VALUES(?, ?, ?, ?, ?)";
		// third query for grabbing the newly inserted post from posts table
		String query3 = "SELECT * FROM posts WHERE USERID = ? AND TITLE = ? AND CATEGORY = ? AND BODY = ? AND CREATIONDATE = ?";

		// execute second query to insert post using user id
		jdbcTemplate.update(query2, userid, topic.getTitle(), topic.getCategory(), topic.getBody(), topic.getDate());
		
		// execute third query for grabbing the newly inserted post
		SqlRowSet srs2 = jdbcTemplate.queryForRowSet(query3, userid, topic.getTitle(), topic.getCategory(), topic.getBody(), topic.getDate() + ".0");
		
		// storing the post
		srs2.next();
		TopicModel tp = new TopicModel(srs2.getString("CREATIONDATE"), srs2.getString("TITLE"), srs2.getString("CATEGORY"), srs2.getString("BODY"), srs2.getString("ID"), srs2.getString("USERID"));
		
        // returning the newly inserted post
        return tp;
	}
	/**
	 * Overridden method for getting posts by their category from the posts table
	 * 
	 * @param category type String
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getPostByCategory(String category) 
	{
		// defining our query
		// query for grabbing all post from particular category
		String query = "SELECT * FROM posts WHERE CATEGORY = ?";
		
		// execute the query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, category);
        
		// instantiate list of topic models
		List<TopicModel> topics = new ArrayList<TopicModel>();
				
        // while the rowset has a next add the row to the list
        while(srs.next())
        	topics.add(new TopicModel(srs.getString("CREATIONDATE"), srs.getString("TITLE"), srs.getString("CATEGORY"), srs.getString("BODY"), srs.getString("ID"), srs.getString("USERID")));

        // return the list
        return topics;
	}
	/**
	 * Overridden method for getting all posts from the posts table
	 * 
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getAllPosts() 
	{
		// defining our query
		// grabbing everything from the posts table
		String query = "SELECT * FROM posts";

		// execute query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query);

		// instantiate list of topic models
		List<TopicModel> topics = new ArrayList<TopicModel>();
				
		// while the rowset has a next add the row to the list
        while(srs.next())
        	topics.add(new TopicModel(srs.getString("CREATIONDATE"), srs.getString("TITLE"), srs.getString("CATEGORY"), srs.getString("BODY"), srs.getString("ID"), srs.getString("USERID")));

        // return the list
        return topics;
	}
	/**
	 * Overridden method for getting most recent posts from the posts table
	 * 
	 * @return ListTopicModel
	 */
	@Override
	public List<TopicModel> getMostRecentPosts() 
	{
		// defining our query
		// grabbing the topic with the most recent date from each category
		String query = "SELECT i1.* FROM posts AS i1 LEFT JOIN posts AS i2 ON (i1.CATEGORY = i2.CATEGORY AND i1.CREATIONDATE < i2.CREATIONDATE) WHERE i2.CREATIONDATE IS NULL";

		// execute query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query);

		// instantiate list of topic models
		List<TopicModel> topics = new ArrayList<TopicModel>();
				
		// while the rowset has a next add the row to the list
        while(srs.next())
        	topics.add(new TopicModel(srs.getString("CREATIONDATE"), srs.getString("TITLE"), srs.getString("CATEGORY"), srs.getString("BODY"), srs.getString("ID"), srs.getString("USERID")));

        // return the list
        return topics;
	}
	/**
	 * Overridden method for getting the selected post from the posts table
	 * 
	 * @param id type String
	 * @return TopicModel
	 */
	@Override
	public TopicModel getTopic(String id) 
	{
		// defining our query
		// grabbing the post of the specific id
		String query = "SELECT * FROM posts WHERE ID = ?";

		// execute query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, id);

		// instantiate topic model
		TopicModel topic = new TopicModel();
				
        // if the rowset has a next row set all the topic's properties
        if(srs.next())
        {
        	topic.setDate(srs.getString("CREATIONDATE"));
        	topic.setTitle(srs.getString("TITLE"));
        	topic.setCategory(srs.getString("CATEGORY"));
        	topic.setBody(srs.getString("BODY"));
        	topic.setId(srs.getString("ID"));
        	topic.setUserid(srs.getString("USERID"));
        }

        // return the post
        return topic;
	}
	/**
	 * Overridden method for updating the selected post in the posts table
	 * 
	 * @param topic type String
	 * @return TopicModel
	 */
	@Override
	public TopicModel updateTopic(TopicModel topic) 
	{
		// defining our first query
		// updating the row where the id equals the passed in id
		String query1 = "UPDATE posts SET TITLE = ?, BODY = ? WHERE ID = ?";
		
		// execute the update
		jdbcTemplate.update(query1, topic.getTitle(), 
				topic.getBody(), 
				topic.getId());
		
		// defining our second query
		// grab the newly updated row based off of the id
		String query2 = "SELECT * FROM posts WHERE ID = ?";

		// execute the second query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query2, topic.getId());

		// instantiate topic model
		TopicModel returnTopic = new TopicModel();
				
        // if the rowset has a next row set the properties of the model
        if(srs.next())
        {
        	returnTopic.setDate(srs.getString("CREATIONDATE"));
        	returnTopic.setTitle(srs.getString("TITLE"));
        	returnTopic.setCategory(srs.getString("CATEGORY"));
        	returnTopic.setBody(srs.getString("BODY"));
        	returnTopic.setId(srs.getString("ID"));
        	returnTopic.setUserid(srs.getString("USERID"));
        }

        // return the model
        return returnTopic;
	}
	/**
	 * Overridden method for deleting the selected post from the posts table
	 * 
	 * @param topic type TopicModel
	 */
	@Override
	public void deleteTopic(TopicModel topic) 
	{
		// defining our query
		// delete the posts table based off passed in id
		String query = "DELETE FROM posts WHERE ID = ?";

		// execute the delete
		jdbcTemplate.update(query, topic.getId());
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
		// query for inserting a new comment
		String query1 = "INSERT INTO comments(UID, PID, COMMENTBODY, CREATIONDATE) VALUES(?, ?, ?, ?)";
		
		// execute first query to insert comment
		jdbcTemplate.update(query1, userid, postid, comment.getComment(), date);
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
		// query for grabbing all comments for a particular post
		String query = "SELECT comments.ID, comments.UID, comments.PID, comments.COMMENTBODY, comments.CREATIONDATE, users.USERNAME " + 
		"FROM comments INNER JOIN users " +
		"ON comments.UID = users.ID " + 
		"WHERE comments.PID = ? " +
		"ORDER BY CREATIONDATE DESC";
		
		// executing the query
		SqlRowSet srs = jdbcTemplate.queryForRowSet(query, postid);

		// instantiate list of comment models
		List<CommentModel> comments = new ArrayList<CommentModel>();
				
		// while the rowset has a next add the row to the list
        while(srs.next())
        	comments.add(new CommentModel(srs.getString("COMMENTBODY"), srs.getString("CREATIONDATE"), srs.getString("ID"), srs.getString("UID"), srs.getString("PID"), srs.getString("USERNAME")));

        // return the list
        return comments;
	}
}
