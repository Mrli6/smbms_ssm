package com.ssm.service.provider;

import com.ssm.dao.provider.ProviderMapper;
import com.ssm.pojo.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public List<Provider> queryProviders(Map<String, Object> map) {
        return providerMapper.queryProviders(map);
    }

    @Override
    public int providerAdd(Provider provider) {
        return providerMapper.providerAdd(provider);
    }

    @Override
    public Provider queryProviderById(int id) {
        return providerMapper.queryProviderById(id);
    }

    @Override
    public int providerModify(Provider provider) {
        return providerMapper.providerModify(provider);
    }

    @Override
    public int delProvider(int id) {
        return providerMapper.delProvider(id);
    }
}
