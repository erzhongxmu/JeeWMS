package org.jeecgframework.web.system.sms.util.msg.domain;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * . ISMG以CMPP_CONNECT_RESP消息响应SP的链接请求。<br/>
 * status响应状态状态 0：正确 1：消息结构错 2：非法源地址 3：认证错 4：版本太高 5~ ：其他错误<br/>
 * authenticatorISMG SMG认证码，用于鉴别ISMG。 其值通过单向MD5 hash计算得出，表示如下： Authent
 * icatorISMG =MD5（Status+AuthenticatorSource+shared secret），Shared secret
 * 由中国移动与源地址实体事先商定， AuthenticatorSource为源地址实体发送给ISMG的对应消息CMPP_Connect中的值。
 * 认证出错时，此项为空。<br/>
 * version服务器支持的最高版本号，对于3.0的版本，高4bit为3，低4位为0<br/>
 */
public class MsgConnectResp extends MsgHead {
	private static Logger logger = Logger.getLogger(MsgConnectResp.class);
	private int status;// 响应状态状态 0：正确 1：消息结构错 2：非法源地址 3：认证错 4：版本太高 5~ ：其他错误
	private String statusStr;// 响应状态状态 0：正确 1：消息结构错 2：非法源地址 3：认证错 4：版本太高 5~
								// ：其他错误
	private byte[] authenticatorISMG;// ISMG认证码，用于鉴别ISMG。 其值通过单向MD5
										// hash计算得出，表示如下： AuthenticatorISMG
										// =MD5（Status+AuthenticatorSource+shared
										// secret），Shared secret
										// 由中国移动与源地址实体事先商定，AuthenticatorSource为源地址实体发送给ISMG的对应消息CMPP_Connect中的值。
										// 认证出错时，此项为空。
	private byte version;// 服务器支持的最高版本号，对于3.0的版本，高4bit为3，低4位为0

	/**
	 * .
	 * 
	 * @param data
	 *            byte[]
	 */
	public MsgConnectResp(byte[] data) {
		if (data.length == 8 + 4 + 16 + 1) {
			ByteArrayInputStream bins = new ByteArrayInputStream(data);
			DataInputStream dins = new DataInputStream(bins);
			try {
				this.setTotalLength(data.length + 4);
				this.setCommandId(dins.readInt());
				this.setSequenceId(dins.readInt());
				this.setStatus(dins.readInt());
				byte[] aiByte = new byte[16];
				dins.read(aiByte);
				this.authenticatorISMG = aiByte;
				this.version = dins.readByte();
				dins.close();
				bins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("链接至IMSP,解析数据包出错，包长度不一致。长度为:" + data.length);
		}
	}

	/**
	 * .
	 * 
	 * @return int
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * .
	 * 
	 * @param status
	 *            int
	 */
	public void setStatus(int status) {
		this.status = status;
		switch (status) {
		case 0:
			statusStr = "正确";
			break;
		case 1:
			statusStr = "消息结构错";
			break;
		case 2:
			statusStr = "非法源地址";
			break;
		case 3:
			statusStr = "认证错";
			break;
		case 4:
			statusStr = "版本太高";
			break;
		case 5:
			statusStr = "其他错误";
			break;
		default:
			statusStr = status + ":未知";
			break;
		}
	}

	/**
	 * .
	 * 
	 * @return byte[]
	 */
	public byte[] getAuthenticatorISMG() {
		return authenticatorISMG;
	}

	/**
	 * .
	 * 
	 * @param authenticatorISMG
	 *            byte[]
	 */
	public void setAuthenticatorISMG(byte[] authenticatorISMG) {
		this.authenticatorISMG = authenticatorISMG;
	}

	/**
	 * .
	 * 
	 * @return byte
	 */
	public byte getVersion() {
		return version;
	}

	/**
	 * .
	 * 
	 * @param version
	 *            byte
	 */
	public void setVersion(byte version) {
		this.version = version;
	}

	/**
	 * .
	 * 
	 * @return String
	 */
	public String getStatusStr() {
		return statusStr;
	}
}
