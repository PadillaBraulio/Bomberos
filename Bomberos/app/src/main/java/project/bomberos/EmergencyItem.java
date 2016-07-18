package project.bomberos;

import java.io.Serializable;

/**
 * Created by root on 18/07/16.
 */

public class EmergencyItem {
    private final String id;
    private final String latitude;
    private final String longitude;
    private final String telefone;
    private final String date;
    private final String ambulance;

    public EmergencyItem(String id, String latitude, String longitude, String telefone, String date, String ambulance) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefone = telefone;
        this.date = date;
        this.ambulance = ambulance;
    }

    public String getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getAmbulance() {
        return ambulance;
    }

    @Override
    public String toString() {
        return "EmergencyItem{" +
                "id='" + id + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", telefone='" + telefone + '\'' +
                ", date='" + date + '\'' +
                ", ambulance='" + ambulance + '\'' +
                '}';
    }
}