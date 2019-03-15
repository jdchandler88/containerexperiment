package com.mycompany.simpleservice.svc.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mycompany.simpleservice.svc.entity.Role;

@Stateless
public class RoleFacade extends AbstractFacade<Role>	 {
	
	public RoleFacade() {
		super(Role.class);
	}

	@PersistenceContext(unitName = "users")
    private EntityManager entityManager;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	
	public void createRole(String name, String description) {
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		create(role);
	}
	
	public Role getByRoleName(String roleName) {
		 return (Role) getEntityManager().createNamedQuery("Role.findByName")
            .setParameter("rolename", roleName)
            .getSingleResult();
	}
	
}
