package ddd.leave.domain.leave.repository.persistence;

import ddd.leave.domain.leave.repository.facade.LeaveRepositoryInterface;
import ddd.leave.domain.leave.repository.mapper.ApprovalInfoDao;
import ddd.leave.domain.leave.repository.mapper.LeaveDao;
import ddd.leave.domain.leave.repository.mapper.LeaveEventDao;
import ddd.leave.domain.leave.repository.po.ApprovalInfoPO;
import ddd.leave.domain.leave.repository.po.LeaveEventPO;
import ddd.leave.domain.leave.repository.po.LeavePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 持久化 entity 及处理事件 in repository
 * @author apple
 */
@Repository
public class LeaveRepositoryImpl implements LeaveRepositoryInterface {

    @Resource
    LeaveDao leaveDao;

    @Resource
    ApprovalInfoDao approvalInfoDao;

    @Resource
    LeaveEventDao leaveEventDao;

    @Override
    public void save(LeavePO leavePO) {
        // 持久化 leave 实体
        leaveDao.save(leavePO);
        // 保存 leavePO 后，设置 approvalInfoPO 的 leave_id
        leavePO.getHistoryApprovalInfoPOList().forEach(approvalInfoPO -> approvalInfoPO.setLeaveId(leavePO.getId()));
        approvalInfoDao.saveAll(leavePO.getHistoryApprovalInfoPOList());
    }

    @Override
    public void saveEvent(LeaveEventPO leaveEventPO) {
        leaveEventDao.save(leaveEventPO);
    }

    @Override
    public LeavePO findById(String id) {
        return leaveDao.findById(id)
                .orElseThrow(() -> new RuntimeException("leave not found"));
    }

    @Override
    public List<LeavePO> queryByApplicantId(String applicantId) {
        List<LeavePO> leavePOList = leaveDao.queryByApplicantId(applicantId);
        leavePOList.forEach(leavePO -> {
                    List<ApprovalInfoPO> approvalInfoPOList = approvalInfoDao.queryByLeaveId(leavePO.getId());
                    leavePO.setHistoryApprovalInfoPOList(approvalInfoPOList);
                });
        return leavePOList;
    }

    @Override
    public List<LeavePO> queryByApproverId(String approverId) {
        List<LeavePO> leavePOList = leaveDao.queryByApproverId(approverId);
        leavePOList.stream()
                .forEach(leavePO -> {
                    List<ApprovalInfoPO> approvalInfoPOList = approvalInfoDao.queryByLeaveId(leavePO.getId());
                    leavePO.setHistoryApprovalInfoPOList(approvalInfoPOList);
                });
        return leavePOList;
    }

}
