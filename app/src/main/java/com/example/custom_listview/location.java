package com.example.custom_listview;

public class location {

    long corD;
    String countryOrRegion;
    String provinceOrState;
    String county;
    String isoCode;
    long lat;

    public location(long corD, String countryOrRegion, String provinceOrState, String county, String isoCode, long lat) {
        this.corD = corD;
        this.countryOrRegion = countryOrRegion;
        this.provinceOrState = provinceOrState;
        this.county = county;
        this.isoCode = isoCode;
        this.lat = lat;
    }
}
