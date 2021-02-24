package ddd.leave.domain.person.entity;

import lombok.Data;

/**
 * 实体
 *
 * 组织关系
 * 创建、修改人员组织关系
 * 查询请假审批对象
 *
 * @author apple
 */
@Data
public class Relationship {

    String id;

    String personId;

    String leaderId;

    int leaderLevel;
}
