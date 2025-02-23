package com.interview.parkinglotspring;

import com.interview.parkinglotspring.controllers.ParkingLotController;
import com.interview.parkinglotspring.dtos.GenerateTicketResponseDto;
import com.interview.parkinglotspring.models.ParkingLot;
import com.interview.parkinglotspring.singleton.ScannerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Scanner;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ParkingLotSpringApplication {

	public static ParkingLotController controller;

	@Autowired
	public ParkingLotSpringApplication(ParkingLotController controller){
		ParkingLotSpringApplication.controller = controller;

	}

	public static void main(String[] args) {

		SpringApplication.run(ParkingLotSpringApplication.class, args);
		Scanner sc = ScannerObject.getInstance();

		ParkingLot parkingLot =  controller.setUpLot();

		while(true){
			parkingLot.printParkingLot();

			System.out.println("\nWelcome to "+ parkingLot.getName() +"\n1. Enter the parking lot\n2. Exit the parking lot\n3. Terminate the Application");
			int choice = Integer.parseInt(sc.nextLine());
			if(choice == 1){
				controller.entry(parkingLot);
			}else if(choice == 2){
				controller.exit(parkingLot);
			}else if(choice == 3){
				break;
			}else {
				System.out.println("Invalid choice");
			}
		}

		ScannerObject.close();
	}

}
