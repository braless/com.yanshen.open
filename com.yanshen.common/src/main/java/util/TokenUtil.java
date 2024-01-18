package util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dto.TenantAccountDTO;
import dto.TenantUserDTO;
import exception.BaseException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import tool.TokenClaimEnum;

/**
 * @Auther: taohaifeng
 * @Date: 2018/9/25 17:04
 * @Description: token 生成
 */
public class TokenUtil {
	// 32 位随机字符
	// private static final String SECRET = RandomStringUtils.randomAlphanumeric(32);
	// TODO 随机字符, 暂写在代码中, 需要定时更新?
	private static final String SECRET            = "HBZDUUALlGq0NACTJ9Uxtb9sMz2Aa70r";
	private static final long   CALENDAR_INTERVAL = 10L;

	/**
	 * JWT生成Token.
	 * JWT构成: header, payload, signature
	 * @param accountId
	 *            登录成功后用户accountId, 参数 accountId 不可传空
	 */
	public static String createToken(String accountId) throws BaseException {
		if(StringUtils.isBlank(accountId)){
			throw new BaseException(CodeEnum.PARAMS_FAILTURE.getCode(),"用户 Id 不可为空!");
		}
		// build token
		// param backups {iss:lyzh_hw4, aud:Web}
		String token = JWT.create() // header
				.withClaim("iss", TokenClaimEnum.LYZH_HW4.getClaim()) // payload
				.withClaim("aud", TokenClaimEnum.WEB_YW.getClaim())
				.withClaim("accountId", accountId)
				.sign(Algorithm.HMAC256(SECRET)); // signature
		return token;
	}

	/**
	 * 解密Token, 获取 claims 中的信息
	 *
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> decryToken(String token)  {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaims();
	}

	/**
	 * 根据Token获取用户id 和租户id
	 *
	 * @param token
	 * @return userId
	 */
	public static Map<String,String> getTokenInfo(String token) {
		Map<String, Claim> claims = decryToken(token);
		Map<String,String> map = new HashMap<String,String>();
		Claim userIdClaim = claims.get("accountId");
		if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
			// token 校验失败, 抛出Token验证非法异常
			throw new BaseException(CodeEnum.VERIFY_FAILTURE.getCode(),"Token 错误!");
		}
		map.put("accountId", userIdClaim.asString());
		
		Claim audClaim = claims.get("aud");
		if(null != audClaim && StringUtils.isNotEmpty(audClaim.toString()) 
				&& TokenClaimEnum.WEB_ZH.getClaim().equals(audClaim.asString())) {
			Claim tenantIdClaim = claims.get("tenantId");
			if (null == tenantIdClaim || StringUtils.isEmpty(tenantIdClaim.asString())) {
				// token 校验失败, 抛出Token验证非法异常
				throw new BaseException(CodeEnum.VERIFY_FAILTURE.getCode(),"Token 错误!");
			}
			map.put("tenantId", tenantIdClaim.asString());
			
			Claim yhlxClaim = claims.get("yhlx");
			if (null == yhlxClaim || StringUtils.isEmpty(yhlxClaim.asInt().toString())) {
				// token 校验失败, 抛出Token验证非法异常
				throw new BaseException(CodeEnum.VERIFY_FAILTURE.getCode(),"Token 错误!");
			}
			map.put("yhlx", yhlxClaim.asInt().toString());


		}
		if (null != audClaim && StringUtils.isNotEmpty(audClaim.toString())
				&& TokenClaimEnum.WEB_APP.getClaim().equals(audClaim.asString())) {
			Claim sjhmClaim = claims.get("sjhm");

			if (null!=sjhmClaim && StringUtils.isNotBlank(sjhmClaim.asString())) {
				String sjhm=sjhmClaim.asString();
				map.put("sjhm", sjhm);
			}
		}
		//当前登录账户的名字 h_tenant_user.zsxm 或者 h_tenant_account.zhnc
		Claim nickNameClaim = claims.get("nickName");
		if (null!=nickNameClaim && StringUtils.isNotBlank(nickNameClaim.asString())) {
			String nickName=nickNameClaim.asString();
			map.put("nickName", nickName);
		}
		return map;
	}
	
	
	public static String getAccountId(String token)  {
		Map<String, Claim> claims = decryToken(token);
		Claim userIdClaim = claims.get("accountId");
		if (null == userIdClaim || StringUtils.isEmpty(userIdClaim.asString())) {
			// token 校验失败, 抛出Token验证非法异常
			throw new BaseException(CodeEnum.VERIFY_FAILTURE.getCode(),"Token 错误!");
		}
		return userIdClaim.asString();
	}
	/**
	 * 租户端生成token
	 * @return
	 * @throws BaseException
	 */
	public static String createToken(TenantUserDTO tenantUserDTO) throws BaseException{
		if(StringUtils.isBlank(tenantUserDTO.getTenantuserid())){
			throw new BaseException(CodeEnum.PARAMS_FAILTURE.getCode(),"用户 Id 不可为空!");
		}
		// build token
		// param backups {iss:lyzh_hw4, aud:Web}
		String token = JWT.create() // header
				.withClaim("iss", TokenClaimEnum.LYZH_HW4.getClaim()) // payload
				.withClaim("aud", TokenClaimEnum.WEB_ZH.getClaim())
				.withClaim("accountId", tenantUserDTO.getTenantuserid())
				.withClaim("tenantId", tenantUserDTO.getTenantid())
				.withClaim("yhlx", tenantUserDTO.getYhlx())
				.withClaim("nickName", tenantUserDTO.getZsxm())
				.withClaim("sjhm",tenantUserDTO.getSjhm())
				.sign(Algorithm.HMAC256(SECRET)); // signature
		return token;
	}

	/**
	 * 获取当前时间和设置过期时间
	 */
	@SuppressWarnings("unused")
	private void dateCreat(){
		LocalDate localDate = LocalDate.now();
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		Date iatDate = Date.from(instant);
		// expire time
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime exDateTime = localDateTime.plusDays(CALENDAR_INTERVAL);
		Instant exInstant = exDateTime.atZone(zone).toInstant();
		Date expiresDate = Date.from(exInstant);
	}


	/**
	 * 创建C端用户的token
	 * @param
	 * @return
	 * @throws BaseException
	 */
	public static String createToken(TenantAccountDTO tenantAccountDTO) throws BaseException {
		if(StringUtils.isBlank(tenantAccountDTO.getTenantaccountid())){
			throw new BaseException(CodeEnum.PARAMS_FAILTURE.getCode(),"用户 Id 不可为空!");
		}
		String token="";
		// build token
		// param backups {iss:lyzh_hw4, aud:Web}
//		String token = JWT.create() // header
//				.withClaim("iss", TokenClaimEnum.LYZH_HW4.getClaim()) // payload
//				.withClaim("aud", TokenClaimEnum.WEB_APP.getClaim())
//				.withClaim("accountId", tenantAccountDTO.getTenantaccountid())
//				.withClaim("tenantId", tenantAccountDTO.getTenantid()) // 如果是绿色用户，租户ID默认为0，不可为空值
//				.withClaim("sfyz", tenantAccountDTO.getSfyz())
//				.withClaim("sjhm",tenantAccountDTO.getSjhm())
//				.withClaim("nickName", tenantAccountDTO.getZhnc())
//				.sign(Algorithm.HMAC256(SECRET)); // signature
		return token;
	}

	public static void main(String[] args) {
		//String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJXZWJfYXBwIiwiYWNjb3VudElkIjoiMjE3NThiYTktZWIwOS00MDgzLWE1OTMtZTAyNGE1MzljN2QxIiwiaXNzIjoibHl6aF9odzQiLCJzamhtIjoiMTM0NDU2NzAxMDYifQ.CGzXPnZWIN3CRy-lrR4QK34DrIpIFpLC_8zcV57PaXQ";
		String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJXZWJfYXBwIiwiYWNjb3VudElkIjoiMjE3NThiYTktZWIwOS00MDgzLWE1OTMtZTAyNGE1MzljN2QxIiwiaXNzIjoibHl6aF9odzQiLCJzamhtIjoiMTM0NDU2NzAxMDYifQ.CGzXPnZWIN3CRy-lrR4QK34DrIpIFpLC_8zcV57PaXQ";
		String a=TokenUtil.getAccountId(token);
		Map<String, Claim> claimMap = TokenUtil.decryToken(token);
		String iss = claimMap.get("iss").asString();
		String aud = claimMap.get("aud").asString();
		String sjhm = claimMap.get("sjhm").asString();
		System.out.println("iss:"+iss);
		System.out.println("aud:"+aud);
		System.out.println("sjhm:"+sjhm);
	}
}
