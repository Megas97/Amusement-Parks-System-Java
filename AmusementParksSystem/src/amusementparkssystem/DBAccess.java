package amusementparkssystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DBAccess {
    public static boolean addPark(String parkName, Double parkTicketPrice){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = new Amusementpark(null, parkTicketPrice, 0.0, parkName, null, null);
            id = (Integer) session.save(park);
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static List<Amusementpark> getAllParks(){
        List<Amusementpark> parks = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Amusementpark.class);
            parks = cr.list();
            Collections.sort(parks, new Comparator<Amusementpark>() {
                public int compare(Amusementpark p1, Amusementpark p2) {
                    return p1.getParkId().compareTo(p2.getParkId());
                }
            });
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return parks;
    }
    
    public static boolean setManager(String managerName, double managerSalary, int parkId){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Manager manager = new Manager(managerName, managerSalary, null);
            id = (Integer) session.save(manager);
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            park.setManager(manager);
            session.update(park);
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static boolean createAndSetFacility(String facilityName, int facilityMinAge, int parkId){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            Facility facility = new Facility(park, facilityName, facilityMinAge, null);
            id = (Integer) session.save(facility);
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static boolean addVisitorToPark(String visitorName, int visitorAge, double visitorMoney, int parkId){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            Set<Amusementpark> parksToVisit = new HashSet<Amusementpark>(0);
            parksToVisit.add(park);
            Child visitor = new Child(visitorAge, visitorMoney, visitorName, parksToVisit, null);
            id = (Integer) session.save(visitor);
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static List<Facility> getParkFacilities(int parkId){
        List<Facility> facilitiesList = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            Set<Facility> facilitiesSet = park.getFacilities();
            facilitiesList = new ArrayList<>(facilitiesSet);
            Collections.sort(facilitiesList, new Comparator<Facility>() {
                public int compare(Facility f1, Facility f2) {
                    return f1.getFacilityId().compareTo(f2.getFacilityId());
                }
            });
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return facilitiesList;
    }
    
    public static Amusementpark getPark(int parkId){
        Amusementpark park = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            park = (Amusementpark) session.get(Amusementpark.class, parkId);
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return park;
    }
    
    public static boolean updateParkIncome(int parkId, double money){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            park.setParkTotalIncome(park.getParkTotalIncome() + money);
            session.update(park);
            id = 1;
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static List<Child> getParksVisitors(){
        List<Child> children = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Criteria cr = session.createCriteria(Child.class);
            children = cr.list();
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return children;
    }
    
    public static boolean increaseManagerSalaryIfParkGoalReached(double percent, double goal, int parkId){
        int id = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            if (park.getParkTotalIncome() >= goal){
                double newManagerSalary = park.getManager().getManagerSalary() + (percent / 100) * park.getManager().getManagerSalary();
                park.getManager().setManagerSalary(newManagerSalary);
                session.update(park);
                id = 1;
            }
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static List<Child> getParkVisitors(int parkId){
        List<Child> visitorsList = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Amusementpark park = (Amusementpark) session.get(Amusementpark.class, parkId);
            Set<Child> visitorsSet = park.getChilds();
            visitorsList = new ArrayList<>(visitorsSet);
            Collections.sort(visitorsList, new Comparator<Child>() {
                public int compare(Child v1, Child v2) {
                    return v1.getChildId().compareTo(v2.getChildId());
                }
            });
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return visitorsList;
    }
    
    public static boolean addVisitorToFacility(int parkId, int visitorId, int facilityId){
        int id = 0;
        Amusementpark park = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            int realVisitorID = visitorId;
            int realFacilityID = facilityId;
            if (parkId > 1){
                realVisitorID = 0;
                realFacilityID = 0;
                for (int i = 1; i < parkId; i++){
                    park = (Amusementpark) session.get(Amusementpark.class, i);
                    Set<Child> visitorsSet = park.getChilds();
                    for (Child visitor : visitorsSet){
                        realVisitorID++;
                    }
                    Set<Facility> facilitiesSet = park.getFacilities();
                    for (Facility facility : facilitiesSet){
                        realFacilityID++;
                    }
                }
                realVisitorID = realVisitorID + visitorId;
                realFacilityID = realFacilityID + facilityId;
            }
            Child visitor = (Child) session.get(Child.class, realVisitorID);
            //System.out.println("ID: " + realVisitorID + " / Name: " + visitor.getChildName());
            Facility facility = (Facility) session.get(Facility.class, realFacilityID);
            Set<Child> visitors = facility.getChilds();
            visitors.add(visitor);
            facility.setChilds(visitors);
            id = (Integer) session.save(facility);
            /*If we add more visitors and/or facilities to an existing park after it has been created, 
            the 'Add Visitor To Facility' function does not work correctly. ID numbers get mixed up.*/
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return id > 0;
    }
    
    public static List<Child> getFacilityVisitors(int facilityId, int parkId){
        List<Child> visitorsList = null;
        Amusementpark park = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            int realFacilityID = facilityId;
            if (parkId > 1){
                realFacilityID = 0;
                for (int i = 1; i < parkId; i++){
                    park = (Amusementpark) session.get(Amusementpark.class, i);
                    Set<Facility> facilitiesSet = park.getFacilities();
                    for (Facility facility : facilitiesSet){
                        realFacilityID++;
                    }
                }
                realFacilityID = realFacilityID + facilityId;
            }
            Facility facility = (Facility) session.get(Facility.class, realFacilityID);
            Set<Child> visitorsSet = facility.getChilds();
            visitorsList = new ArrayList<>(visitorsSet);
            Collections.sort(visitorsList, new Comparator<Child>() {
                public int compare(Child v1, Child v2) {
                    return v1.getChildId().compareTo(v2.getChildId());
                }
            });
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return visitorsList;
    }
    
    public static int getFacilityMinAllowedAge(int facilityId, int parkId){
        int age = 0;
        Amusementpark park = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            int realFacilityID = facilityId;
            if (parkId > 1){
                realFacilityID = 0;
                for (int i = 1; i < parkId; i++){
                    park = (Amusementpark) session.get(Amusementpark.class, i);
                    Set<Facility> facilitiesSet = park.getFacilities();
                    for (Facility facility : facilitiesSet){
                        realFacilityID++;
                    }
                }
                realFacilityID = realFacilityID + facilityId;
            }
            Facility facility = (Facility) session.get(Facility.class, realFacilityID);
            age = facility.getFacilityMinAge();
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return age;
    }
    
    public static int getVisitorAge(int visitorId, int parkId){
        int age = 0;
        Amusementpark park = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            int realVisitorID = visitorId;
            if (parkId > 1){
                realVisitorID = 0;
                for (int i = 1; i < parkId; i++){
                    park = (Amusementpark) session.get(Amusementpark.class, i);
                    Set<Child> visitorsSet = park.getChilds();
                    for (Child visitor : visitorsSet){
                        realVisitorID++;
                    }
                }
                realVisitorID = realVisitorID + visitorId;
            }
            Child visitor = (Child) session.get(Child.class, realVisitorID);
            age = visitor.getChildAge();
            tx.commit();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            if (tx != null){
                tx.rollback();
            }
        }finally{
            session.close();
        }
        return age;
    }
}