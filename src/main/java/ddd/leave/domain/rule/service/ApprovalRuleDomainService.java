package ddd.leave.domain.rule.service;

import ddd.leave.domain.rule.entity.ApprovalRule;
import ddd.leave.domain.rule.repository.facade.ApprovalRuleRepositoryInterface;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author apple
 */
@Service
public class ApprovalRuleDomainService {

    @Resource
    ApprovalRuleRepositoryInterface repositoryInterface;

    /**
     * 查询规则下的审批leader的最高级别
     *
     * @param personType
     * @param leaveType
     * @param duration
     * @return
     */
    public int getLeaderMaxLevel(String personType, String leaveType, long duration) {
        // 查询审批规则
        ApprovalRule rule = new ApprovalRule();
        rule.setPersonType(personType);
        rule.setLeaveType(leaveType);
        rule.setDuration(duration);
        // 根据审批规则，查询审批人
        return repositoryInterface.getLeaderMaxLevel(rule);
    }
}
