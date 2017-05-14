package VehicleParkSystem;

/**
 * Created by Pasindu on 11/16/2016.
 */
public abstract class Vehicle {
    private String idPlate = "e", brandOfVehicle, entranceDate, entranceTime;

    Vehicle(String idPlate, String brandOfVehicle) {
        super();
        this.idPlate = idPlate;
        this.brandOfVehicle = brandOfVehicle;

        DateTime dt = new DateTime();

        entranceDate = dt.getDate();
        entranceTime = dt.getTime();
    }

    String getIdPlate() {
        return idPlate;
    }

    String getBrandOfVehicle() {
        return brandOfVehicle;
    }

    String getEntranceDate() {
        return entranceDate;
    }

    String getEntranceTime() {
        return entranceTime;
    }
}
