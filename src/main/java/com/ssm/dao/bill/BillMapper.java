package com.ssm.dao.bill;

import com.ssm.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BillMapper {
    List<Bill> queryBills(@Param("map")Map<String, Object> map);

    int addBill(Bill bill);

    Bill queryBillById(@Param("id") int id);

    int modifyBill(Bill bill);

    int delBill(@Param("id") int id);

    List<Bill> queryBillByProviderId(@Param("id") int id);

}
