package org.onlinetest.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class SessionUtil {

	private final static SessionUtil instance = new SessionUtil();
	private final SessionFactory factory;
	
	private SessionUtil(){
		Configuration config = new Configuration().configure();
		StandardServiceRegistryBuilder ssrBuilder = new StandardServiceRegistryBuilder();
		ssrBuilder.applySettings(config.getProperties());
		factory = config.buildSessionFactory(ssrBuilder.build());
	}
	
	public static Session getSession(){
		return getInstance().factory.openSession();
	}
	
	private static SessionUtil getInstance(){
		return instance;
	}
}