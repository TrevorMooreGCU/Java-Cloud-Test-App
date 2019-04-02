package com.cloudapplication.services.utility;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trevor Moore
 * CST-323
 * 03/12/2019
 * This is my own work.
 * 
 * Tracer class that is an intercepter for performing system logs.
 * @author Trevor
 *
 */
@Aspect
public class Tracer
{
	// setting the logger object
    private static Logger logger = LoggerFactory.getLogger("GcuTracer");
    /**
     * Method will log when any method in the controller package has been entered.
     */
    @Pointcut("within(com.cloudapplication.controller..*)")
	public void inControllerLayer()
	{
    	// log the within info
    	logger.info("------------------> Within Controller Layer.");
	}
    /**
     * Method will log when any method in the .data package has been entered.
     */
    @Pointcut("within(com.cloudapplication.services.data..*)")
	public void inDataServiceLayer()
	{
    	// log the within info
    	logger.info("------------------> Within Data Service Layer.");
	}
    /**
     * Method will log when any method in the .business package has been entered.
     */
    @Pointcut("within(com.cloudapplication.services.business..*)")
	public void inBusinessServiceLayer()
	{
    	// log the within info
    	logger.info("------------------> Within Business Service Layer.");
	}
    /**
     * Method will log when any method in any packages of the project throws an exception.
     */
    @AfterThrowing(pointcut = "execution(* com.cloudapplication..*.*.*(..))", throwing = "error")
    public void logAfterThrowingAllMethods(JoinPoint jp, Throwable error) throws Throwable
    {
    	// log the error
    	logger.error("------------------> EXCEPTION THROWN in " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + "(): " + error);
    }
    /**
     * General logging method that will log when any method is entered and exited.
     * @param pjp type ProceedingJoinPoint
     * @return type generic Object
     * @throws Throwable type Throwable
     */
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable
	{
    	// log the entering info
		logger.info("------------------> Entering " + pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + "()");
		// call proceed on the pjp object
		Object retVal = pjp.proceed();
		// log the leaving info
		logger.info("------------------> Leaving " + pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName() + "()");
		// return the result of the proceed call
		return retVal;
	}
}
