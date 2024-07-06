package com.daocao.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.domain.entity.UmsRole;

import java.util.List;

public interface IUmsRoleService extends IService<UmsRole> {
    List<UmsRole> selectByUserId(Long userid);

}
