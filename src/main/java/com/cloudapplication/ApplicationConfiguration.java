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
	    dataSource.setUrl("jdbc:mysql://aucxibl2dxeo01wa:hytrxvvxgrfx6e02@l9dwvv6j64hlhpul.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/n1euzrfjibaye0bl");
	    dataSource.setUsername("aucxibl2dxeo01wa");
	    dataSource.setPassword("hytrxvvxgrfx6e02");
	    
	    // Azure:
	    //dataSource.setUrl("jdbc:mysql://127.0.0.1:52017/mydatabase?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
	    //dataSource.setUsername("azure");
	    //dataSource.setPassword("6#vWHD_$");
	    
	    // OpenShift:
	    //dataSource.setUrl("jdbc:mysql://172.30.76.5:3306/gcuclouddb?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
	    //dataSource.setUsername("trevormoore");
	    //dataSource.setPassword("gcucloud");
	    
	    // AWS
	    //dataSource.setUrl("jdbc:mysql://mygcuclouddbinstance.craihqdakq1j.us-east-2.rds.amazonaws.com:3306/gcuclouddb");
	    //dataSource.setUsername("trevormoore");
	    //dataSource.setPassword("gcucloud");
	    
	    // GoogleCloud:
	    //dataSource.setUrl("jdbc:mysql://google/googlegcuclouddb?socketFactory=com.google.cloud.sql.mysql.SocketFactory&cloudSqlInstance=axial-iris-231514:us-west1:googlegcucloud");
	    //dataSource.setUsername("trevormoore");
	    //dataSource.setPassword("trevormoore");
		//test dataSource.setUrl("jdbc:mysql://35.227.174.244:3306/googlegcuclouddb");
		//test dataSource.setUrl("jdbc:mysql://google/googlegcuclouddb?cloudSqlInstance=axial-iris-231514:us-west1:googlegcucloud&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false");
		//test dataSource.setUrl("jdbc:mysql://google/googlegcuclouddb?cloudSqlInstance=axial-iris-231514:us-west1:googlegcucloud&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=trevormoore&password=trevormoore");

	    dataSource.setInitialSize(1);
	    return dataSource;
	}
	
}