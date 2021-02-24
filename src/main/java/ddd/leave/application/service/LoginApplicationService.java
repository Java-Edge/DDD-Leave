package ddd.leave.application.service;

import ddd.leave.domain.person.entity.Person;
import ddd.leave.domain.person.service.PersonDomainService;
import ddd.leave.infrastructure.client.AuthFeignClient;
import ddd.leave.infrastructure.common.api.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 应用服务
 *
 * 认证并获取人员信息
 * @author apple
 */
@Service
public class LoginApplicationService {


    @Resource
    AuthFeignClient authService;

    @Resource
    PersonDomainService personDomainService;

    public Response login(Person person) {
        return authService.login(person);
    }
}