package springsecurity.employeecrud.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springsecurity.employeecrud.Config.CustomUserDetailService;

import springsecurity.employeecrud.RolePermissionModel.Entity.UserEntity;
import springsecurity.employeecrud.RolePermissionModel.Service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
/**
 *
 * @author Saeed Shah
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain){
        //getHeader here
        //validate token
        String username = null;

            try { String url = request.getRequestURI().replace(servletContext.getContextPath() + "/", "").replaceAll("(([/]+)([0-9])+)", "");
                System.out.println("Original Url: " + request.getRequestURI());
                System.out.println("Url: " + url);

               // Add path lists here in authURlList which are used without JWT Token

                ArrayList<String> authUrlList = new ArrayList<>();
                authUrlList.add("Login/auth");
                authUrlList.add("books/views");
                authUrlList.add("books/view");


                //The upcoming urls are checked if already added in url list

                if (authUrlList.contains(url)) {

                    System.out.println("username: " + request.getParameter("username"));
                    System.out.println("DoFilter: " + Boolean.TRUE);
                    // simply generate response from coming request
                    chain.doFilter(request, response);

                  }

                // If url is not present in UrlList then urls must be added in permission list to proceed
                else {

                    System.out.println("ELSE URL LIST NOT CONTAINS: " + url);
                    String authToken = request.getHeader("Authorization");

                    System.out.println("Token   "+authToken);

                    //To check Token is expired or not
                    if (authToken != null && !authToken.trim().isEmpty() && !authToken.equals("Bearer")) {//AFTER
                        if (jwtUtil.isTokenExpired(authToken)) {

                            System.out.println("isTokenExpired ");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
                        } else {
                            System.out.println("Not Expiredddd");
                        }
                        System.out.println("=======================");
                        System.out.println("authToken: " + authToken);
                        System.out.println("ExpDate: " + jwtUtil.extractExpiration(authToken));
                        System.out.println("=======================");

                       //extract username from token login user
                       username = this.jwtUtil.extractUsername(authToken);

                        if (username != null) {

                            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

                            if (username == null && username.isEmpty()) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Not Found!!!");
                            }
                            UserEntity users = this.userService.findByUserName(username);
                            if (users.getStatus() == false) {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Not Found!!!");

                            }
//
                            System.out.println("============================================");
                            System.out.println("UserName: " + username);
                            System.out.println("Status: " + users.getStatus());
                            System.out.println("Url: " + url);

                            if (!authUrlList.contains(url)) {
                                String[] urlSpiltter = url.split("/");
                                if (urlSpiltter.length > 1) {
                                    url = urlSpiltter[0] + "/" + urlSpiltter[1];
                                } else {
                                    url = urlSpiltter[0];
                                }

                                // craete List of all the Authenticated Requests
                                ArrayList<String> tokenPermissionlist = jwtUtil.getPermissionList(authToken);

                                tokenPermissionlist.add("books/create");
                                tokenPermissionlist.add("books/update");
                                tokenPermissionlist.add("books/delete");
                                tokenPermissionlist.add("books/getAll");
                                tokenPermissionlist.add("books/view");
                                tokenPermissionlist.add("books/truncate");
                                tokenPermissionlist.add("books/getAllByAuthor");
                                tokenPermissionlist.add("books/getAllByName");

                                tokenPermissionlist.add("author/create");
                                tokenPermissionlist.add("author/update");
                                tokenPermissionlist.add("author/delete");
                                tokenPermissionlist.add("author/getAll");
                                tokenPermissionlist.add("author/view");
                                tokenPermissionlist.add("author/truncate");
                                tokenPermissionlist.add("author/views");
                                tokenPermissionlist.add("author/getByName");


                                // to check coming url in permission list

                                if (tokenPermissionlist.contains(url)) {
                                    System.out.println("Authorize:---- " + Boolean.TRUE);
                                    System.out.println("============================================\n");

                                    //User Authentication

                                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                    SecurityContextHolder.getContext().setAuthentication(authentication);

                                    if (jwtUtil.validateToken(authToken, userDetails)) {
                                        logger.info("Valid Token");
                                    } else {
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                    }

                                } else {
                                    System.out.println("Authorize:NOT " + Boolean.FALSE);
                                    System.out.println("============================================\n");
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                                }
                            } else {
                                System.out.println("URL NOT FOUND ");
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

                            }
                        } else {
                            System.out.println("User is null");
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        }
                    } else {
                        System.out.println("Token is Null");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }
                    System.out.println("============================================");
                    System.out.println("LAST POINT");
                    System.out.println("============================================\n");
                    chain.doFilter(request, response);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

    }



}
