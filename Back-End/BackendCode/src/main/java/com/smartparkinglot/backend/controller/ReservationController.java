package com.smartparkinglot.backend.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartparkinglot.backend.entity.*;
import com.smartparkinglot.backend.service.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final TokenService tokenService;
    private final ParkingSpotService parkingSpotService;
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, TokenService tokenService, ParkingSpotService parkingSpotService, UserService userService, CarService carService) {
        this.reservationService = reservationService;
        this.tokenService = tokenService;
        this.parkingSpotService = parkingSpotService;
        this.carService = carService;
        this.userService = userService;
    }
    @GetMapping("get-own-active-reservations")
    public ResponseEntity<?> getOwnReservations(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);// Assuming the scheme is "Bearer "
        if(tokenService.validateToken(token)) {
            User userAuthorized = tokenService.getUserByToken(token);

            return ResponseEntity.ok().body(reservationService.getOwnActiveReservations(userAuthorized.getEmail()).stream().map(reservation -> {
                return new ReservationDetails(reservation.getId(), reservation.getCar_id().getId(), reservation.getParkingSpot().getId(), reservation.getStartTime(), reservation.getStopTime(), reservation.getStatus());
            }));
        }
        else {
            return ResponseEntity.badRequest().body("Authentication token invalid. Protected resource could not be accessed");
        }
    }
    @GetMapping("get-user-reservations")
    public ResponseEntity<?> getUsersReservations(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String userEmail) {
        String token = authorizationHeader.substring(7);// Assuming the scheme is "Bearer "
        if(tokenService.validateToken(token)) {
            User userAuthorized = tokenService.getUserByToken(token);
            if(userAuthorized.getType() == 2 ) {
                if(!userService.existsByEmail(userEmail)) {
                    return ResponseEntity.badRequest().body("User for which data is requested does not exist");
                }
                return ResponseEntity.ok().body(reservationService.getUsersReservations(userEmail).stream().map(reservation -> {
                    return new ReservationDetails(reservation.getId(), reservation.getCar_id().getId(), reservation.getParkingSpot().getId(), reservation.getStartTime(), reservation.getStopTime(), reservation.getStatus());
                }));
            }
            else {
                return ResponseEntity.badRequest().body("User is not administrator");
            }
        }
        else {
            return ResponseEntity.badRequest().body("Authentication token invalid. Protected resource could not be accessed");
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> registerNewReservation(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ReservationRequest reservationRequest) {
        String token = authorizationHeader.substring(7);// Assuming the scheme is "Bearer "

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC+3"));
        Timestamp startTimestamp = Timestamp.from(Instant.from(formatter.parse(reservationRequest.startTime)));
        Timestamp endTimestamp = Timestamp.from(Instant.from(formatter.parse(reservationRequest.endTime)));

        if(tokenService.validateToken(token)) {
            User userAuthorized = tokenService.getUserByToken(token);
            if(parkingSpotService.checkParkingSpotAvailability(reservationRequest.spotID, startTimestamp, endTimestamp)) {
                int reservationCost = parkingSpotService.calculateReservationCost(startTimestamp, endTimestamp, reservationRequest.spotID);
                if(reservationCost < userAuthorized.getBalance()) {
                    Car userCar = new Car(carService.getPlateById(reservationRequest.carId), reservationRequest.carCapacity, reservationRequest.carType, userAuthorized);
                    carService.addNewCar(userCar);
                    return reservationService.createReservation(userAuthorized, reservationRequest.spotID, startTimestamp, endTimestamp, reservationCost, userCar);
                }
                else {
                    return ResponseEntity.badRequest().body("User does not have enough money in his balance");
                }

            }
            else {
                return ResponseEntity.badRequest().body("Parking spot not available");
            }
        }
        else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class ReservationRequest {
        private Long spotID;
        private String startTime;
        private String endTime;
        private Long carId;
        private int carCapacity;
        private String carType;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class ReservationDetails {
        private Long id;
        private Long car_id;
        private Long parking_spot_id;
        private Timestamp start_time;
        private Timestamp stop_time;
        private String status;
    }
}
