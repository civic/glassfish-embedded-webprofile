package example.service;

import example.entity.Customer;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Customer用のドメインロジック
 */
@Dependent
public class CustomerService {
    @PersistenceContext
    private EntityManager em;

    public CustomerService() {
    }

    public CustomerService(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }


    @Transactional
    public Customer addNewCustomer(String name){
        Customer c = new Customer();
        c.setCustomerName(name);

        em.persist(c);

        return c;
    }

    public List<Customer> all(){
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }
    public Customer getById(int id){
        return em.createNamedQuery("Customer.findByCustomerId", Customer.class)
                .setParameter("customerId", id)
                .getSingleResult();
    }
    @Transactional
    public int deleteAllCustomers(){
        return em.createQuery("DELETE FROM Customer").executeUpdate();
    }
}
