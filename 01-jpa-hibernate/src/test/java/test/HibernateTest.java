package test;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;
import pojo.Customer;

import java.util.List;

public class HibernateTest {

    private SessionFactory sf;

    @Before
    public void init(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testI(){
        try(Session session = sf.openSession()){
            Transaction transaction = session.beginTransaction();
            Customer customer = new Customer();
            customer.setCustName("小c");

            session.save(customer);
            transaction.commit();
        }

    }

    @Test
    public void testR(){
        try(Session session = sf.openSession()){
            Transaction transaction = session.beginTransaction();

            //lazy load
            Customer customer = session.load(Customer.class,1L);
            //Customer customer = session.find(Customer.class,1L);
            System.out.println(customer);

            transaction.commit();
        }
    }

    @Test
    public void testU(){
        try(Session session = sf.openSession()){
            Transaction transaction = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(2l);
            customer.setCustName("小ｄ");

            //新增 session.save()
            //更新 session.update()
            session.saveOrUpdate(customer);
            transaction.commit();
        }

    }

    @Test
    public void testD(){
        try(Session session = sf.openSession()){
            Transaction transaction = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(3l);

            session.remove(customer);
            transaction.commit();
        }

    }

    @Test
    public void testHql(){
        try(Session session = sf.openSession()){
            Transaction transaction = session.beginTransaction();

            //hibernate官網有 SELECT 可省略
            String  hql ="FROM Customer";
            List<Customer> resultList = session.createQuery(hql,Customer.class).getResultList();
            System.out.println(resultList);

            transaction.commit();
        }

    }

}
