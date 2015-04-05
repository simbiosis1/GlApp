package org.simbiosis.bp.gl;

import java.io.Serializable;

import org.simbiosis.gl.model.GlTrans;

public class GlTransMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1801001107030668052L;
	long idSource;
	String queueName;
	GlTrans glTransDto;

	public long getIdSource() {
		return idSource;
	}

	public void setIdSource(long idSource) {
		this.idSource = idSource;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public GlTrans getGlTransDto() {
		return glTransDto;
	}

	public void setGlTransDto(GlTrans glTransDto) {
		this.glTransDto = glTransDto;
	}

}
