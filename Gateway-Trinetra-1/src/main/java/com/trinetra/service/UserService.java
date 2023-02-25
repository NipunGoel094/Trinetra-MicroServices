package com.trinetra.service;

import static com.trinetra.model.enums.Status.FAILED;
import static com.trinetra.model.enums.Status.SUCCESS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trinetra.GatewayTrinteraApplication;
import com.trinetra.auth.model.SignupRequest;
import com.trinetra.auth.model.UpdateRoleResponse;
import com.trinetra.auth.model.UpdateUserRequest;
import com.trinetra.exception.CustomException;
import com.trinetra.exception.InternalServerError;
import com.trinetra.mailer.Mail;
import com.trinetra.mailer.MailService;
import com.trinetra.model.entity.Address;
import com.trinetra.model.entity.User;
import com.trinetra.model.entity.mapping.UserRole;
import com.trinetra.model.entity.master.Role;
import com.trinetra.model.page.response.UserDetailsVO;
import com.trinetra.model.page.response.UserRolesVO;
import com.trinetra.model.request.PatchRoleRquest;
import com.trinetra.model.request.ResetPasswordRequest;
import com.trinetra.model.response.Children;
import com.trinetra.model.response.MailResponse;
import com.trinetra.model.response.MenuBarResponse;
import com.trinetra.model.response.Message;
import com.trinetra.model.response.TabChildren;
import com.trinetra.model.response.TabChildren2;
import com.trinetra.model.useraccess.dto.MenuMasterDto;
import com.trinetra.model.useraccess.dto.UserAccessDTO;
import com.trinetra.model.useraccess.entity.ChildMenuMaster;
import com.trinetra.model.useraccess.entity.MenuAccessMaster;
import com.trinetra.model.useraccess.entity.MenuMaster;
import com.trinetra.model.useraccess.entity.MenuTabActionMapping;
import com.trinetra.model.useraccess.entity.TabMaster;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.model.wrapper.UserWrapper;
import com.trinetra.repo.AddressRepo;
import com.trinetra.repo.MenuAccessMasterRepo;
import com.trinetra.repo.MenuMasterRepo;
import com.trinetra.repo.RoleRepo;
import com.trinetra.repo.UserRepository;
import com.trinetra.repo.UserRoleRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService {
	@Value("${user_initial_role}")
	private String INITIAL_ROLE;
	@Value("${spring.mail.username}")
	private String mailFrom;
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	private final UserRepository userRepo;
	private final PasswordEncoder encoder;
	private final RoleRepo roleRepo;
	private final Validator validator;
	private final MailService mailService;
	private final UserRoleRepo userRoleRepo;
	private final AddressRepo addressRepo;
	private final Logger logger = LoggerFactory.getLogger(GatewayTrinteraApplication.class);
	@Autowired
	@Lazy
	private MenuMasterRepo menuMasterRepo;
	
	@Autowired
	@Lazy
	private MenuAccessMasterRepo menuAccessMasterRepo;
	
	@Autowired
	@Lazy
	private ObjectMapper objectMapper;

	@Autowired
	public UserService(UserRepository userRepo, PasswordEncoder encoder, RoleRepo roleRepo, Validator validator,
			MailService mailService, UserRoleRepo userRoleRepo, AddressRepo addressRepo) {
		this.userRepo = userRepo;
		this.encoder = encoder;
		this.roleRepo = roleRepo;
		this.validator = validator;
		this.mailService = mailService;
		this.userRoleRepo = userRoleRepo;
		this.addressRepo = addressRepo;
	}

	public User getUser(String username) {
		User user = findUser(username);
		return user;
	}

	public User getUser(long id) {
		User user = findUser(id);
		return user;
	}

	public User saveUser(User user) {
		return userRepo.save(user);
	}
	
	public void deleteUser(String username) {
		Boolean isExist = userRepo.existsByUsername(username);
		if (isExist) {
			userRepo.deleteByUsername(username);
			log.info("[deleteUser] User: {} exists.", username);
		}

		else {
			log.info("[deleteUser] User does not exist.");
			throw new CustomException("No such user found");
		}
	}

	public String deleteUser(long id) {
		String msg="";
		try {
		User user = findUser(id);
		if(user!=null) {
			user.setActive(false);
			userRepo.save(user);
			msg="Deactivate user Successfully!!";
			return msg;

		} else
			throw new CustomException("No such user found");
		}catch (Exception e) {
			throw new CustomException("No such user found");
		}
	}

	public User updateUser(UpdateUserRequest request, String username) {
		User user = findUser(username);

		Optional<User> byUsername = userRepo.findByUsername(request.getUsername());
		Optional<User> byEmail = userRepo.findByEmail(request.getEmail());

		if (byUsername.isPresent()) {
			if (user.getId() != byUsername.get().getId()) {
				log.info("[updateUser] Username {} is already taken!", username);
				throw new CustomException("Error: Username is already taken!");
			}
		}
		if (byEmail.isPresent()) {
			if (user.getId() != byEmail.get().getId()) {
				log.info("[updateUser] Email {} is already taken!", byEmail.get().getId());
				throw new CustomException("Error: Email is already taken!");
			}
		}
		Address address = user.getAddress();
		user.setUsername(request.getUsername());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setMobileNumber(request.getMobileNumber());

		address.setAddressType(request.getAddress().getAddressType());
		address.setAddress1(request.getAddress().getAddress1());
		address.setAddress2(request.getAddress().getAddress2());
		address.setPincode(request.getAddress().getPincode());

		User updatedUser = userRepo.save(user);
		Address updatedAddress = addressRepo.save(address);

		updatedUser.setAddress(updatedAddress);
		return updatedUser;

	}

	public User updateUser(UpdateUserRequest request, long id) {
		User user = findUser(id);
		Optional<User> byUsername = userRepo.findByUsername(request.getUsername());
		Optional<User> byEmail = userRepo.findByEmail(request.getEmail());

		if (byUsername.isPresent()) {
			if (user.getId() != byUsername.get().getId()) {
				throw new CustomException("Error: Username is already taken!");
			}
		}
		if (byEmail.isPresent()) {
			if (user.getId() != byEmail.get().getId()) {
				throw new CustomException("Error: Email is already taken!");
			}
		}

		Address address = user.getAddress();
		user.setUsername(request.getUsername());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setMobileNumber(request.getMobileNumber());

		address.setAddressType(request.getAddress().getAddressType());
		address.setAddress1(request.getAddress().getAddress1());
		address.setAddress2(request.getAddress().getAddress2());
		address.setPincode(request.getAddress().getPincode());

		User updatedUser = userRepo.save(user);
		Address updatedAddress = addressRepo.save(address);

		updatedUser.setAddress(updatedAddress);
		return updatedUser;
	}

	public Object updateRole(String username, PatchRoleRquest request) {
		User user = findUser(username);
		return assignRoles(request, user);
	}

	private UpdateRoleResponse assignRoles(PatchRoleRquest request, User user) {
		List<String> alreadyAssigned = new ArrayList<>();
		List<String> roleNotFound = new ArrayList<>();
		List<String> roles = request.getRoles();
		Set<Role> roleSet = user.getRoles();
		List<String> roleValue = new ArrayList<>();
		List<String> assigned = new ArrayList<>();
		for (Role roleObj : roleSet) {
			String name = roleObj.getName();
			roleValue.add(name);
		}

		for (String roleName : roles) {
			roleName = roleName.toUpperCase();
			if (roleValue.contains(roleName)) {
				alreadyAssigned.add(roleName);
				continue;
			}
			if (!roleRepo.existsByName(roleName)) {
				roleNotFound.add(roleName);
			} else {
				Role _role = roleRepo.findByName(roleName);
				userRoleRepo.updateRole(user.getId(), _role.getId());
				assigned.add(roleName);
			}
		}

		UpdateRoleResponse obj = new UpdateRoleResponse();
		obj.setRoleNotFound(roleNotFound);
		obj.setAlreadyAssigned(alreadyAssigned);
		obj.setRolesAssigned(assigned);

		return obj;
	}

	public Object updateRole(long id, PatchRoleRquest request) {
		User user = findUser(id);
		return assignRoles(request, user);
	}

	private User findUser(String username) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new CustomException("User '" + username + "' not found !!"));
		return user;
	}

	private User findUser(long id) {
		User user = userRepo.findById(id)
				.orElseThrow(() -> new CustomException("User with id'" + id + "' not found !!"));
		return user;
	}

	public Page<User> getAllUser(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("created_date").descending());
		Page<User> allUser = userRepo.getAllUser(pageable);
		return allUser;
	}

	public UserWrapper createMultipleUser(List<SignupRequest> userList) {
		List<User> savedUserList = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		Map<String, List<String>> errorMap = new HashMap<>();
		List<String> fieldError = null;

		int i = 0;
		for (SignupRequest req : userList) {
			Set<ConstraintViolation<SignupRequest>> validate = validator.validate(req);
			fieldError = new ArrayList<>();
			for (ConstraintViolation<SignupRequest> c : validate) {
				fieldError.add(c.getMessage());

			}
			if (fieldError.size() != 0) {
				errorMap.put("User[" + i + "]", fieldError);
				i++;
				continue;
			}

			if (userRepo.existsByUsername(req.getUsername())) {
				errors.add("User '" + req.getUsername() + "' not created, username already taken");
				i++;
				continue;
			}

			if (userRepo.existsByEmail(req.getEmail())) {
				errors.add("User '" + req.getUsername() + "' not created, email already taken");
				i++;
				continue;
			}

			User user = new User(req.getUsername(), req.getFirstname(), req.getLastname(),
					encoder.encode(req.getPassword()), req.getEmail(), req.getMobileNumber(),
					new Address(req.getAddress().getAddressType(), req.getAddress().getAddress1(),
							req.getAddress().getAddress2(), req.getAddress().getPincodeId()));

            User savedUser = null;
            //user.setCreatedAt((LocalDateTime.now()).toString());

			savedUser = userRepo.save(user);
			Role roleUser = null;
			if (roleRepo.existsByName(INITIAL_ROLE)) {
				roleUser = roleRepo.findByName(INITIAL_ROLE);
			} else {
				roleUser = roleRepo.save(new Role(INITIAL_ROLE));
			}
			Long roleId = roleUser.getId();
			Long userId = savedUser.getId();
			userRoleRepo.save(new UserRole(userId, roleId));
			savedUserList.add(savedUser);
			i++;
		}
		int created = savedUserList.size();
		int failed = userList.size() - created;
		Map<String, Integer> data = new HashMap<>();
		data.put("created", created);
		data.put("failed", failed);
		UserWrapper userWrapper = new UserWrapper(savedUserList, errors, errorMap, data);
		return userWrapper;

	}

	public MailResponse forgotPassword(String email, int keyword) {

		Optional<User> userOptional = userRepo.findByEmail(email);

		if (!userOptional.isPresent()) {
			throw new CustomException("Invalid email");
		}
		String token = null;

		String mailBody = null;
		if (keyword == 1) {
			token = generateToken();
			mailBody = "Your password has been reset. Please visit the following URL to set a new one:\n\n "
					+ "http://localhost:8080/user/reset-password?token=" + token
					+ "\n\nThis link will expired in 30 minutes." + "\n\nIf not requested by you, ignore the mail";
		} else if (keyword == 0) {

			char[] otp = OTP(6);
			token = toString(otp);

			mailBody = "Your password has been reset. please use the below OTP to change the password.\n\n " + token
					+ "\n\nIf not requested by you, ignore the mail";
		}

		User user = userOptional.get();
		user.setResetPasswordToken(token);
		user.setTokenCreationDate(LocalDateTime.now());
		user = userRepo.save(user);
		Mail mail = new Mail();
		mail.setMailFrom(mailFrom);
		mail.setMailTo(email);
		mail.setMailContent(mailBody);
		mail.setMailSubject("Password Reset");
		mailService.sendEmail(mail);
		MailResponse mailResponse = new MailResponse();
		mailResponse.setFrom(mailFrom);
		mailResponse.setTo(email);
		mailResponse.setToken(token);
		mailResponse.setMessage(mailBody);
		mailResponse.setStatus("Mail was sent successfully to: " + email);
		return mailResponse;
	}

	public Message resetPassword(ResetPasswordRequest resetPasswordRequest) {
		String token = resetPasswordRequest.getToken();
		String password = resetPasswordRequest.getPassword();
		Optional<User> userOptional = Optional.ofNullable(userRepo.findByResetPasswordToken(token));

		if (!userOptional.isPresent()) {
			throw new CustomException("Invalid token");
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			throw new CustomException("Token expired");
		}
		User user = userOptional.get();

		user.setPassword(encoder.encode(password));
		user.setResetPasswordToken(null);
		user.setTokenCreationDate(null);
		userRepo.save(user);
		return new Message("Your password updated Successfully");
	}

	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	public char[] OTP(int len) {
		String numbers = "0123456789";
		Random random = new Random();
		char[] otp = new char[len];
		for (int i = 0; i < len; i++) {

			otp[i] = numbers.charAt(random.nextInt(numbers.length()));
		}
		return otp;

	}

	public String toString(char[] a) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < a.length; i++) {
			sb.append(a[i]);
		}

		return sb.toString();
	}
	public Page<UserDetailsVO> getAllUsersById(int page, int size) {
		Page<UserDetailsVO> userList = null;
		String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRolesVO userRolesVO = userRepo
				.findUserByUsername(null != loggedInUserName ? loggedInUserName.toLowerCase() : "");
		Pageable pageable = PageRequest.of(page, size, Sort.by("created_date").descending());
		if (null != userRolesVO) {
			userList = userRepo.findAllByOtherLoggedInUser(pageable, userRolesVO.getUserId());
		} else {
			userList = userRepo.findAllBySuperUser(pageable);
		}
		return userList;
	}
	
	public User updateUserByUserId(UpdateUserRequest request, long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		User user = null;
		if (userOpt.isPresent()) {
			user = userOpt.get();
		}

		user.setUsername(request.getUsername());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setMobileNumber(request.getMobileNumber());
		User updatedUser = userRepo.save(user);

		List<String> roleList = request.getRoles();
		if (null != roleList && !roleList.isEmpty()) {
			List<Role> existingRoleList = roleRepo.findByRoleNames(roleList);
			userRoleRepo.deleteByUserId(updatedUser.getId());
			existingRoleList.forEach(role -> {
				userRoleRepo.updateRole(updatedUser.getId(), role.getId());
			});

		}
		return updatedUser;

	}
	
	public Object updateUserV2ByUserId(UpdateUserRequest request, long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		User user = null;
		if (userOpt.isPresent()) {
			user = userOpt.get();
		}
		else {
			return new Message("User Id Not Found.");
		}
		user.setUsername(request.getUsername());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setMobileNumber(request.getMobileNumber());
		User updatedUser = userRepo.save(user);

		List<String> roleList = request.getRoles();
		if (null != roleList && !roleList.isEmpty()) {
			List<Role> existingRoleList = roleRepo.findByRoleNames(roleList);
			userRoleRepo.deleteByUserId(updatedUser.getId());
			existingRoleList.forEach(role -> {
				userRoleRepo.updateRole(updatedUser.getId(), role.getId());
			});

		}
		return updatedUser;

	}
	
	public Page<UserDetailsVO> getUsersByFilter(int page, int size, String input) {
		Page<UserDetailsVO> userList = null;
		input = null != input ? input.toLowerCase() : "";
		String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		UserRolesVO userRolesVO = userRepo
				.findUserByUsername(null != loggedInUserName ? loggedInUserName.toLowerCase() : "");
		Pageable pageable = PageRequest.of(page, size, Sort.by("created_date").descending());
		if (null != userRolesVO) {
			userList = userRepo.findByFilterWithOtherLoggedInUser(pageable, input, userRolesVO.getUserId());
		} else {
			userList = userRepo.findByFilterWithSuperUser(pageable, input);
		}
		return userList;
	}

	public User createUser(SignupRequest reqObj) {

		try {

			User user = new User(reqObj.getUsername(), reqObj.getFirstname(), reqObj.getLastname(),
					encoder.encode(reqObj.getPassword()), reqObj.getEmail(), reqObj.getMobileNumber(), null);

			User savedUser = null;
			if(!reqObj.getRoles().isEmpty() && reqObj.getRoles().size()>0) {
				String userType =reqObj.getRoles().get(0);
				if(userType.equalsIgnoreCase("USER"))
				{
					user.setUserTypeId(0);
				}	
				else if(userType.equalsIgnoreCase("Super Admin"))
				{
					user.setUserTypeId(1);
				}
				else if(userType.equalsIgnoreCase("VLTD Manufacturer"))
				{
					user.setUserTypeId(2);
				}
				else if(userType.equalsIgnoreCase("Dealer"))
				{
					user.setUserTypeId(3);	
				}
//				else if(userType.equalsIgnoreCase("Permit Holder"))
//				{
//					user.setUserTypeId(4);
//				}
				else if(userType.equalsIgnoreCase("Permit Holder"))
				{
					user.setUserTypeId(326);
				}
				else if(userType.equalsIgnoreCase("RTO"))
				{
					user.setUserTypeId(5);
				}
				else if(userType.equalsIgnoreCase("RTO"))
				{
					user.setUserTypeId(5);
				}
				else if(userType.equalsIgnoreCase("Transport Official"))
				{
					user.setUserTypeId(10);
				}
				else if(userType.equalsIgnoreCase("Citizen"))
				{
					user.setUserTypeId(21);
				}
				else if(userType.equalsIgnoreCase("State Police"))
				{
					user.setUserTypeId(17);
				}
				else if(userType.equalsIgnoreCase("Vehicle Manufacturer"))
				{
					user.setUserTypeId(7);
				}	
			}
			savedUser = userRepo.save(user);

			List<String> roleList = reqObj.getRoles();
			if (null != roleList && !roleList.isEmpty()) {
				List<Role> existingRoleList = roleRepo.findByRoleNames(roleList);
				for (Role role : existingRoleList) {
					userRoleRepo.updateRole(savedUser.getId(), role.getId());
				}
			}
			return savedUser;
		} catch (Exception e) {
			log.error("Exception occurred while saving user: {}", e.getMessage());
			e.printStackTrace();
			throw new CustomException("User creation failed!");
		}
	}

	public List<MenuMasterDto> getUserAccessTree(int userTypeId) {
		try {
			List<MenuMasterDto> resultList = null;
			List<MenuMaster> menuList = menuMasterRepo.findByUserTypeId(userTypeId);			
		resultList = menuList.stream().map(obj->{
				return objectMapper.convertValue(obj, MenuMasterDto.class);
			}).collect(Collectors.toList());			
			return resultList;			
		} catch (Exception e) {
			log.error("Exception occurred while saving user: {}", e.getMessage());
			e.printStackTrace();
			throw new InternalServerError("Exception occurred while fetching user access data.");
		}
	}
	
	public String updateUserAccess(List<MenuMasterDto> requestPayload) {
		try {
			List<MenuMaster> menuMasterList = requestPayload.parallelStream().map(menu -> {
				MenuMaster menuMaster = objectMapper.convertValue(menu, MenuMaster.class);
				menuMaster.setChildMenuMaster(menu.getChildMenuMaster().parallelStream().map(child -> {
					ChildMenuMaster childMenuMaster = objectMapper.convertValue(child, ChildMenuMaster.class);
					childMenuMaster.setTabMaster(child.getTabMaster().parallelStream().map(tab -> {
						TabMaster tabMaster = objectMapper.convertValue(tab, TabMaster.class);
						tabMaster.setMenuTabActionMapping(tab.getMenuTabActionMapping().parallelStream().map(action -> {
							return objectMapper.convertValue(action, MenuTabActionMapping.class);
						}).collect(Collectors.toList()));
						return tabMaster;
					}).collect(Collectors.toList()));
					return childMenuMaster;
				}).collect(Collectors.toList()));
				return menuMaster;
			}).collect(Collectors.toList());
			ArrayNode arrayNode = prepareMenuJson(menuMasterList);
			int userTypeId = requestPayload.get(0).getUserTypeId();
			MenuAccessMaster menuAccessMaster = menuAccessMasterRepo.findByUserTypeId(userTypeId);
			if (null != menuAccessMaster) {
				menuAccessMaster.setJsonValue(objectMapper.writeValueAsString(arrayNode));
				menuAccessMaster.setUserTypeId(userTypeId);
				menuAccessMasterRepo.save(menuAccessMaster);
			} else {
				menuAccessMaster = new MenuAccessMaster();
				menuAccessMaster.setJsonValue(objectMapper.writeValueAsString(arrayNode));
				menuAccessMaster.setUserTypeId(userTypeId);
				menuAccessMasterRepo.save(menuAccessMaster);
			}
			menuMasterRepo.saveAll(menuMasterList);
			return "User access details updated successfully.";
		} catch (Exception e) {
			log.error("Exception occurred while updating user access controls: {}", e.getMessage());
			e.printStackTrace();
			throw new InternalServerError("Excception occurred while updating user access controls.");
		}
	}

	@SuppressWarnings("deprecation")
	public ArrayNode prepareMenuJson(List<MenuMaster> menuMasterList) throws CustomException {
		ArrayNode menuArr = objectMapper.createArrayNode();
		try {
			menuMasterList = menuMasterList.stream().map(menu->{
				List<ChildMenuMaster> childList = menu.getChildMenuMaster().stream().map(child->{
					List<TabMaster> tabList = child.getTabMaster().stream().map(tab->{
						List<MenuTabActionMapping> actionList = tab.getMenuTabActionMapping().stream().map(action->{
							return action;
						}).sorted(Comparator.comparing(MenuTabActionMapping::getSequenceNo)).collect(Collectors.toList());
						tab.setMenuTabActionMapping(actionList);
						return tab;
					}).sorted(Comparator.comparing(TabMaster::getSequenceNo)).collect(Collectors.toList());
					child.setTabMaster(tabList);
					return child;
				}).sorted(Comparator.comparing(ChildMenuMaster::getSequenceNo)).collect(Collectors.toList());
				menu.setChildMenuMaster(childList);
				return menu;
			}).sorted(Comparator.comparing(MenuMaster::getSequenceNo)).collect(Collectors.toList());
			
			menuMasterList.forEach(menu -> {
				if (menu.isHasAccess()) {
					ObjectNode menuMap = objectMapper.createObjectNode();
					menuMap.put("menuName", menu.getItem());
					menuMap.put("route", !menu.getRoute().isEmpty() ? menu.getRoute() : null);
					ArrayNode childInnerArr = objectMapper.createArrayNode();
					menu.getChildMenuMaster().forEach(child -> {
						if (child.isHasAccess()) {
							ObjectNode childMap = objectMapper.createObjectNode();
							childMap.put("name", child.getItem());
							childMap.put("route", !child.getRoute().isEmpty() ? child.getRoute() : null);
							childInnerArr.add(childMap);
							child.getTabMaster().forEach(tab -> {
								if (child.isHasAccess()) {
									ObjectNode tabMap = objectMapper.createObjectNode();
									tabMap.put("name", tab.getItem());
									tabMap.put("route", !tab.getRoute().isEmpty() ? tab.getRoute() : null);
									ArrayNode actionInnerArr = objectMapper.createArrayNode();
									tab.getMenuTabActionMapping().forEach(action -> {
										if (action.isHasAccess()) {
											ObjectNode actionMap = objectMapper.createObjectNode();
											actionMap.put("name", action.getItem());
											actionMap.put("route",
													!action.getRoute().isEmpty() ? action.getRoute() : null);
											actionInnerArr.add(actionMap);
										}
									});
									if (!actionInnerArr.isEmpty()) {
										tabMap.put("inner", actionInnerArr);
									}
									childInnerArr.add(tabMap);
								}
							});
						}
					});
					if (!childInnerArr.isEmpty()) {
						menuMap.put("inner", childInnerArr);
					}
					menuArr.add(menuMap);
				}
			});
		} catch (Exception e) {
			log.error("Excception occurred while preparing menu json: {}", e.getMessage());
			e.printStackTrace();
			throw new CustomException("Excception occurred while preparing menu json.");
		}
		return menuArr;
	}
	
	
	
	
	
	
	public List<MenuBarResponse> getUserAccessList(int userTypeId) {
		try {
		 	List<MenuMaster> menuList = menuMasterRepo.findByUserTypeId(userTypeId);
			List<MenuBarResponse> menuBarResponses= new ArrayList<MenuBarResponse>();
			for(MenuMaster menuMaster:menuList)
			{
				MenuBarResponse menuBarResponse= new MenuBarResponse();
				menuBarResponse.setName(menuMaster.getItem());
				menuBarResponse.setChecked(menuMaster.isHasAccess());
				
				List<Children> chList = new ArrayList<Children>();
				 for(ChildMenuMaster childMenuMaster: menuMaster.getChildMenuMaster())
				 {
					Children children= new Children();
					children.setName(childMenuMaster.getItem());
					children.setChecked(childMenuMaster.isHasAccess());	
					List<TabChildren> tabChildrens= new ArrayList<TabChildren>();					
					for(TabMaster tabMaster: childMenuMaster.getTabMaster())
					{
						TabChildren tabChildren = new TabChildren();
						tabChildren.setName(tabMaster.getItem());
						tabChildren.setChecked(tabMaster.isHasAccess());
						
						List<TabChildren2> lChildren2s = new ArrayList<TabChildren2>();
						  for(MenuTabActionMapping mapping: tabMaster.getMenuTabActionMapping())
						  {
							  TabChildren2 tabChildren2= new TabChildren2();
							  tabChildren2.setName(mapping.getItem());
							  tabChildren2.setChecked(mapping.isHasAccess());
							  tabChildren2.setChildren(new ArrayList<Children>());
							  
							  lChildren2s.add(tabChildren2);
						  }
						  tabChildren.setChildren(lChildren2s);
						  tabChildrens.add(tabChildren); 						 
					}
				
					  children.setChildren(tabChildrens);
					  chList.add(children);
				 }
				 menuBarResponse.setChildren(chList);
				 menuBarResponses.add(menuBarResponse);
			}
			
		 
			return menuBarResponses;
			
		} catch (Exception e) {
			log.error("Exception occurred while saving user: {}", e.getMessage());
			e.printStackTrace();
			throw new InternalServerError("Exception occurred while fetching user access data.");
		}
	}



	public String updateUserAccessList(UserAccessDTO user) {

		List<MenuMaster> menuList = menuMasterRepo.findByUserTypeId(Integer.parseInt(user.getUserType()));
		for (MenuMaster menuMaster : menuList) {
			if (user.getHasAccessList().contains(menuMaster.getItem())) {
				menuMaster.setHasAccess(true);
			} else {
				menuMaster.setHasAccess(false);
			}

			for (ChildMenuMaster childMenuMaster : menuMaster.getChildMenuMaster()) {
				if (user.getHasAccessList().contains(childMenuMaster.getItem())) {
					childMenuMaster.setHasAccess(true);
				} else {
					childMenuMaster.setHasAccess(false);
				}
				for (TabMaster tabMaster : childMenuMaster.getTabMaster()) {
					if (user.getHasAccessList().contains(tabMaster.getItem())) {
						tabMaster.setHasAccess(true);
					} else {
						tabMaster.setHasAccess(false);
					}

					for (MenuTabActionMapping mapping : tabMaster.getMenuTabActionMapping()) {
						if (user.getHasAccessList().contains(mapping.getItem())) {
							mapping.setHasAccess(true);
						} else {
							mapping.setHasAccess(false);
						}
					}

				}
			}

		}

		List<MenuMasterDto> resultList = menuList.stream().map(obj -> {
			return objectMapper.convertValue(obj, MenuMasterDto.class);
		}).collect(Collectors.toList());

		return updateUserAccess(resultList);
	}

public ResponseEntity<ResponseWrapper> findAndGetAllRoles(int userId) {
		
		UserRole userRole = null;
		List<Role> roles = null;
		try {
			userRole = userRoleRepo.findByUserId(userId);
			roles = roleRepo.getAllRoles(userRole.getRole_id());
		} catch (Exception e) {
			logger.info("unable to get the user role: " +e);
			return new ResponseEntity<>(new ResponseWrapper(FAILED.name, null),HttpStatus.NOT_FOUND);
		}
				
		if(userRole.getRole_id()==21) {
			return new ResponseEntity<>(new ResponseWrapper(SUCCESS.name, roles),HttpStatus.OK); 
		} else {
			
			Long roleId = userRole.getRole_id();
			List<Role> filteredRoles = filterData(roles,roleId);
			return new ResponseEntity<>(new ResponseWrapper(SUCCESS.name,filteredRoles),HttpStatus.OK);

		}
		
	}
	
	private List<Role> filterData(List<Role> roles, Long roleId){
		
		List<Role> newRoles = new ArrayList<>();
		
		for(int i=0; i<roles.size();i++) {
		
		for(Role role : roles){
			if(role.getParentId()==roleId) {
				newRoles.add(role);
				roleId=role.getId();
			}
		}
	}
		return newRoles;
	}
	
	
	public Map<String,String> checkUserDecryption(String encryptedUserName, String encryptedPassword) {

    	final String key = "AES1234567890123";
    	final String iv = "AES1234567890123";
	     IvParameterSpec parameterSpec = null;
	     SecretKeySpec skeySpec = null;
	     Cipher decryptionCipher = null;
	     String decryptedUsername  = null;
	     String decryptedPassword = null;
		try {
			parameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
			skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	     
		try {
			decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			decryptionCipher.init(Cipher.DECRYPT_MODE,skeySpec,parameterSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	       
			try {
				decryptedUsername  = new String(decryptionCipher.doFinal(Base64.getDecoder().decode(encryptedUserName)));
				decryptedPassword = new String(decryptionCipher.doFinal(Base64.getDecoder().decode(encryptedPassword)));
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
			
			final String userName = decryptedUsername;
	        final String password = decryptedPassword;
	        Map<String,String> userData = new HashMap<>();
	        
	        userRepo.findByUsername(decryptedUsername)
	        .ifPresentOrElse(user -> {userData.put("userName", user.getUsername());
	        						  userData.put("password", password);
	        						 },
	        		() -> new CustomException("User '" + userName + "' not found"));
	        
	       return userData;
    }
}