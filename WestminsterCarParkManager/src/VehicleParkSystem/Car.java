package VehicleParkSystem;

/**
 * Created by Pasindu on 11/16/2016.
 */
public class Car extends Vehicle {
    private int noOfDoors;
    private String vehicleColor;

    Car(String idPlate, String brandOfVehicle, int noOfDoors, String vehicleColor) {
        super(idPlate, brandOfVehicle);
        this.noOfDoors = noOfDoors;
        this.vehicleColor = vehicleColor;
    }
}