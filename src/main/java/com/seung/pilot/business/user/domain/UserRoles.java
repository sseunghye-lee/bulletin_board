package com.seung.pilot.business.user.domain;

import com.seung.pilot.commons.BaseEntity;
import com.seung.pilot.commons.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = {@Index(columnList = "userId, userRole", unique = true)})
public class UserRoles extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(columnDefinition = "varchar(36) COMMENT 'UserRole Id'")
    private String userRoleId;

    @Column(columnDefinition = "bigint COMMENT '회원 ID'", insertable = false, updatable = false)
    private Long userId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;

    @Column(columnDefinition = "varchar(20) COMMENT '권한'")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public static UserRoles makeUserRole(UserRole role){
        return UserRoles.builder().userRole(role).build();
    }
}
