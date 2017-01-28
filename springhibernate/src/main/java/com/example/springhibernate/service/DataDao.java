package com.example.springhibernate.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("dataDao")
public class DataDao {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addObject(Object o) {
		getSession().save(o);
	}

	public void deleteObject(Object o) {
		getSession().delete(o);
	}

	public void updateObject(Object o) {
		getSession().update(o);
	}

	public void saveOrUpdateObject(Object o) {
		getSession().saveOrUpdate(o);
	}

	public <T> T getObjectById(Class<T> c, Serializable id) {
		return (T) getSession().get(c, id);
	}

	public <T> void deleteObjectById(Class<T> c, Serializable id) {
		deleteObject(getObjectById(c, id));
	}

	public List<?> getAllObject(Class<?> c) {
		TypedQuery<?> typedQuery = getSession().createQuery("from " + c.getName());
		return typedQuery.getResultList();
	}

	public List<?> getObjectsByCondition(String hql, String[] params, Object... p) {
		TypedQuery<?> typedQuery = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < p.length; i++) {
				typedQuery.setParameter(params[i], p[i]);
			}
		}
		return typedQuery.getResultList();
	}

	public long getCount(String hql, Object... p) {
		NativeQuery<?> query = getSession().createNativeQuery(hql);
		return ((Number) query.getSingleResult()).longValue();
	}

	public List<?> pageQueryViaParam(String hql, Integer pageSize, Integer page, String[] params, Object... p) {
		TypedQuery<?> query = getSession().createQuery(hql);
		if (p != null) {
			for (int i = 0; i < p.length; i++) {
				query.setParameter(params[i], p[i]);
			}
		}
		if (pageSize != null && pageSize > 0 && page != null && page > 0) {
			query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize);
		}
		return query.getResultList();
	}

	public Object getObjectByCondition(String hql, String[] params, Object... p) {
		TypedQuery<?> typedQuery = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < p.length; i++) {
				typedQuery.setParameter(params[i], p[i]);
			}
		}
		List<?> list = typedQuery.getResultList();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void deleteObjectsByCondition(String hql, String[] params, Object... p) {
		TypedQuery<?> typedQuery = getSession().createQuery(hql);
		if (p != null) {
			for (int i = 0; i < p.length; i++) {
				typedQuery.setParameter(params[i], p[i]);
			}
		}
		typedQuery.executeUpdate();
	}

	public void executeNonSelectSql(String sql) {
		NativeQuery<?> query = getSession().createNativeQuery(sql);
		query.executeUpdate();
	}

	public List<?> executeSelectSql(String sql, Class<?> c) {
		NativeQuery<?> query = getSession().createNativeQuery(sql, c);
		return query.getResultList();
	}

}
