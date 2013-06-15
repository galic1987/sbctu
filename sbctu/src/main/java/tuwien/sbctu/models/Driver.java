/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tuwien.sbctu.models;

/**
 *
 * @author Adnan
 */
public class Driver extends Person{
    
    public enum DriverStatus{
       WAITING, DRIVING;
    }
    
    public Driver(Long id){
        super(id);
        setDriverStatus(DriverStatus.WAITING);
    }
    
    DriverStatus driverStatus;

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }
    
}
