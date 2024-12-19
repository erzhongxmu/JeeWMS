package com.base.modules.biconf.biapi.dto;

import com.base.modules.biconf.entity.BiVisual;
import com.base.modules.biconf.entity.BiVisualConfig;
import lombok.Data;

import java.io.Serializable;

/**
 * 大屏展示DTO
 *
 * @author
 */
@Data
public class VisualDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 可视化主信息
	 */
	private BiVisual visual;

	/**
	 * 可视化配置信息
	 */
	private BiVisualConfig config;

}
