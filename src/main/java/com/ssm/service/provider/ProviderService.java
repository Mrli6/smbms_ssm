package com.ssm.service.provider;

import com.ssm.pojo.Provider;

import java.util.List;
import java.util.Map;

public interface ProviderService {

    List<Provider> queryProviders(Map<String, Object> map);

    int providerAdd(Provider provider);

    Provider queryProviderById(int id);

    int providerModify(Provider provider);

    int delProvider(int id);
}
