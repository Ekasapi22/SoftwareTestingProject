package org.mr.abschlussprojekt.bikeRental.database.services;

import javafx.collections.ObservableList;
import org.mr.abschlussprojekt.bikeRental.database.DatabaseManager;
import org.mr.abschlussprojekt.bikeRental.database.daos.StationDao;
import org.mr.abschlussprojekt.bikeRental.model.Station;
import org.mr.abschlussprojekt.bikeRental.setting.AppTexts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StationService {

    private final Connection connection;

    public StationService(){
        this.connection = DatabaseManager.getInstance().getConnection();
    }


    public ObservableList<Station> getAllStations() {
        return StationDao.getAllStations(connection);
    }

    public int getStationIdByLocation(String stationAddress) {
        return StationDao.getStationIdByLocation(stationAddress, connection);
    }

    //added for testing
    public String getStationLocationById(int stationId)
    {
        return StationDao.getStationLocationByID(stationId,connection);
    }



}
