package ddd.leave.domain.leave.entity;

import ddd.leave.domain.leave.entity.valueobject.Applicant;
import ddd.leave.domain.leave.entity.valueobject.Approver;
import ddd.leave.domain.leave.entity.valueobject.LeaveType;
import ddd.leave.domain.leave.entity.valueobject.Status;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 请假单聚合根
 *
 * @author apple
 */
@Data
public class Leave {

    String id;

    Applicant applicant;

    Approver approver;

    LeaveType type;

    /**
     * 审批状态
     */
    Status status;

    Date startTime;

    Date endTime;

    long duration;

    /**
     * 审批领导的最大级别
     */
    int leaderMaxLevel;

    /**
     * 当前审批意见
     */
    ApprovalInfo currentApprovalInfo;

    /**
     * 历史审批意见
     */
    List<ApprovalInfo> historyApprovalInfos;

    public long getDuration() {
        return endTime.getTime() - startTime.getTime();
    }

    public Leave addHistoryApprovalInfo(ApprovalInfo approvalInfo) {
        if (null == historyApprovalInfos) {
            historyApprovalInfos = new ArrayList<>();
        }
        this.historyApprovalInfos.add(approvalInfo);
        return this;
    }

    /**
     * 创建请假信息
     *
     * @return
     */
    public Leave create() {
        this.setStatus(Status.APPROVING);
        this.setStartTime(new Date());
        return this;
    }

    public Leave agree(Approver nextApprover) {
        this.setStatus(Status.APPROVING);
        this.setApprover(nextApprover);
        return this;
    }

    public Leave reject(Approver approver) {
        this.setApprover(approver);
        this.setStatus(Status.REJECTED);
        this.setApprover(null);
        return this;
    }

    public Leave finish() {
        this.setApprover(null);
        this.setStatus(Status.APPROVED);
        this.setEndTime(new Date());
        this.setDuration(this.getEndTime().getTime() - this.getStartTime().getTime());
        return this;
    }
}
