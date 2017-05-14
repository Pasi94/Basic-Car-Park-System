package VehicleParkSystem;

/**
 * Created by Pasindu on 11/16/2016.
 */
public class Van extends Vehicle {
    private String cargoInfo;

    Van(String idPlate, String brandOfVehicle, String cargoInfo) {
        super(idPlate, brandOfVehicle);
        this.cargoInfo = cargoInfo;
    }
}
