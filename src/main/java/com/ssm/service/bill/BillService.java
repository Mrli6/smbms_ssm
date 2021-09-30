package com.ssm.service.bill;

import com.ssm.pojo.Bill;

import java.util.List;
import java.util.Map;

public interface BillService {
    List<Bill> queryBills(Map<String, Object> map);

    int billAdd(Bill bill);

    Bill queryBillById(int id);

    int modifyBill(Bill bill);
    
    int delBill(int id);

    int queryBillByProviderId(int id);
}
