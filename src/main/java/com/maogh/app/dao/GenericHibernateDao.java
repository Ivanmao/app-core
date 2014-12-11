package com.maogh.app.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.maogh.app.model.GenericEntity;

public class GenericHibernateDao<T extends GenericEntity, PK extends Serializable>
		implements GenericDao<T, PK> {
	private Class<T> persistentClass;

	/**
	 * Constructor that takes in a class to see which type of entity to persist.
	 * Use this constructor when subclassing.
	 * 
	 * @param persistentClass
	 *            the class type you'd like to persist
	 */
	public GenericHibernateDao(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() throws HibernateException {
		Session sess = getSessionFactory().getCurrentSession();
		if (sess == null) {
			sess = getSessionFactory().openSession();
		}
		return sess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Session session = getSession();
		return (List<T>) session.createCriteria(persistentClass).list();
	}

	@Override
	public List<T> getAllDistinct() {
		Collection<T> result = new LinkedHashSet<T>(getAll());
		return new ArrayList<T>(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T) getSession().get(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(PK id) {
		Session session = getSession();
		T t = (T) session.load(persistentClass, id);
		return t != null;
	}

	@Override
	public T save(T object) {
		getSession().save(object);
		return object;
	}

	@Override
	public void remove(T object) {
		getSession().delete(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(PK id) {
		Session session = getSession();
		T t = (T) session.load(persistentClass, id);
		getSession().delete(t);
	}
}
