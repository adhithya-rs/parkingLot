package com.interview.parkinglotspring.controllers;

import com.interview.parkinglotspring.dtos.GenerateBillResponseDto;
import com.interview.parkinglotspring.dtos.GeneratePaymentResponseDto;
import com.interview.parkinglotspring.dtos.GenerateTicketRequestDto;
import com.interview.parkinglotspring.dtos.GenerateTicketResponseDto;
import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Gate;
import com.interview.parkinglotspring.models.enums.*;
import com.interview.parkinglotspring.factories.ParkingSpotAssignmentStrategyFactory;
import com.interview.parkinglotspring.models.Floor;
import com.interview.parkinglotspring.models.ParkingLot;
import com.interview.parkinglotspring.repositories.FloorRepository;
import com.interview.parkinglotspring.repositories.GateRepository;
import com.interview.parkinglotspring.repositories.ParkingLotRepository;
import com.interview.parkinglotspring.services.BillService;
import com.interview.parkinglotspring.services.ParkingLotService;
import com.interview.parkinglotspring.services.PaymentService;
import com.interview.parkinglotspring.singleton.ScannerObject;
import com.interview.parkinglotspring.strategies.parkingSpotAssignmentStrategy.ParkingSpotAssignmentStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Setter
@Component
public class ParkingLotController {
    private final GateRepository gateRepository;
    private Scanner sc = ScannerObject.getInstance();
    private FloorRepository floorRepo;
    private TicketController ticketController;
    private ParkingLotRepository parkingLotRepo;
    private BillService billService;
    private PaymentService paymentService;
    private ParkingLotService parkingLotService;


    @Autowired  // Ensure this is present
    public ParkingLotController(ParkingLotRepository parkingLotRepo, TicketController ticketController, FloorRepository floorRepo, BillService billService, PaymentService paymentService, ParkingLotService parkingLotService, GateRepository gateRepository) {
        this.parkingLotRepo = parkingLotRepo;
        this.ticketController = ticketController;
        this.floorRepo = floorRepo;
        this.billService = billService;
        this.paymentService = paymentService;
        this.parkingLotService = parkingLotService;
        this.gateRepository = gateRepository;
    }


    //--------------------------------------------->
    public ParkingLot setUpLot(){
        System.out.println("Enter Parking Lot name:");
        String parkingLotName = sc.nextLine();
        System.out.println("Enter Parking Lot Address:");
        String parkingLotAddress = sc.nextLine();

        //Getting details of floor
        System.out.println("Enter Number Of Floors:");
        Long numberOfFloors = Long.parseLong(sc.nextLine());
        System.out.println("Enter Number Of cars can be parked:");
        Long numberOfCars = Long.parseLong(sc.nextLine());
        System.out.println("Enter Number Of bikes can be parked:");
        Long numberOfBikes = Long.parseLong(sc.nextLine());

        //Getting Gate Details
        System.out.println("Enter Number Of entry Gates:");
        Long numberOfEntryGates = Long.parseLong(sc.nextLine());
        System.out.println("Enter Number Of Exit Gates:");
        Long numberOfExitGates = Long.parseLong(sc.nextLine());
        List<String> operatorNames = new ArrayList<>();
        for(int i=1;i<=numberOfEntryGates+numberOfExitGates;i++){
            System.out.println("Enter Operator-"+i+" Name:");
            operatorNames.add(sc.nextLine());
        }


        return parkingLotRepo.save(ParkingLot.getBuilder()
                .setName(parkingLotName)
                .setAddress(parkingLotAddress)
                .setFloors(numberOfFloors, numberOfCars, numberOfBikes)
                .setGates(numberOfEntryGates, numberOfExitGates, operatorNames)
                .build());
    }


    //---------------------------------------------->
    public void entry(ParkingLot parkingLot){
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter Gate Number");
        Long gateId = Long.parseLong(sc.nextLine());
        if(!isValidEntryGateId(gateId)){
            System.out.println("Invalid Gate Number!!\n");
            return;
        }
        System.out.println("Enter Vehicle Number");
        String vehicleNumber = sc.nextLine();
        VehicleType vehicleType = getVehicleType();
        ParkingSpotAssignmentStrategyType parkingSpotAssignmentStrategyType = getParkingSpotAssignmentStrategy(parkingLot.getFloors(), vehicleType);
        if(parkingSpotAssignmentStrategyType == null){
            return;
        }
        ParkingSpotAssignmentStrategy parkingSpotAssignmentStrategy = ParkingSpotAssignmentStrategyFactory.getParkingSpotAssignmentStrategy(parkingSpotAssignmentStrategyType);
        GenerateTicketRequestDto generateTicketRequestDto = new GenerateTicketRequestDto();
        generateTicketRequestDto.setVehicleNumber(vehicleNumber);
        generateTicketRequestDto.setGateId(gateId);
        generateTicketRequestDto.setOwnerName(name);
        generateTicketRequestDto.setParkingSpotAssignmentStrategy(parkingSpotAssignmentStrategy);
        generateTicketRequestDto.setVehicleType(vehicleType);
        GenerateTicketResponseDto generateTicketResponseDto = ticketController.generateTicket(generateTicketRequestDto);

        if(generateTicketResponseDto.getResponseStatus() == ResponseStatus.SUCCESS){
            System.out.println("Your Ticket has been generated");
            generateTicketResponseDto.getTicket().printTicket();
            parkingLotService.onVehicleEntry(generateTicketResponseDto.getTicket());
        }else{
            System.out.println(generateTicketResponseDto.getMessage());
        }
    }

    private boolean isValidEntryGateId(Long gateId) {
        Optional<Gate> optionalGate = gateRepository.findById(gateId);
        Gate gate = optionalGate.get();
        System.out.println(gate.getId() +" Type: "+gate.getGateType());
        return gate.getGateType().equals(GateType.ENTRY);
    }

    private ParkingSpotAssignmentStrategyType getParkingSpotAssignmentStrategy(List<Floor> floors, VehicleType vehicleType){

        TreeSet<FloorType> floorTypes = new TreeSet<>();
        for(Floor floor : floors){
            if(floor.getVehicleTypeParkingAvailabilityStatus().get(vehicleType).equals(VehicleTypeParkingAvailabilityStatus.AVAILABLE)){
                floorTypes.add(floor.getFloorType());
            }
        }
        if(floorTypes.isEmpty()) {
            System.out.println("No parking spots available.");
            return null;
        }
        System.out.println("Select Parking Preferences\n1. Nearest\n2. Random");
        int i=3;
        for(FloorType floorType : floorTypes){
            System.out.println(i+". " + floorType);
            i++;
        }
        System.out.println("10. If you are not interested in parking");
        int choice = Integer.parseInt(sc.nextLine());
        return switch (choice) {
            case 1 -> ParkingSpotAssignmentStrategyType.NEAREST;
            case 2 -> ParkingSpotAssignmentStrategyType.RANDOM;
            case 3 -> ParkingSpotAssignmentStrategyType.CHEAPEST;
            case 4 -> ParkingSpotAssignmentStrategyType.VIP;
            case 10 -> null;
            default -> {
                System.out.println("Invalid choice!!  Try Again");
                yield getParkingSpotAssignmentStrategy(floors, vehicleType );
            }
        };
    }

    private VehicleType getVehicleType(){
        System.out.println("Enter Vehicle Type\n1. Car\n2. Bike");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice){
            case 1:
                return VehicleType.CAR;
                case 2:
                    return VehicleType.BIKE;
                    default:
                        System.out.println("Invalid Vehicle Type!! Try Again");
        }
        return getVehicleType();
    }


    //---------------------------------------------->
    public void exit(ParkingLot parkingLot){
        System.out.println("Enter the gate Number");
        Long gateId = Long.parseLong(sc.nextLine());
        if(!isValidExitGateId(gateId)){
            System.out.println("Invalid Gate Number!!\n");
            return;
        }
        System.out.println("Enter the ticket number");
        String ticketNumber = sc.nextLine();
        GenerateBillResponseDto generateBillResponseDto =  billService.generateBill(ticketNumber, gateId);
        if(generateBillResponseDto.getResponseStatus().equals(ResponseStatus.FAILURE)){
            System.out.println(generateBillResponseDto.getMessage());
            return;
        }
        Bill bill = generateBillResponseDto.getBill();
        System.out.println("\nYour bill has been generated:\n");
        GeneratePaymentResponseDto generatePaymentResponseDto = paymentService.getPayments(bill.getAmount());
        bill.setPayments(generatePaymentResponseDto.getPayments());

        bill.printBill();
        parkingLotService.onVehicleExit(bill);
        System.out.println("Goodbye "+bill.getTicket().getVehicle().getOwnerName());
    }

    private boolean isValidExitGateId(Long gateId) {
        Optional<Gate> optionalGate = gateRepository.findById(gateId);
        return optionalGate.map(gate -> gate.getGateType().equals(GateType.EXIT)).orElse(false);
    }
}
