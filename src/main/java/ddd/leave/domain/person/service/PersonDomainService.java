package ddd.leave.domain.person.service;

import ddd.leave.domain.person.entity.Person;
import ddd.leave.domain.person.entity.valueobject.PersonStatus;
import ddd.leave.domain.person.repository.facade.PersonRepository;
import ddd.leave.domain.person.repository.po.PersonPO;
import ddd.leave.domain.rule.service.ApprovalRuleDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 人员组织关系 领域服务
 *
 * 依赖：封装人员和组织关系实体方法
 *
 * @author apple
 */
@Service
@Slf4j
public class PersonDomainService {

    @Resource
    PersonRepository personRepository;

    @Resource
    PersonFactory personFactory;

    /**
     * 创建人员（+组织关系？）
     *
     * @param person
     */
    public void create(Person person) {
        PersonPO personPO = personRepository.findById(person.getPersonId());
        if (null == personPO) {
            throw new RuntimeException("Person already exists");
        }
        person.create();
        personRepository.insert(personFactory.createPersonPO(person));
    }

    /**
     * 修改人员（+组织关系？）
     *
     */
    public void update(Person person) {
        person.setLastModifyTime(new Date());
        personRepository.update(personFactory.createPersonPO(person));
    }

    public void deleteById(String personId) {
        PersonPO personPO = personRepository.findById(personId);
        Person person = personFactory.getPerson(personPO);
        person.disable();
        personRepository.update(personFactory.createPersonPO(person));
    }

    /**
     * 查询人员
     *
     */
    public Person findById(String userId) {
        PersonPO personPO = personRepository.findById(userId);
        return personFactory.getPerson(personPO);
    }

    /**
     * 根据审批规则查询审批人
     * find leader with applicant, if leader level bigger then leaderMaxLevel return null, else return Approver from leader;
     *
     * @param applicantId
     * @param leaderMaxLevel
     * @return
     */
    public Person findFirstApprover(String applicantId, int leaderMaxLevel) {
        PersonPO leaderPO = personRepository.findLeaderByPersonId(applicantId);
        if (leaderPO.getRoleLevel() > leaderMaxLevel) {
            return null;
        } else {
            return personFactory.createPerson(leaderPO);
        }
    }

    /**
     * find leader with current approver, if leader level bigger then leaderMaxLevel return null, else return Approver from leader;
     *
     * @param currentApproverId
     * @param leaderMaxLevel
     * @return
     */
    public Person findNextApprover(String currentApproverId, int leaderMaxLevel) {
        PersonPO leaderPO = personRepository.findLeaderByPersonId(currentApproverId);
        if (leaderPO.getRoleLevel() > leaderMaxLevel) {
            return null;
        } else {
            return personFactory.createPerson(leaderPO);
        }
    }

}