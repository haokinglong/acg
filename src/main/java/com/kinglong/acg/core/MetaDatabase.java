package com.kinglong.acg.core;

import com.kinglong.acg.consts.DatabaseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 元数据库表
 *
 * @author haojinlong
 * @date 2020/3/20 17:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaDatabase implements Serializable {

	private static final long serialVersionUID = -2055033908590325475L;

	private String cmt;
	private String description;
	private DatabaseType type;
	private String name;
	private List<MetaTable> metaTables;
	private String password;
	private String username;
	private String url;
	/**
	 * 实例名：比如 orcl
	 */
	private String instanceName;
}
