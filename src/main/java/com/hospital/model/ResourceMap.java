package com.hospital.model;

import java.io.Serializable;

/**
 * 资源映射表
 * @author yubing
 *
 */
public class ResourceMap  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6080793896864955163L;

	private String resourceId;

    private String imsAccount;

    private String imsPassword;

    private String authName;

    private String mediaxAccount;

    private String mediaxPassword;

    public String getImsAccount() {
        return imsAccount;
    }

    public void setImsAccount(String imsAccount) {
        this.imsAccount = imsAccount == null ? null : imsAccount.trim();
    }

    public String getImsPassword() {
        return imsPassword;
    }

    public void setImsPassword(String imsPassword) {
        this.imsPassword = imsPassword == null ? null : imsPassword.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getMediaxAccount() {
        return mediaxAccount;
    }

    public void setMediaxAccount(String mediaxAccount) {
        this.mediaxAccount = mediaxAccount == null ? null : mediaxAccount.trim();
    }

    public String getMediaxPassword() {
        return mediaxPassword;
    }

    public void setMediaxPassword(String mediaxPassword) {
        this.mediaxPassword = mediaxPassword == null ? null : mediaxPassword.trim();
    }

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}