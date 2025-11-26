package com.beanspot.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final TokenProvider tokenProvider;
    @Autowired
    private final CustomUserDetailService customUserDetailService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //요청 경로 추출
            String requestUri = request.getRequestURI();
            //토큰 추출
            String token = parseBearerToken(request);

            if(StringUtils.hasText(token) && !token.equalsIgnoreCase("null")){
                if(requestUri.startsWith("/api/auth/oauth/kakao/signup")){
                    if(!validateTemporaryToken(token, request, response)) return; //임시 토큰이 유효하지 않으면 필터 체인을 진행하지 않음.
                }else{
                    if(tokenProvider.validateToken(token)) {
                        String tokenType = tokenProvider.getTokenType(token);
                        //일반 토큰과 임시 토큰을 분리해서 처리해야 함.
                        if("access".equals(tokenType)){
                            Long userId = tokenProvider.getUserIdFromToken(token);
                            log.info("User id is {}", userId);

                            UserDetails userDetails = customUserDetailService.loadUserById(userId);
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            //                    AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null,
                            //                            AuthorityUtils.NO_AUTHORITIES);
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            log.info("Authenticated user {}", authentication);
                            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                            securityContext.setAuthentication(authentication);
                            SecurityContextHolder.setContext(securityContext);
                        }
                    }
                }

            }
        }catch (Exception ex) {
            logger.error("could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateTemporaryToken(String token, HttpServletRequest request, HttpServletResponse response) {
        if(tokenProvider.validateToken(token)) {
            String tokenType = tokenProvider.getTokenType(token);
            if ("temporary".equals(tokenType)) {
                String socialId = tokenProvider.getSocialIdFromToken(token);
                String socialType = tokenProvider.getSocialTypeFromToken(token);

                log.info("Temporary token detected. Social ID: {} and Social Type: {}", socialId, socialType);

                //소셜 아이디를 SecurityContext에 저장
                UserDetails userDetails = customUserDetailService.loadUserBySocicalId(socialId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("Authenticated user {}", authentication);
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);

                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }else{
                // 'temporary' 토큰이 아니면, 허용되지 않는 토큰 타입
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false; // 인증 실패
            }
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("it is not a temporary token");
            return false;
        }
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
