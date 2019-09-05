package amusementparkssystem;
// Generated Jan 12, 2019 11:54:16 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Amusementpark generated by hbm2java
 */
@Entity
@Table(name="amusementpark"
    ,catalog="amusementparksdatabase"
)
public class Amusementpark  implements java.io.Serializable {


     private Integer parkId;
     private Manager manager;
     private Double parkTicketPrice;
     private Double parkTotalIncome;
     private String parkName;
     private Set<Facility> facilities = new HashSet<Facility>(0);
     private Set<Child> childs = new HashSet<Child>(0);

    public Amusementpark() {
    }

    public Amusementpark(Manager manager, Double parkTicketPrice, Double parkTotalIncome, String parkName, Set<Facility> facilities, Set<Child> childs) {
       this.manager = manager;
       this.parkTicketPrice = parkTicketPrice;
       this.parkTotalIncome = parkTotalIncome;
       this.parkName = parkName;
       this.facilities = facilities;
       this.childs = childs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="parkId", unique=true, nullable=false)
    public Integer getParkId() {
        return this.parkId;
    }
    
    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="Manager_managerId")
    public Manager getManager() {
        return this.manager;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    
    @Column(name="parkTicketPrice", precision=22, scale=0)
    public Double getParkTicketPrice() {
        return this.parkTicketPrice;
    }
    
    public void setParkTicketPrice(Double parkTicketPrice) {
        this.parkTicketPrice = parkTicketPrice;
    }

    
    @Column(name="parkTotalIncome", precision=22, scale=0)
    public Double getParkTotalIncome() {
        return this.parkTotalIncome;
    }
    
    public void setParkTotalIncome(Double parkTotalIncome) {
        this.parkTotalIncome = parkTotalIncome;
    }

    
    @Column(name="parkName", length=45)
    public String getParkName() {
        return this.parkName;
    }
    
    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="amusementpark")
    public Set<Facility> getFacilities() {
        return this.facilities;
    }
    
    public void setFacilities(Set<Facility> facilities) {
        this.facilities = facilities;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="childpark", catalog="amusementparksdatabase", joinColumns = { 
        @JoinColumn(name="ChildPark_parkId", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="ChildPark_childId", nullable=false, updatable=false) })
    public Set<Child> getChilds() {
        return this.childs;
    }
    
    public void setChilds(Set<Child> childs) {
        this.childs = childs;
    }




}

