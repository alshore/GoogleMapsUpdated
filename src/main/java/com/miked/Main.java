package com.miked;

import com.google.maps.*;
import com.google.maps.model.*;
import com.sun.jndi.cosnaming.IiopUrl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public static void main(String[] args)throws Exception{
//https://developers.google.com/maps/documentation/geocoding/intro

        String key = null;//The following try/catch will throw an exception if my key.txt file is FUBAR.
        try{
            BufferedReader reader = new BufferedReader(new FileReader("geokey.txt"));
            key = reader.readLine();//This holds the contents of key.txt if it exists.
            System.out.println(key);//This prints my key if it exists.
        }
        catch (Exception ioe){
            System.out.println("No key file found, or could not be read.");
            System.exit(-1);
        }
        GeoApiContext context = new GeoApiContext().setApiKey(key);
        GeocodingApiRequest locationRequest = new GeocodingApiRequest(context);
        System.out.println("Enter a city to see its latitude and longitude.");
        String location = stringScanner.nextLine();
        //String baseURL = String.format("https://maps.googleapi.com/maps/api/geocode/json?address=%s&key=%s", location, key);

        //geocodingResultsArray
        GeocodingResult[] geocodingResultsArray = locationRequest.address(location).await();
        //System.out.println(baseURL);
        //System.out.println(geocodingResults[0]);
        Bounds bounds = new Geometry().bounds;
        //System.out.println(locationRequest);
        //System.out.println("array"+geocodingResultsArray[1]);
        //todo loop over geocodingResultsArray.  formattedAddress.
        ArrayList<String> addy = new ArrayList<>();
        if (geocodingResultsArray.length > 0) {
            for(int i =0; i<geocodingResultsArray.length; i++) {
                GeocodingResult firstGeocodingResult = geocodingResultsArray[i];
                LatLng latlong = firstGeocodingResult.geometry.location;//geometry and location are tags under "GeocodingResult".
                String LatLong = latlong.toString();
                LatLong = firstGeocodingResult.formattedAddress;
                addy.add(LatLong);
                System.out.println(latlong);
                System.out.println(LatLong);
                System.out.println(addy.get(i));
                //todo - Use the latlong object to make an elevation request.
                ElevationResult[] elevationResultsArray = ElevationApi.getByPoints(context, latlong).await();
            }
        } else {
            System.out.println("No results found");
            //todo ask user again, or whatever
        }
        System.out.println(addy);
        System.out.println("Was your location in the list? (y or n)");
        String searchSucessful = stringScanner.nextLine();
            if(searchSucessful.equalsIgnoreCase("n")){
                System.out.println("Be more specific please");
                String addedInfo = stringScanner.nextLine();
                geocodingResultsArray = locationRequest.address(addedInfo).await();
                for (int i =0; i < geocodingResultsArray.length; i++){
                    GeocodingResult secondGeocodingResult = geocodingResultsArray[i];
                    LatLng latLng = secondGeocodingResult.geometry.location;
                    String LatLng = latLng.toString();
                    LatLng = secondGeocodingResult.formattedAddress;
                    addy.add(LatLng);
                    System.out.println(latLng);
                    System.out.println(LatLng);
                    System.out.println(addy.get(i));
                }
            }



//public class Main {
//
//    static Scanner stringScanner = new Scanner(System.in);
//
//    public static void main(String[] args)throws Exception{
//
//        String key = null;
//
//        try(BufferedReader reader = new BufferedReader(new FileReader("geoKey.txt"))){
//            key = reader.readLine();
//            System.out.println(key);
//
//        }catch (Exception ioe){
//            System.out.println("No key file found, or could not read key. Please verify key.txt present");
//            System.exit(-1);
//        }
//        GeoApiContext context = new GeoApiContext().setApiKey(key);
//        GeocodingApiRequest locationRequested= new GeocodingApiRequest(context);
//
//        System.out.println("Enter a place...");
//        String entry = stringScanner.nextLine();
//
//        GeocodingResult[] result = locationRequested.address(entry).await();
//
//
////        GeocodingResult[] result = GeocodingApi.geocode(context, entry).await();
//        Bounds bounds = new Geometry().bounds;
//       // System.out.println(locationRequested);
//        System.out.println("Bounds " +bounds);
//
//        if (result.length > 0) {
//            for (int i = 0; i < result.length; i++) {
//                System.out.println("result " + result[i]);
//                //System.out.println("LR " + locationRequested.address(entry));
//            }
//
//                GeocodingResult firstGeocodingResult = result[0];
//                LatLng latLng = firstGeocodingResult.geometry.location;
//                // = GeocodingApi.reverseGeocode(context,latLng);
//
//                System.out.println("Latlng: " + latLng);
//            System.out.println("RLR" + locationRequested);
//
//
//        }

        //LatLng mctcLatLng = new LatLng(44.973074, -93.283356);

       //ElevationResult[] results = ElevationApi.getByPoints(context, mctcLatLng).await();

        //if (results.length >= 1){
            //ElevationResult mctcElevation = results[0];
            //System.out.println("The elevation of MCTC above sea level is " + mctcElevation+ " meters");
            //System.out.println(String.format("The elevation of MCTC above sea level is %.2f meters.", mctcElevation.elevation));
        }
    }

