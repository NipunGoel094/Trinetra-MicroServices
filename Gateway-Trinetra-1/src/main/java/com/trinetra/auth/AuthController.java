package com.trinetra.auth;


import static com.trinetra.constant.Constant.EMAIL_ALREADY_TAKEN;
import static com.trinetra.constant.Constant.PINCODE_NOT_FOUND;
import static com.trinetra.constant.Constant.USER_ALREADY_TAKEN;
import static com.trinetra.constant.Constant.USER_DISABLED;
import static com.trinetra.model.enums.Status.FAILED;
import static com.trinetra.model.enums.Status.SUCCESS;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinetra.auth.model.JwtResponse;
import com.trinetra.auth.model.LoginRequest;
import com.trinetra.auth.model.SignupRequest;
import com.trinetra.exception.CustomException;
import com.trinetra.exception.ExceptionResponse;
import com.trinetra.exception.ExceptionResponseWrapper;
import com.trinetra.model.entity.Address;
import com.trinetra.model.entity.Pincode;
import com.trinetra.model.entity.User;
import com.trinetra.model.entity.mapping.UserRole;
import com.trinetra.model.entity.master.Role;
import com.trinetra.model.request.ForgotPasswordRequest;
import com.trinetra.model.request.MobileValidateOtpRequest;
import com.trinetra.model.request.ResetPasswordRequest;
import com.trinetra.model.response.MailResponse;
import com.trinetra.model.response.Message;
import com.trinetra.model.useraccess.entity.MenuAccessMaster;
import com.trinetra.model.wrapper.MobileApiResponseWrapper;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.repo.MenuAccessMasterRepo;
import com.trinetra.repo.PincodeRepo;
import com.trinetra.repo.RoleRepo;
import com.trinetra.repo.StateMasterRepo;
import com.trinetra.repo.UserRepository;
import com.trinetra.repo.UserRoleRepo;
import com.trinetra.service.MobileApplicationService;
import com.trinetra.service.UserService;

import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepo userRoleRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final PincodeRepo pincodeRepo;
    private final JwtUtil jwtUtil;
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
	@Lazy
	private MenuAccessMasterRepo menuAccessMasterRepo;
    
    @Autowired
	@Lazy
	private ObjectMapper objectMapper;
    
    @Autowired
    private CaptchaService captchaService;
    
	@Autowired
	private MobileApplicationService mobileApplicationService;
	
	@Autowired
	private StateMasterRepo stateMasterRepo;
    
    @Value("${user_initial_role}")
    private String initialRole;
    public AuthController(ReactiveAuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          UserRoleRepo userRoleRepo,
                          RoleRepo roleRepo,
                          PasswordEncoder encoder,
                          UserService userService,
                          PincodeRepo pincodeRepo,
                          JwtUtil jwtUtil,
                          JdbcTemplate jdbcTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepo = userRoleRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.userService = userService;
        this.pincodeRepo = pincodeRepo;
        this.jwtUtil = jwtUtil;
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @PostMapping("/signin")
    public Mono<ResponseEntity<ResponseWrapper>> authenticateUser(@RequestBody LoginRequest loginRequest,@RequestParam("access") String access) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    	
    	if(!access.equalsIgnoreCase("mobile")) {
    		boolean checkRecaptchavalidation = captchaService.verify(loginRequest.getRecaptchaResponse());
        	
        	if(!checkRecaptchavalidation) {
                return Mono.just(new ResponseEntity<ResponseWrapper>(new ResponseWrapper("Invalid Request", checkRecaptchavalidation), HttpStatus.UNAUTHORIZED));
            }
    	}
    	
    	Map<String,String> checkUserDecryption = userService.checkUserDecryption(loginRequest.getUsername(),loginRequest.getPassword());
    	
    	Mono<Authentication> authentication = 
            		authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(checkUserDecryption.get("userName"), 
                    										checkUserDecryption.get("password")));        
    	Mono<ResponseEntity<ResponseWrapper>> monoResponseEntity =
    	authentication
        .flatMap(auth -> {
            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
            String jwt = jwtUtil.generateToken(userDetails);
            Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
            User user = userOptional.get();
    		try {
    			MenuAccessMaster menuAccessMaster = menuAccessMasterRepo.findByUserTypeId(user.getUserTypeId());
    			user.setMenuAccessJson(Arrays.asList(objectMapper.readValue(menuAccessMaster.getJsonValue(), Map[].class)));
    			user.getAddress().setState(stateMasterRepo.getStateName(user.getId()).orElse(null));
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		if (userOptional.get().isActive() == false) {
    			throw new CustomException(USER_DISABLED);
    		}
    		JwtResponse jwtResponse = new JwtResponse(jwt, "Bearer", user);
            return Mono.just(new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, jwtResponse ),	HttpStatus.OK));
        });

    	return monoResponseEntity;
        
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {

            throw new CustomException(USER_ALREADY_TAKEN);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {

            throw new CustomException(EMAIL_ALREADY_TAKEN);
        }

        Optional<Pincode> optional = pincodeRepo.findByPincode(signUpRequest.getAddress().getPincode());
        if(!optional.isPresent()){
            throw new CustomException(PINCODE_NOT_FOUND);
        }
       // Set<Pincode> pinCode = new HashSet<>();
		//pinCode.add(optional.get());
		
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail(),
                signUpRequest.getMobileNumber(),
                new Address (
                        signUpRequest.getAddress().getAddressType(),
                        signUpRequest.getAddress().getAddress1(),
                        signUpRequest.getAddress().getAddress2(),
                        optional.get().getId()
                )
        );
        //user.setRoles("ROLE_USER");
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role("ROLE_USER"));


            User savedUser = null;
            //user.setCreatedAt((LocalDateTime.now()).toString());
//            user.setRoles(roles);
            savedUser = userRepository.save(user);
            Role roleUser = null;
            if(roleRepo.existsByName(initialRole)){
            roleUser = roleRepo.findByName(initialRole);
            }else{
                roleUser = roleRepo.save(new Role(initialRole));
            }
        Long roleId = roleUser.getId();
        Long userId = savedUser.getId();
        userRoleRepo.save(new UserRole(userId,roleId));
        Address address = savedUser.getAddress();
        address.setPincode(optional.get().getPincode());
        savedUser.setAddress(address);
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,savedUser), HttpStatus.CREATED);
    }

    @PostMapping("/forgotPassword/{key}")
    public ResponseEntity<ResponseWrapper> forgotPassword(  @PathVariable("key") int key,
                                                            @RequestParam String email) {
        MailResponse mailResponse = userService.forgotPassword( email,key);
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,mailResponse), HttpStatus.CREATED);
    }
    
    @PostMapping("/forgotPasswordForMobile/{key}")
	public ResponseEntity<MobileApiResponseWrapper> forgotPassword(@PathVariable("key")int key, @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
		Object forgotPasswordResponse = mobileApplicationService.forgotPassword(key,forgotPasswordRequest);
		if(forgotPasswordResponse instanceof Message) {
			return new ResponseEntity<MobileApiResponseWrapper>(toMobileApiErrorResponseWrapper(FAILED.name,forgotPasswordResponse),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MobileApiResponseWrapper>(new MobileApiResponseWrapper(SUCCESS.name,forgotPasswordResponse),HttpStatus.OK);
	}

	@PostMapping("/validateOtp")
	public ResponseEntity<MobileApiResponseWrapper> validateOtp(@RequestBody MobileValidateOtpRequest mobileValidateOtpRequest) {

		Message message = mobileApplicationService.validateOtp(mobileValidateOtpRequest);
		if(message.getMessage().equals("OTP is Validated.Password updation successful")) {
			return new ResponseEntity<MobileApiResponseWrapper>(new MobileApiResponseWrapper(SUCCESS.name, message), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<MobileApiResponseWrapper>(toMobileApiErrorResponseWrapper(FAILED.name, message), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/reset-mobile-password")
    public ResponseEntity<MobileApiResponseWrapper> resetMobilePassword(@Valid @RequestBody ResetPasswordRequest req) {
        Message message = mobileApplicationService.resetPassword(req);
        if(message.getMessage().equals("Your password updated Successfully")) {
        	return new ResponseEntity<MobileApiResponseWrapper>(new MobileApiResponseWrapper(SUCCESS.name,message), HttpStatus.OK);
        }
        return new ResponseEntity<MobileApiResponseWrapper>(toMobileApiErrorResponseWrapper(FAILED.name,message), HttpStatus.NOT_FOUND);
    }
	
	private MobileApiResponseWrapper toMobileApiErrorResponseWrapper(String name, Object response) {
		Map<String,String> res=new HashedMap<>();
		res.put("message", ((Message)response).getMessage());
	    return new MobileApiResponseWrapper(FAILED.name,res);

	}
	
    @PutMapping("/reset-password")
    public ResponseEntity<ResponseWrapper> resetPassword(@Valid @RequestBody ResetPasswordRequest req) {
        Message message = userService.resetPassword(req);
        return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,message), HttpStatus.OK);
    }
    
    @GetMapping("/error/token/expired")
    public ResponseEntity<ExceptionResponseWrapper> tokenExpiredError(@RequestParam("msg") String msg){
    	 ExceptionResponse message = new ExceptionResponse(HttpStatus.UNAUTHORIZED, "Token has expired.");
    	 message.setErrors(Arrays.asList(msg));
         ExceptionResponseWrapper response = new ExceptionResponseWrapper("Failed", message);
         return new ResponseEntity<ExceptionResponseWrapper>(response, HttpStatus.UNAUTHORIZED);
    }

    private String loggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       return auth.getName();
    }
}
