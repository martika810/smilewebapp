package com.madcoding.smile.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CacheDataProviderContextListener implements ServletContextListener {
	private static CacheDataProviderContextListener INSTANCE = null;
	private ExecutorService executor;

	private boolean gracefulShutdown = true;
	
	public static CacheDataProviderContextListener getInstance(){
		return INSTANCE;
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		INSTANCE = this;
		final ServletContext context = contextEvent.getServletContext();
		final ThreadFactory daemonFactory = new DaemonThreadFactory();

		executor = Executors.newFixedThreadPool(4,daemonFactory);
		context.setAttribute("SMILE_CACHE_SERVICE", executor);
		
		updateCache();

	}
	
	public void updateCache(){
		executor.execute(new FindAllPhotosToVoteTask());
		executor.execute(new FindAllFriendListTask());
		executor.execute(new FindAllRankPhotoTask());
		executor.execute(new FindAllProfilePhotoTask());
	}
	
	public void updateIndividualCache(String userId){
		executor.execute(new FindFriendListTask(userId,new Callback() {
			
			@Override
			public void callback() {
				// TODO Auto-generated method stub
				
			}
		}));
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (gracefulShutdown) {
			executor.shutdown();
		} else {
			executor.shutdownNow();
		}

	}

}
