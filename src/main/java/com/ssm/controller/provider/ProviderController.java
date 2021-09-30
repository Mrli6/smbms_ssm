package com.ssm.controller.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;
    @Autowired
    private BillService billService;

    // 查看部分或所有===================================================================================================
    @RequestMapping("/provider.do")
    public String goProvier(@RequestParam(value = "queryProCode", required = false) String queryProCode,
                            @RequestParam(value = "queryProName", required = false) String queryProName, Model model){
        Map<String, Object> map = new HashMap<>();
        if(queryProCode != null){
            queryProCode = queryProCode.trim();
        }
        if(queryProName != null){
            queryProName = queryProName.trim();
        }
        map.put("proCode", queryProCode);
        map.put("proName", queryProName);
        List<Provider> providerList = providerService.queryProviders(map);

        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProCode", queryProCode);
        model.addAttribute("queryProName", queryProName);

        return "providerlist";
    }



    // 增加=============================================================================================================
    @RequestMapping("/provideradd.do")
    public String goProviderAdd(){
        return "provideradd";
    }

    @RequestMapping("/provideradd")
    public String providerAdd(HttpServletRequest req, HttpServletResponse resp, Provider provider) throws IOException, ServletException {
        provider.setCreatedBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        if(providerService.providerAdd(provider) > 0){
            return "redirect:/provider/provider.do";
        }else{
            return "provideradd";
        }
    }



    // 查看详细=========================================================================================================
    @RequestMapping("/viewprovider")
    public String viewProvider(@RequestParam("proid") String id, Model model){
        Provider provider = providerService.queryProviderById(Integer.parseInt(id));
        model.addAttribute("provider", provider);

        return "providerview";
    }



    // 修改=============================================================================================================
    @RequestMapping("/modifyprovider")
    public String goModify(@RequestParam("proid") String id, Model model){
        Provider provider = providerService.queryProviderById(Integer.parseInt(id));
        model.addAttribute("provider", provider);

        return "providermodify";
    }

    @RequestMapping("/modifysave")
    public String modifySave(HttpServletRequest req, HttpServletResponse resp, Provider provider) throws IOException, ServletException {
        provider.setModifyBy(((User)req.getSession().getAttribute(Constant.USER_SESSION)).getId());
        provider.setModifyDate(new Date());

        if(providerService.providerModify(provider) > 0){
            return "redirect:/provider/provider.do";
        }else{
            return "providermodify";
        }
    }



    // 删除=============================================================================================================
    @RequestMapping("/delprovider")
    @ResponseBody
    public String delProvider(@RequestParam("proid") String id) throws JsonProcessingException {
        Map<String, Object> resultMap = new HashMap<>();

        int delId = Integer.parseInt(id);
        int count = billService.queryBillByProviderId(delId);
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else if(count > 0){
            resultMap.put("delResult", count);
        }else{
            if(providerService.delProvider(delId) > 0){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }

        return new ObjectMapper().writeValueAsString(resultMap);
    }
}
