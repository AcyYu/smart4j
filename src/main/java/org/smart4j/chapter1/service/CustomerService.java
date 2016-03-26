package org.smart4j.chapter1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.model.Customer;

import java.util.List;
import java.util.Map;


/**
 * 客户业务逻辑
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     *
     * @param keyword 关键字
     * @return 客户列表
     */
    public List<Customer> getCustomerList(String keyword) {
        String sql = "SELECT c.id,c.name,c.contact,c.email,c.telephone,c.remark FROM customer c";
        return DatabaseHelper.queryEntityListByKeyword(Customer.class, sql, keyword, "name", "contact");
    }

    /**
     * 获取客户列表
     *
     * @return 客户列表
     */
    public List<Customer> getCustomerList() {
        LOGGER.info("查询客户列表");
        String sql = "SELECT c.id,c.name,c.contact,c.email,c.telephone,c.remark FROM customer c";
        List<Customer> customerList = DatabaseHelper.queryEntityList(Customer.class, sql);
        return customerList;
    }

    /**
     * 根据id获取客户信息
     *
     * @param id 主键id
     * @return 客户信息
     */
    public Customer getCustomer(Long id) {
        String sql = "SELECT c.id,c.name,c.contact,c.email,c.telephone,c.remark FROM customer c WHERE c.id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 创建新客户信息
     *
     * @param fieldMap 客户信息map
     * @return 创建结果
     */
    public Boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     * 更新客户信息
     *
     * @param id       主键
     * @param fieldMap 客户信息map
     * @return 更新结果
     */
    public Boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * 删除客户信息
     *
     * @param id 主键
     * @return 删除结果
     */
    public Boolean deleteCustomer(Long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
