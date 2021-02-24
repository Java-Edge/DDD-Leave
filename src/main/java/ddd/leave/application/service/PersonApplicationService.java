package ddd.leave.application.service;

import ddd.leave.domain.person.entity.Person;
import ddd.leave.domain.person.service.PersonDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人员应用服务
 * @author apple
 */
@Service
public class PersonApplicationService {

    @Autowired
    PersonDomainService personDomainService;

    /**
     * 创建人员应用服务
     *
     */
    public void create(Person person) {
        personDomainService.create(person);
    }

    /**
     * 修改人员应用服务
     *
     * @param person
     */
    public void update(Person person) {
        personDomainService.update(person);
    }

    /**
     * 删除人员应用服务
     *
     */
    public void deleteById(String personId) {
        personDomainService.deleteById(personId);
    }

    /**
     * 查询人员应用服务
     *
     */
    public Person findById(String personId) {
        return null;
    }

    public Person findFirstApprover(String applicantId, int leaderMaxLevel) {
        return personDomainService.findFirstApprover(applicantId, leaderMaxLevel);
    }
}
