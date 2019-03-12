package com.mycompany.simpleservice.svc.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.simpleservice.svc.entity.User;

@Stateless
public class UserFacade extends AbstractFacade<User>	 {

	public UserFacade() {
		super(User.class);
	}

	@PersistenceContext(unitName = "users")
    private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}

	
	
}
