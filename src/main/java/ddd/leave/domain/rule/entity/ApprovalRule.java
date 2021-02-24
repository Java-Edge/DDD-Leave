package ddd.leave.domain.rule.entity;

import ddd.leave.domain.leave.entity.Leave;
import lombok.Data;

/**
 * 值对象
 *
 * 审批规则
 *
 * @author apple
 */
@Data
public class ApprovalRule {

    String personType;

    /**
     * 请假类型
     */
    String leaveType;

    /**
     * 时长
     */
    long duration;

    int maxLeaderLevel;

    /**
     * 查询审批规则
     */
    public static ApprovalRule getByLeave(Leave leave){
        ApprovalRule rule = new ApprovalRule();
        rule.setPersonType(leave.getApplicant().getPersonType());
        rule.setLeaveType(leave.getType().toString());
        rule.setDuration(leave.getDuration());
        return rule;
    }
}
