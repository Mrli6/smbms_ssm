package com.ssm.controller.bill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import com.ssm.pojo.Bill;
import com.ssm.pojo.Provider;
import com.ssm.pojo.User;
import com.ssm.service.bill.BillService;
import com.ssm.service.provider.ProviderService;
import com.ssm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;


    // 查询部分或所有===================================================================================================
    @RequestMapping("/bill.do")
    public String goBill(@RequestParam(name = "queryProductName", required = false)String queryProductName,
                         @RequestParam(name = "queryProviderId", required = false)String queryProviderId,
                         @RequestParam(name = "queryIsPayment", required = false)String queryIsPayment, Model model){
        Map<String, Object> map = new HashMap<>();
        map.put("proCoe", "");
        map.put("proName", "");
        List<Provider> providerList = providerService.queryProviders(map);
        model.addAttribute("providerList", providerList);


        if(queryProductName != null){
            queryProductName = queryProductName.trim();
        }
        int id = 0;
        if(!StringUtils.isNullOrEmpty(queryProviderId)){
            id = Integer.parseInt(queryProviderId);
        }
        int pay = 0;
        if(!StringUtils.isNullOrEmpty(queryIsPayment)){
            pay = Integer.parseInt(queryIsPayment);
        }
        map.put("queryProductName", queryProductName);
        map.put("queryProviderId", id);
        map.put("queryIsPayment", pay);

        List<Bill> billList = billService.queryBills(map);

        model.addAttribute("billList", billList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);

        return "billlist";
    }



    // 增加=============================================================================================================
    @RequestMapping("/billadd.do")
    public String goBillAdd(){
        return "billadd";
    }

    @RequestMapping("/getproviderlist")
    @ResponseBody
    public String getProviderList() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("proCoe", "");
        map.put("proName", "");
        List<Provider> providerList = providerService.queryProviders(map);

        return new ObjectMapper().writeValueAsString(providerList);
    }

    @RequestMapping("/billadd")
    public String billAdd(HttpServletRequest req, HttpServletResponse resp, Bill bill) throws IOException, ServletException {
        bill.setCreatedBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        if(billService.billAdd(bill) > 0){
            return "redirect:/bill/bill.do";
        }else {
            return "billadd";
        }
    }


    // 查看详细=========================================================================================================
    @RequestMapping("/viewbill")
    public String viewBill(@RequestParam("billid") String id , Model model){
        Bill bill = billService.queryBillById(Integer.parseInt(id));
        model.addAttribute("bill", bill);

        return "billview";
    }


    // 修改=============================================================================================================
    @RequestMapping("/modifybill")
    public String goModify(@RequestParam(value = "billid") String id, Model model){
        Bill bill = billService.queryBillById(Integer.parseInt(id));
        model.addAttribute("bill", bill);

        return "billmodify";
    }

    @RequestMapping("/modifysave")
    public String modifySave(HttpServletRequest req, HttpServletResponse resp, Bill bill) throws IOException, ServletException {
        bill.setModifyBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        bill.setModifyDate(new Date());

        if(billService.modifyBill(bill) > 0){
            return "redirect:/bill/bill.do";
        }else{
            return "billmodify";
        }
    }



    // 删除
    @RequestMapping("/delbill")
    @ResponseBody
    public String delBill(@RequestParam("billid") String id) throws IOException {
        Map<String, String> resultMap = new HashMap<>();

        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            int delId = Integer.parseInt(id);
            if(delId <= 0){
                resultMap.put("delResult", "notexist");
            }else{
                if(billService.delBill(delId) > 0){
                    resultMap.put("delResult", "true");
                }else{
                    resultMap.put("delResult", "false");
                }
            }
        }

        return new ObjectMapper().writeValueAsString(resultMap);
    }
}