package org.jeecgframework.web.rest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.security.DataScopeSecurityHelper;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * TSUser的Restful API的Controller.
 *
 * @author liuht
 */
@Controller
@RequestMapping(value = "/user")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private Validator validator;

	/**
	 * 访问地址：http://localhost:8080/jeecg/rest/user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> list() {
		TSUser currentUser = currentUser();
		if (currentUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (!canReadUsers(currentUser)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		List<TSUser> users = securityHelper().findVisibleUsers(currentUser);
		return new ResponseEntity<List<UserView>>(toUserViews(users), HttpStatus.OK);
	}

	/**
	 * 访问地址：http://localhost:8080/jeecg/rest/user/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TSUser currentUser = currentUser();
		if (currentUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (!canReadUsers(currentUser) || !securityHelper().canAccessUser(currentUser, id)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		TSUser user = userService.get(TSUser.class, id);
		if (user == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserView>(new UserView(user), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TSUser user, UriComponentsBuilder uriBuilder) {
		TSUser currentUser = currentUser();
		if (currentUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (!canWriteUsers(currentUser)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		Set<ConstraintViolation<TSUser>> failures = validator.validate(user);
		if (!failures.isEmpty()) {
			return new ResponseEntity<Object>(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}
		TSUser safeUser = new TSUser();
		copyWritableFields(user, safeUser);
		safeUser.setUserName(user.getUserName());
		if (StringUtil.isNotEmpty(user.getPassword())) {
			safeUser.setPassword(PasswordUtil.encrypt(user.getUserName(), user.getPassword(), PasswordUtil.getStaticSalt()));
		}
		safeUser.setStatus(Globals.User_Normal);
		safeUser.setDeleteFlag(Globals.Delete_Normal);
		userService.save(safeUser);

		URI uri = uriBuilder.path("/rest/user/" + safeUser.getId()).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);
		return new ResponseEntity<Object>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody TSUser user) {
		TSUser currentUser = currentUser();
		if (currentUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (!canWriteUsers(currentUser) || !securityHelper().canAccessUser(currentUser, id)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		TSUser persistedUser = userService.get(TSUser.class, id);
		if (persistedUser == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		copyWritableFields(user, persistedUser);
		userService.saveOrUpdate(persistedUser);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		TSUser currentUser = currentUser();
		if (currentUser == null) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		if (!canWriteUsers(currentUser) || !securityHelper().canAccessUser(currentUser, id)) {
			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		TSUser targetUser = userService.get(TSUser.class, id);
		if (targetUser == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		userService.deleteEntityById(TSUser.class, id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	private TSUser currentUser() {
		return securityHelper().getCurrentUser();
	}

	private boolean canReadUsers(TSUser currentUser) {
		DataScopeSecurityHelper helper = securityHelper();
		return helper.isAdmin(currentUser) || helper.hasFunctionAuth(currentUser, DataScopeSecurityHelper.USER_MANAGE_URL);
	}

	private boolean canWriteUsers(TSUser currentUser) {
		DataScopeSecurityHelper helper = securityHelper();
		return helper.isAdmin(currentUser) || helper.hasFunctionAuth(currentUser, DataScopeSecurityHelper.USER_MANAGE_URL);
	}

	private List<UserView> toUserViews(List<TSUser> users) {
		List<UserView> views = new ArrayList<UserView>();
		for (TSUser user : users) {
			views.add(new UserView(user));
		}
		return views;
	}

	private void copyWritableFields(TSUser source, TSUser target) {
		target.setRealName(source.getRealName());
		target.setMobilePhone(source.getMobilePhone());
		target.setOfficePhone(source.getOfficePhone());
		target.setEmail(source.getEmail());
		target.setActivitiSync(source.getActivitiSync());
		target.setBrowser(source.getBrowser());
	}

	private DataScopeSecurityHelper securityHelper() {
		return new DataScopeSecurityHelper(userService);
	}

	public static class UserView {
		private String id;
		private String userName;
		private String realName;
		private String mobilePhone;
		private String officePhone;
		private String email;
		private Short status;
		private Short activitiSync;
		private String departid;
		private Date createDate;
		private String createBy;
		private String createName;
		private Date updateDate;
		private String updateBy;
		private String updateName;

		public UserView(TSUser user) {
			this.id = user.getId();
			this.userName = user.getUserName();
			this.realName = user.getRealName();
			this.mobilePhone = user.getMobilePhone();
			this.officePhone = user.getOfficePhone();
			this.email = user.getEmail();
			this.status = user.getStatus();
			this.activitiSync = user.getActivitiSync();
			this.departid = user.getDepartid();
			this.createDate = user.getCreateDate();
			this.createBy = user.getCreateBy();
			this.createName = user.getCreateName();
			this.updateDate = user.getUpdateDate();
			this.updateBy = user.getUpdateBy();
			this.updateName = user.getUpdateName();
		}

		public String getId() {
			return id;
		}

		public String getUserName() {
			return userName;
		}

		public String getRealName() {
			return realName;
		}

		public String getMobilePhone() {
			return mobilePhone;
		}

		public String getOfficePhone() {
			return officePhone;
		}

		public String getEmail() {
			return email;
		}

		public Short getStatus() {
			return status;
		}

		public Short getActivitiSync() {
			return activitiSync;
		}

		public String getDepartid() {
			return departid;
		}

		public Date getCreateDate() {
			return createDate;
		}

		public String getCreateBy() {
			return createBy;
		}

		public String getCreateName() {
			return createName;
		}

		public Date getUpdateDate() {
			return updateDate;
		}

		public String getUpdateBy() {
			return updateBy;
		}

		public String getUpdateName() {
			return updateName;
		}
	}
}
