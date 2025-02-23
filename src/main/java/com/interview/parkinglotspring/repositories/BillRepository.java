package com.interview.parkinglotspring.repositories;

import com.interview.parkinglotspring.models.Bill;
import com.interview.parkinglotspring.models.Floor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class BillRepository {
    public static Map<Long, Bill> billMap = new HashMap<>();
    private Long billId = 0L;

    public Optional<Bill> findById(long id) {
        return Optional.ofNullable(billMap.get(id));
    }

    public Bill save(Bill bill) {
        if(billMap.get(bill.getId()) == null) {
            billId +=1;
            bill.setId(billId);
            billMap.put(bill.getId(), bill);
        }else{
            billMap.put(bill.getId(), bill);
        }
        return bill;
    }
}
