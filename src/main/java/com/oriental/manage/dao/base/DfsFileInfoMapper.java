package com.oriental.manage.dao.base;

import com.oriental.manage.pojo.base.DfsFileInfo;

import java.util.List;

public interface DfsFileInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    int insert(DfsFileInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    int insertSelective(DfsFileInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    DfsFileInfo selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DfsFileInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dfs_file_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DfsFileInfo record);

    List<DfsFileInfo> searchDfsFileInfo(DfsFileInfo dfsFileInfo);
}