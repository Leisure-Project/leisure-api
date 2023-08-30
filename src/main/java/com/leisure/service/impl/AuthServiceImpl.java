package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.config.security.JwtProvider;
import com.leisure.entity.Admin;
import com.leisure.entity.Client;
import com.leisure.entity.Role;
import com.leisure.entity.dto.Admin.CreateAdminResource;
import com.leisure.entity.dto.Client.CreateClientResource;
import com.leisure.entity.dto.Jwt.LoginResource;
import com.leisure.entity.dto.Jwt.TokenResource;
import com.leisure.entity.enumeration.Rolname;
import com.leisure.repository.AdminRepository;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.RoleRepository;
import com.leisure.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;
    @Override
    public Role getClientRole() throws Exception {
        Optional<Role> optionalRole = this.roleRepository.findByName(Rolname.Role_Client);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException(String.format("No existe un role llamado %s", Rolname.Role_Client));
        }
        return optionalRole.get();
    }

    @Override
    public Role getAdminRole() throws Exception {
        Optional<Role> optionalRole = this.roleRepository.findByName(Rolname.Role_Admin);
        if(optionalRole.isEmpty()){
            throw new ResourceNotFoundException(String.format("No existe un role llamado %s", Rolname.Role_Admin));
        }
        return optionalRole.get();
    }

    @Override
    public Client registerClient(Client request) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(getClientRole());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setRoles(roleSet);
        return clientRepository.save(request);
    }

    @Override
    public Admin registerAdmin(Admin request) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(getAdminRole());
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setRoles(roleSet);
        return adminRepository.save(request);
    }

    @Override
    public TokenResource login(LoginResource loginResource) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginResource.getDni(), loginResource.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = String.format("Bearer %s", provider.generateToken(authentication));
        TokenResource tokenResource = new TokenResource(token);
        return tokenResource;
    }
}
