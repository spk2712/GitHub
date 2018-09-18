package com.tcs.ifact.bobj;

import javax.validation.constraints.NotEmpty;

import com.tcs.ifact.constraint.FieldMatch;

@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetBObj {

	@NotEmpty
    private String user;
	
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    private String oldpassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
