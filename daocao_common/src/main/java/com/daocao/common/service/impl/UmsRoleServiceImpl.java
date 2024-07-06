package com.daocao.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daocao.common.domain.entity.UmsRole;
import com.daocao.common.mapper.UmsRoleMapper;
import com.daocao.common.service.IUmsRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {
    @Override
    public List<UmsRole> selectByUserId(Long userid) {
        return List.of();
    }
}
