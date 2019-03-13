package com.mycompany.simpleservice.svc.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.simpleservice.svc.entity.User;
import com.mycompany.simpleservice.util.PasswordUtil;

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
	
	
	public void createUser(String username, String password) {
		User newUser = new User();
		//use username
		newUser.setUsername(username);
		
		//store salt and hash
		String salt = PasswordUtil.generateSalt();
		newUser.setPasswordSalt(new String(salt));
		newUser.setPasswordHash(PasswordUtil.hash(password, salt));
		
		create(newUser);
	}
	
	public User getByUserName(String username) {
		 return (User) getEntityManager().createNamedQuery("User.findByName")
            .setParameter("username", username)
            .getSingleResult();
	}
	
}
