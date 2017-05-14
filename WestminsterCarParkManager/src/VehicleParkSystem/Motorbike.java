package VehicleParkSystem;

/**
 * Created by Pasindu on 11/16/2016.
 */
public class Motorbike extends Vehicle {
    private int engineCapacity;

    Motorbike(String idPlate, String brandOfVehicle, int engineCapacity) {
        super(idPlate, brandOfVehicle);
        this.engineCapacity = engineCapacity;
    }
}
