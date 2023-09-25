/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package entitymanagerdemo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Address;
import model.Customer;

/**
 *
 * @author Phoom1645
 */
public class EntityManagerDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        createData(1L, "John", "Henry", "jh@mail.com", "123/4 Viphavadee Rd.", "Bangkok", "TH", "10900");
        createData(2L, "Marry", "Jane", "mj@mail.com", "123/5 Viphavadee Rd.", "Bangkok", "TH", "10900");
        createData(3L, "Peter", "Parker", "pp@mail.com", "543/21 Fake Rd.", "Nonthaburi", "TH", "20900");
        createData(4L, "Bruce", "Wayn", "bw@mail.com", "678/90 Unreal Rd.", "Pathumthani", "TH", "30500");
        printAllCustomer();
        System.out.println("--------------- PrinBy City ---------------------");
        printCustomerByCity("Bangkok");
    }
    
  
    
    public static void createData(Long iD, String firstName, String lastName, String email, String street, String city, String country, String zipcode ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Customer cus = new Customer(iD, firstName, lastName, email, new ArrayList<>());
        Address addr = new Address(iD, street, city, country, zipcode, cus);
        cus.getAddressCollection().add(addr);
        em.getTransaction().begin();
        try {
            em.persist(cus);
            em.persist(addr);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     *
     */
    public static void printAllCustomer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        List<Customer> cusList = (List<Customer>) em.createNamedQuery("Customer.findAll").getResultList();
        for (Customer cusPrint : cusList) {
            System.out.println("-------------------");
            System.out.println(cusPrint.toString());
            for (Address addrPrint : cusPrint.getAddressCollection()) {
                System.out.println(addrPrint.toString());
            }
            System.out.println("-------------------");
        }
    }
    
    public static void printCustomerByCity(String city) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Address.findByCity");
        query.setParameter("city", city);
        List<Address> addrList = (List<Address>) query.getResultList();
        for (Address addrPrint : addrList) {
            System.out.println("-------------------");
            Customer cusPrint = addrPrint.getCustomerFk();
            System.out.println(cusPrint.toString());
            System.out.println(addrPrint.toString());
            System.out.println("-------------------");
        }
    }
    
    

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManagerDemoPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
}