package com.ssm.service.bill;

import com.ssm.dao.bill.BillMapper;
import com.ssm.pojo.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public List<Bill> queryBills(Map<String, Object> map) {
        return billMapper.queryBills(map);
    }

    @Override
    public int billAdd(Bill bill) {
        return billMapper.addBill(bill);
    }

    @Override
    public Bill queryBillById(int id) {
        return billMapper.queryBillById(id);
    }

    @Override
    public int modifyBill(Bill bill) {
        return billMapper.modifyBill(bill);
    }

    @Override
    public int delBill(int id) {
        return billMapper.delBill(id);
    }

    @Override
    public int queryBillByProviderId(int id) {
        int count = 0;
        List<Bill> billList = billMapper.queryBillByProviderId(id);
        for(Bill bill : billList){
            count++;
        }

        return  count;
    }
}
