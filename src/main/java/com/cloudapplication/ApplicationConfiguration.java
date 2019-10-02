package com.cloudapplication;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import com.cloudapplication.controller.CreateController;
import com.cloudapplication.controller.DeleteController;
import com.cloudapplication.controller.HomeController;
import com.cloudapplication.controller.UpdateController;
import com.cloudapplication.controller.ViewTopicController;
import com.cloudapplication.model.CommentModel;
import com.cloudapplication.model.TopicModel;
import com.cloudapplication.services.business.ITopicService;
import com.cloudapplication.services.business.TopicService;
import com.cloudapplication.services.data.DataAccessInterface;
import com.cloudapplication.services.data.TopicDAO;

/**
 * Trevor Moore
 * CST-323
 * 1/10/2019
 * This assignment was completed in collaboration with Aaron Ross
 * This is our own work.
 * 
 * ApplicationConfiguration class for defining all our SpringBeans
 * @author Trevor
 *
 */
@Configuration
public class ApplicationConfiguration 
{
	/// CONTROLLERS ///
	/**
	 * Getter for the HomeController SpringBean
	 * @return type HomeController
	 */
	@Bean(name="homeController")
	public HomeController getHomeController()
	{
		return new HomeController();
	}
	/**
	 * Getter for the CreateController SpringBean
	 * @return type CreateController
	 */
	@Bean(name="createController")
	public CreateController getCreateController()
	{
		return new CreateController();
	}
	/**
	 * Getter for the ViewTopicController SpringBean
	 * @return type CreateController
	 */
	@Bean(name="viewTopicController")
	public ViewTopicController getViewTopicController()
	{
		return new ViewTopicController();
	}
	/**
	 * Getter for the UpdateController SpringBean
	 * @return type UpdateController
	 */
	@Bean(name="updateController")
	public UpdateController getUpdateController()
	{
		return new UpdateController();
	}
	/**
	 * Getter for the DeleteController SpringBean
	 * @return type DeleteController
	 */
	@Bean(name="deleteController")
	public DeleteController getDeleteController()
	{
		return new DeleteController();
	}
	
	
	/// BUSINESS SERVICES ///
	/**
	 * Getter for TopicPostService SpringBean (request scoped)
	 * @return type ITopicPostService
	 */
	@Bean(name="topicService")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public ITopicService getTopicService()
	{
		return new TopicService();
	}
	
	
	/// DATA SERVICES ///
	/**
	 * Getter for SecurityDAO SpringBean (request scoped)
	 * @return type ISecurityDAO
	 */
	/**
	 * Getter for TopicDAO SpringBean (request scoped)
	 * @return type ITopicDAO
	 */
	@Bean(name="topicDAO")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	@Qualifier("topic")
	public DataAccessInterface<TopicModel,CommentModel> getTopicDAO()
	{
		return new TopicDAO();
	}
	
	
	/// DATA SOURCE ///
	/**
	 * Getter for DataSource SpringBean (singleton scoped)
	 * @return type DataSource
	 */
	@Bean(name="dataSource", destroyMethod = "close")
	@Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
	public DataSource getDataSource()
	{
		DataSource dataSource = new DataSource();
	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	    
	    // Heroku:
	    dataSource.setUrl("jdbc:mysql");
	    dataSource.setUsername("");
	    dataSource.setPassword("");
	    
	    // Azure:
	    //dataSource.setUrl("jdbc:mysql");
	    //dataSource.setUsername("");
	    //dataSource.setPassword("");
	    
	    // OpenShift:
	    //dataSource.setUrl("jdbc:mysql");
	    //dataSource.setUsername("");
	    //dataSource.setPassword("");
	    
	    // AWS
	    //dataSource.setUrl("jdbc:mysql");
	    //dataSource.setUsername("");
	    //dataSource.setPassword("");
	    
	    // GoogleCloud:
	    //dataSource.setUrl("jdbc:mysql");
	    //dataSource.setUsername("");
	    //dataSource.setPassword("");

	    dataSource.setInitialSize(1);
	    return dataSource;
	}
	
}
