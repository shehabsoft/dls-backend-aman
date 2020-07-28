package ae.rta.dls.backend.security.util;

import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.um.CorporateType;
import ae.rta.dls.backend.domain.enumeration.um.UserType;
import ae.rta.dls.backend.domain.type.MultilingualJsonType;
import ae.rta.dls.backend.security.dto.CorporateContextDTO;
import ae.rta.dls.backend.security.dto.ProfileContextDTO;
import ae.rta.dls.backend.security.dto.UserContextDTO;
import ae.rta.dls.backend.security.jwt.TokenProvider;
import ae.rta.dls.backend.web.rest.errors.AuthenticationAlertException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Utility class for Spring Security.
 */

@Component
public class SecurityUtils {

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */

    private TokenProvider tokenProvider;


    public SecurityUtils(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

    public static Optional<String> getCurrentUserLoginWithAuthority() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername()+"#"+authentication.getAuthorities().toArray()[0].toString();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal()+"#"+authentication.getAuthorities().toArray()[0].toString();
                }
                return null;
            });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS_ROLE)))
            .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
            .orElse(false);
    }


    /**
     * Get current user profile from security context
     *
     * @return the current profile from context
     */
    public ProfileContextDTO getUserProfile() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String token  = StringUtil.getString(securityContext.getAuthentication().getCredentials());
        String principal = StringUtil.getString(securityContext.getAuthentication().getPrincipal());
        ProfileContextDTO profile = null;

        if (token == null) {
            throw new AuthenticationAlertException("missing JWT security token");
        }

        if (principal == null) {
            throw new AuthenticationAlertException("missing JWT security token principal");
        }

        if (securityContext.getAuthentication().getAuthorities() == null) {
            throw new AuthenticationAlertException("missing JWT security token authority");
        }

        if (securityContext.getAuthentication().getAuthorities().size() > 1) {
            throw new AuthenticationAlertException("not allowed to login in with multiple role");
        }

        Claims claims = tokenProvider.parseClaims(token);

        if (claims == null || claims.isEmpty()) {
            throw new AuthenticationAlertException("missing JWT security token claims");
        }

        profile = new ProfileContextDTO();
        profile.setChannelCode(StringUtil.getString(claims.get(TokenProvider.CHANNEL_CODE)));
        profile.setUserRole(securityContext.getAuthentication().getAuthorities().toArray()[0].toString());

        if (claims.get(TokenProvider.USER_TYPE) != null) {

            UserContextDTO userContextDTO = new UserContextDTO();
            userContextDTO.setUsername(principal);
            userContextDTO.setUserId(NumberUtil.toLong(claims.get(TokenProvider.USER_ID)));
            userContextDTO.setUserType(StringUtil.getString(claims.get(TokenProvider.USER_TYPE)));
            userContextDTO.setUserTypeDescription(new MultilingualJsonType());
            userContextDTO.getUserTypeDescription().setEn(StringUtil.getString(claims.get(TokenProvider.ENGLISH_USER_TYPE_DESC)));
            userContextDTO.getUserTypeDescription().setAr(StringUtil.getString(claims.get(TokenProvider.ARABIC_USER_TYPE_DESC)));
            userContextDTO.setEnglishCustomerName(StringUtil.getString(claims.get(TokenProvider.ENGLISH_CUSTOMER_NAME)));
            userContextDTO.setArabicCustomerName(StringUtil.getString(claims.get(TokenProvider.ARABIC_CUSTOMER_NAME)));
            profile.setUserContextDTO(userContextDTO);

        } else if (claims.get(TokenProvider.CORPORATE_TYPE) != null) {

            CorporateContextDTO corporateContextDTO = new CorporateContextDTO();
            corporateContextDTO.setAgentName(principal);
            corporateContextDTO.setTradeLicenseNo(StringUtil.getString(claims.get(TokenProvider.TRADE_LICENSE_NO)));
            corporateContextDTO.setCorporateType(StringUtil.getString(claims.get(TokenProvider.CORPORATE_TYPE)));
            corporateContextDTO.setCorporateTypeDescription(new MultilingualJsonType());
            corporateContextDTO.getCorporateTypeDescription().setEn(StringUtil.getString(claims.get(TokenProvider.ENGLISH_CORPORATE_TYPE_DESC)));
            corporateContextDTO.getCorporateTypeDescription().setAr(StringUtil.getString(claims.get(TokenProvider.ARABIC_CORPORATE_TYPE_DESC)));
            corporateContextDTO.setArabicCorporateName(StringUtil.getString(claims.get(TokenProvider.ARABIC_CORPORATE_NAME)));
            corporateContextDTO.setEnglishCorporateName(StringUtil.getString(claims.get(TokenProvider.ENGLISH_CORPORATE_NAME)));
            profile.setCorporateContextDTO(corporateContextDTO);
        }

        return profile;
    }

    /**
     * Is public user
     *
     * @return true if the user is public account (anonymous/logged-in)
     */
    public boolean isPublicUser() {
        return getUserProfile().getUserContextDTO() != null &&
            getUserProfile().getUserContextDTO().getUserId() != null &&
            getUserProfile().getUserContextDTO().getUserType() != null &&
            (getUserProfile().getUserContextDTO().getUserType().equals(UserType.ANONYMOUS.value()) ||
                getUserProfile().getUserContextDTO().getUserType().equals(UserType.VERIFIED.value()) ||
                getUserProfile().getUserContextDTO().getUserType().equals(UserType.IAM.value()));
    }

    /**
     * Is BPM user account
     *
     * @return true if the user is bpm account
     */
    public boolean isBpmUser() {
        return getUserProfile().getUserContextDTO() != null &&
            getUserProfile().getUserContextDTO().getUserId() != null &&
            getUserProfile().getUserContextDTO().getUserType() != null &&
            getUserProfile().getUserContextDTO().getUserType().equals(UserType.BPM.value());
    }

    /**
     * is corporate user account
     *
     * @return true if the user is corporate account
     */
    public boolean isCorporateUser() {
        return getUserProfile().getCorporateContextDTO() != null &&
            getUserProfile().getCorporateContextDTO().getTradeLicenseNo() != null &&
            getUserProfile().getCorporateContextDTO().getCorporateType() != null &&
            (getUserProfile().getCorporateContextDTO().getCorporateType().equals(CorporateType.MAIN.value()) ||
                getUserProfile().getCorporateContextDTO().getCorporateType().equals(CorporateType.BRANCH.value()));

    }

}
