package com.yanshen.admin.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Vo
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Data
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
         * 
         */
        private Long id;
        /**
         * 
         */
        private String userName;
        /**
         * 
         */
        private String password;
    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 乐观锁
     */
    private Integer version;
    }
