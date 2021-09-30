package com.ssm.dao.provider;

import com.ssm.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProviderMapper {
    List<Provider> queryProviders(@Param("map") Map<String, Object> map);

    int providerAdd(Provider provider);

    Provider queryProviderById(@Param("id") int id);

    int providerModify(Provider provider);

    int delProvider(@Param("id") int id);
}
