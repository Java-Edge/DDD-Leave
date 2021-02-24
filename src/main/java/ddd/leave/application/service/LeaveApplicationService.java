package ddd.leave.application.service;

import ddd.leave.domain.leave.entity.valueobject.Approver;
import ddd.leave.domain.leave.entity.Leave;
import ddd.leave.domain.leave.service.LeaveDomainService;
import ddd.leave.domain.person.entity.Person;
import ddd.leave.domain.person.repository.po.PersonPO;
import ddd.leave.domain.person.service.PersonDomainService;
import ddd.leave.domain.rule.entity.ApprovalRule;
import ddd.leave.domain.rule.service.ApprovalRuleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审批应用服务
 * 请假单应用服务
 * 依赖封装请假和人员组织关系聚合的领域服务
 *
 * @author apple
 */
@Service
public class LeaveApplicationService {

    @Resource
    LeaveDomainService leaveDomainService;

    @Resource
    PersonDomainService personDomainService;

    @Resource
    ApprovalRuleDomainService approvalRuleDomainService;

    /**
     * 创建一个请假申请并为审批人生成任务
     *
     * @param leave
     */
    public void createLeaveInfo(Leave leave) {
        // 查询审批规则（请假类型、时长）
        int leaderMaxLevel = approvalRuleDomainService.getLeaderMaxLevel(leave.getApplicant().getPersonType(), leave.getType().toString(), leave.getDuration());
        // 根据审批规则查询审批人
        Person approver = personDomainService.findFirstApprover(leave.getApplicant().getPersonId(), leaderMaxLevel);
        // 修改请假流程信息（状态、审批人）
        leaveDomainService.createLeave(leave, leaderMaxLevel, Approver.fromPerson(approver));
    }

    /**
     * 更新请假单基本信息
     *
     * @param leave
     */
    public void updateLeaveInfo(Leave leave) {
        leaveDomainService.updateLeaveInfo(leave);
    }

    /**
     * 提交审批，更新请假单信息
     *
     * @param leave
     */
    public void submitApproval(Leave leave) {
        // 1. 查询审批规则（请假类型和时长）
        ApprovalRule approvalRule = ApprovalRule.getByLeave(leave);
        // 根据审批规则查询审批人
        Person approver = personDomainService.findNextApprover(leave.getApprover().getPersonId(), leave.getLeaderMaxLevel());
        leaveDomainService.submitApproval(leave, Approver.fromPerson(approver));
    }

    public Leave getLeaveInfo(String leaveId) {
        return leaveDomainService.getLeaveInfo(leaveId);
    }

    public List<Leave> queryLeaveInfosByApplicant(String applicantId) {
        return leaveDomainService.queryLeaveInfosByApplicant(applicantId);
    }

    public List<Leave> queryLeaveInfosByApprover(String approverId) {
        return leaveDomainService.queryLeaveInfosByApprover(approverId);
    }
}