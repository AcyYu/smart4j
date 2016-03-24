package org.smart4j.chapter1.service;

import org.smart4j.chapter1.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * 客户业务逻辑
 */
public class CustomerService {
    /**
     * 获取客户列表
     * @param keyword 关键字
     * @return 客户列表
     */
    public List<Customer> getCustomerList(String keyword){
        // TODO
        return null;
    }

    /**
     * 获取客户列表
     * @return 客户列表
     */
    public List<Customer> getCustomerList(){
        // TODO
        return null;
    }

    /**
     * 根据id获取客户信息
     * @param id 主键id
     * @return 客户信息
     */
    public Customer getCustomer(Long id){
        // TODO
        return null;
    }

    /**
     * 创建新客户信息
     * @param fieldMap 客户信息map
     * @return 创建结果
     */
    public Boolean createCustomer(Map<String,Object> fieldMap){
        // TODO
        return false;
    }

    /**
     * 更新客户信息
     * @param id 主键
     * @param fieldMap 客户信息map
     * @return 更新结果
     */
    public Boolean updateCustomer(Long id, Map<String,Object> fieldMap){
        // TODO
        return false;
    }

    /**
     * 删除客户信息
     * @param id 主键
     * @return 删除结果
     */
    public Boolean deleteCustomer(Long id){
        // TODO
        return false;
    }
}
