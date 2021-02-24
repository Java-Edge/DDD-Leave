package ddd.leave.domain.person.entity;

import ddd.leave.domain.person.entity.valueobject.PersonStatus;
import ddd.leave.domain.leave.entity.valueobject.PersonType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 聚合根
 *
 * 上级审批领导
 * @author apple
 */
@Data
public class Person {

    String personId;

    String personName;

    PersonType personType;

    List<Relationship> relationships;

    int roleLevel;

    Date createTime;

    Date lastModifyTime;

    PersonStatus status;

    /**
     * 创建人员
     * todo 查询人员、修改人员、根据审批规则查询审批人
     * @return
     */
    public Person create(){
        this.createTime = new Date();
        this.status = PersonStatus.ENABLE;
        return this;
    }

    public Person enable(){
        this.lastModifyTime = new Date();
        this.status = PersonStatus.ENABLE;
        return this;
    }

    public Person disable(){
        this.lastModifyTime = new Date();
        this.status = PersonStatus.DISABLE;
        return this;
    }
}